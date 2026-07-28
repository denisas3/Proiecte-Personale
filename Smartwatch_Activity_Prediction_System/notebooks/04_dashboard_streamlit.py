from pathlib import Path

import numpy as np
import pandas as pd
import streamlit as st
import matplotlib.pyplot as plt


st.set_page_config(page_title="Dashboard TTM - Smartwatch", layout="wide")

ROOT_DIR = Path(__file__).resolve().parents[1]
AI_DIR = ROOT_DIR / "rezultate_ai"
PRED_FILE = AI_DIR / "predictii_ttm.csv"
METRICS_FILE = AI_DIR / "metrici_modele_ttm.csv"

MODEL_COLUMNS = {
    "Baseline": {
        "Steps": "PredictedSteps_Baseline",
        "Calories": "PredictedCalories_Baseline",
    },
    "TTM zero-shot": {
        "Steps": "PredictedSteps_TTM_ZeroShot",
        "Calories": "PredictedCalories_TTM_ZeroShot",
    },
    "TTM fine-tuned": {
        "Steps": "PredictedSteps_TTM_FineTuned",
        "Calories": "PredictedCalories_TTM_FineTuned",
    },
}


@st.cache_data
def load_predictions() -> pd.DataFrame:
    df = pd.read_csv(PRED_FILE)
    df["ActivityHour"] = pd.to_datetime(df["ActivityHour"], errors="coerce")
    df["ForecastHour"] = pd.to_numeric(df["ForecastHour"], errors="coerce")
    return df


@st.cache_data
def load_metrics() -> pd.DataFrame:
    return pd.read_csv(METRICS_FILE)


st.title("Dashboard predictii TTM - Smartwatch")
st.caption("Date din `rezultate_ai`: predictii pe ora si metrici de performanta.")

predictions = load_predictions()
metrics = load_metrics()

predictions = predictions.dropna(subset=["ActivityHour"])
predictions = predictions.sort_values("ActivityHour")

min_date = predictions["ActivityHour"].dt.date.min()
max_date = predictions["ActivityHour"].dt.date.max()

ids = sorted(predictions["Id"].dropna().unique())
forecast_hours = sorted(predictions["ForecastHour"].dropna().unique())

with st.sidebar:
    st.header("Filtre")
    selected_id = st.selectbox("Utilizator (Id)", ["Toate"] + ids)
    date_range = st.date_input("Interval date", value=(min_date, max_date))
    selected_forecast = st.multiselect(
        "ForecastHour (orizont)",
        forecast_hours,
        default=forecast_hours,
    )
    selected_variable = st.selectbox("Variabila", ["Steps", "Calories"])
    selected_models = st.multiselect(
        "Modele",
        list(MODEL_COLUMNS.keys()),
        default=list(MODEL_COLUMNS.keys()),
    )


def apply_filters(df: pd.DataFrame) -> pd.DataFrame:
    data = df.copy()
    if selected_id != "Toate":
        data = data[data["Id"] == selected_id]

    if isinstance(date_range, tuple) and len(date_range) == 2:
        start_date, end_date = date_range
        data = data[
            (data["ActivityHour"].dt.date >= start_date)
            & (data["ActivityHour"].dt.date <= end_date)
        ]

    if selected_forecast:
        data = data[data["ForecastHour"].isin(selected_forecast)]

    return data


filtered = apply_filters(predictions)

if filtered.empty:
    st.warning("Nu exista date pentru filtrele selectate.")
    st.stop()

actual_col = "ActualSteps" if selected_variable == "Steps" else "ActualCalories"

st.subheader("Overview metrici")
st.dataframe(metrics, width="stretch")


st.subheader("Predictii vs valori reale")
fig, ax = plt.subplots(figsize=(12, 4.5))
ax.plot(filtered["ActivityHour"], filtered[actual_col], label="Actual", color="black")
for model in selected_models:
    pred_col = MODEL_COLUMNS[model][selected_variable]
    if pred_col in filtered.columns:
        ax.plot(filtered["ActivityHour"], filtered[pred_col], label=model)
ax.set_title(f"{selected_variable} - comparatie real vs predictii")
ax.set_xlabel("Ora")
ax.set_ylabel(selected_variable)
ax.legend()
plt.xticks(rotation=30, ha="right")
plt.tight_layout()
st.pyplot(fig)


st.subheader("Eroare absoluta in timp")
mae_by_hour = {}
for model in selected_models:
    pred_col = MODEL_COLUMNS[model][selected_variable]
    if pred_col in filtered.columns:
        grp = filtered.groupby('ActivityHour')
        mae_series = grp.apply(lambda g: (g[pred_col] - g[actual_col]).abs().mean())
        mae_by_hour[model] = mae_series

if mae_by_hour:
    mae_df = pd.DataFrame(mae_by_hour)
    mae_df = mae_df.sort_index()
    fig, ax = plt.subplots(figsize=(12, 4.5))
    for col in mae_df.columns:
        ax.plot(mae_df.index, mae_df[col].rolling(window=3, min_periods=1).mean(), label=col)
    ax.set_title(f"MAE mediu per ora - {selected_variable}")
    ax.set_xlabel("Ora")
    ax.set_ylabel("MAE")
    ax.legend()
    plt.xticks(rotation=30, ha="right")
    plt.tight_layout()
    st.pyplot(fig)
else:
    st.info("Nu exista predictii disponibile pentru modelele selectate.")


st.subheader("Distributia erorilor")
fig, ax = plt.subplots(figsize=(10, 4.5))
for model in selected_models:
    pred_col = MODEL_COLUMNS[model][selected_variable]
    if pred_col in filtered.columns:
        abs_err = (filtered[pred_col] - filtered[actual_col]).abs()
        ax.hist(abs_err, bins=40, alpha=0.4, label=model)
ax.set_title(f"Distributia erorilor absolute - {selected_variable}")
ax.set_xlabel("Eroare absoluta")
ax.set_ylabel("Frecventa")
ax.legend()
plt.tight_layout()
st.pyplot(fig)


st.caption("Rulare: `streamlit run notebooks/04_dashboard_streamlit.py`")
