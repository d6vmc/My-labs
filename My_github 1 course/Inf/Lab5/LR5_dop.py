import pandas as pd
import numpy as np
from tabulate import tabulate

path = "inf_lab5.xlsx"
sheet = "Лист1"
rows = 12
cols = 26

df = pd.read_excel(path, sheet_name=sheet, header=None, engine="openpyxl")

hits = (df == "X1").stack()
if not hits.any():
    raise ValueError('Не найдено "X1" на листе')

r0, c0 = hits[hits].index[0]

chunk = df.iloc[r0:r0 + rows, c0:c0 + cols].copy()

def cell(v):
    if pd.isna(v):
        return ""
    if isinstance(v, (int, np.integer)):
        return int(v)
    if isinstance(v, (float, np.floating)):
        return int(v) if float(v).is_integer() else v
    if isinstance(v, str):
        s = v.strip()
        if s == "":
            return ""
        try:
            n = float(s.replace(",", "."))
            return int(n) if np.isfinite(n) and abs(n - round(n)) < 1e-9 else n
        except Exception:
            return v
    return v

out = chunk.apply(lambda col: col.map(cell)).astype(object)

print(tabulate(out, tablefmt="grid", showindex=False, headers=[]))