import streamlit as st
import pandas as pd
import numpy as np
import torch
from pathlib import Path

try:
    from tsfm_public import TinyTimeMixerForPrediction
    HAS_MODEL_LIB = True
except Exception as e:
    HAS_MODEL_LIB = False
    st.error(f"Eroarea reală de import este: {e}")

st.set_page_config(page_title="Client Predictor - TTM AI", layout="centered", page_icon="🔮")

if not HAS_MODEL_LIB:
    st.error("`tsfm_public` nu functionează corect.")
    st.stop()

st.title("Predictor activitate smarthwatch")
st.markdown("""
Incarcati un fisier CSV cu istoricul activitatii din ultimele zile (minimum 512 ore), iar modelul de AI **IBM Granite TTM** va genera predict-urile pentru următoarele 24 de ore.
""")

MODEL_PATH = "rezultate_ai/ttm_finetuned/checkpoint-100"

@st.cache_resource
def load_ai_model():
    try:
        model = TinyTimeMixerForPrediction.from_pretrained(MODEL_PATH)
        model.eval()
        return model
    except Exception as e:
        st.error(f"Eroare la încărcarea modelului local: {str(e)}")
        return None

model = load_ai_model()

if model is None:
    st.warning("Te rog să verifici calea către folderul modelului (MODEL_PATH).")
    st.stop()

st.subheader("1. Introduceți datele istorice")
uploaded_file = st.file_uploader(
    "alegeti un fisier CSV cu date orare (trebuie sa contina coloanele: StepTotal și Calories)", type=["csv"])

use_demo = st.checkbox("foloseste date demonstrative generate automat.")

if uploaded_file is not None or use_demo:
    st.success("Date primite cu succes!")

    if use_demo:
        dr = pd.date_range(end=pd.Timestamp.now(), periods=512, freq='h')
        df_client = pd.DataFrame({
            'ActivityHour': dr,
            'StepTotal': np.random.randint(0, 3000, size=512),
            'Calories': np.random.randint(60, 250, size=512)
        })
    else:
        df_client = pd.read_csv(uploaded_file)
        if 'ActivityHour' in df_client.columns:
            df_client['ActivityHour'] = pd.to_datetime(df_client['ActivityHour'])

    st.write("ultimele ore introduse:")
    st.dataframe(df_client.tail(5))

    if len(df_client) < 512:
        st.error(
            f"Eroare: fisierul are doar {len(df_client)} inregistrari orare. Sunt necesare minimum 512 ore pentru contextul TTM.")
    else:
        context_data = df_client.tail(512).copy()

        st.subheader("2. generare predictii...")
        if st.button("ruleaza modelul TTM"):
            with st.spinner("modelul analizeaza tiparele din ultimele 512 ore..."):
                target_columns = ["StepTotal", "Calories"]
                values = context_data[target_columns].to_numpy(dtype=np.float32)

                means = np.array([291.60779375, 92.85701585])
                scales = np.array([606.51585846, 51.76852898])
                values_scaled = (values - means) / scales

                input_tensor = torch.tensor(values_scaled, dtype=torch.float32).unsqueeze(0)

                freq_token_tensor = torch.zeros((1, 1), dtype=torch.long)

                with torch.no_grad():
                    outputs = model(past_values=input_tensor, freq_token=freq_token_tensor)
                    predictions_scaled = outputs.prediction_outputs.numpy()[0]

                predictions_original = (predictions_scaled * scales) + means

                predictii_pasi = np.maximum(predictions_original[:, 0], 0).astype(int)
                predictii_calorii = np.maximum(predictions_original[:, 1], 0).astype(int)

                future_hours = pd.date_range(start=context_data['ActivityHour'].iloc[-1] + pd.Timedelta(hours=1),
                                             periods=24, freq='h')

                df_predictii = pd.DataFrame({
                    'Ora Viitoare': future_hours,
                    'Predicție Pasi (StepTotal)': predictii_pasi,
                    'Predicție Calorii (Calories)': predictii_calorii
                })

                st.success("predictii pt urmatoarele 24 de ore")

                col1, col2 = st.columns(2)
                with col1:
                    st.metric("Total pasi estimati (urmatoarele 24h)", f"{sum(predictii_pasi):,}")
                with col2:
                    st.metric("Total calorii de ars (urmatoarele 24h)", f"{sum(predictii_calorii):,} kcal")

                st.write("### Graficul evolutiei estimate pe ore")

                df_grafic = df_predictii.set_index('Ora Viitoare')
                st.line_chart(df_grafic)

                st.write("### Tabel detalii orare")
                st.dataframe(df_predictii)

                csv_export = df_predictii.to_csv(index=False).encode('utf-8')
                st.download_button(
                    label="Descarca predictiile în format CSV",
                    data=csv_export,
                    file_name="predictii_client_24h.csv",
                    mime="text/csv"
                )