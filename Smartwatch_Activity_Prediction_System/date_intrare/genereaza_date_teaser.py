import pandas as pd
import numpy as np

start_date = pd.to_datetime('2026-05-01 00:00:00')
periods = 530
date_range = pd.date_range(start=start_date, periods=periods, freq='h')

steps_active = np.where((date_range.hour >= 8) & (date_range.hour <= 20),
                        np.random.randint(500, 3500, size=periods),
                        np.random.randint(0, 100, size=periods))

calories_active = np.where((date_range.hour >= 8) & (date_range.hour <= 20),
                           np.random.randint(150, 450, size=periods),
                           np.random.randint(60, 100, size=periods))

df_active = pd.DataFrame({
    'ActivityHour': date_range,
    'StepTotal': steps_active,
    'Calories': calories_active
})
df_active.to_csv('date_intrare/teaser_utilizator_activ.csv', index=False)


steps_sedentary = np.where((date_range.hour >= 9) & (date_range.hour <= 18),
                           np.random.randint(100, 600, size=periods),
                           np.random.randint(0, 50, size=periods))

steps_sedentary = np.where((date_range.hour >= 19) & (date_range.hour <= 20),
                           np.random.randint(1000, 2500, size=periods),
                           steps_sedentary)

calories_sedentary = np.where(steps_sedentary > 1000,
                              np.random.randint(150, 300, size=periods),
                              np.random.randint(60, 120, size=periods))

df_sedentary = pd.DataFrame({
    'ActivityHour': date_range,
    'StepTotal': steps_sedentary,
    'Calories': calories_sedentary
})
df_sedentary.to_csv('date_intrare/teaser_utilizator_sedentar.csv', index=False)
