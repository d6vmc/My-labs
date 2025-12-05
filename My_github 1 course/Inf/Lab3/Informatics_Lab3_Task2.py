# Author = Salpagarov Eldar Elbrusovich
# Group = P3132
# Date = 23.10.2025
import re
m = r"""
Петров П.П. P0000
Анищенко А.А. P33113
Примеров Е.В. P0000
Иванов И.И. P0000
"""
lines = []
print("Вводите строки, пустая строка — конец:")
while True:
    line = input()
    if not line:
        break
    lines.append(line)
s = "\n".join(lines)

pattern1 = r'[А-ЯЁ][а-яё-]+ [А-Я]\.[А-Я]\. P\d+'
pattern2 = r'([А-ЯЁ][а-яё-]+ ([А-Я])\.\2\. P\d+)'
matches = re.findall(pattern2, m)
matchees = re.findall(pattern1, m)

for i in range(len(matchees)):
    if matchees[i] not in [x[0] for x in matches]:
        print(matchees[i])