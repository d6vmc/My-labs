# Author = Salpagarov Eldar Elbrusovich
# Group = P3132
# Date = 23.10.2025
import re
m = input("Введите пароль: ")

rules = [lambda x: re.search(r'[1-9]', x), ]

print(rules[0]("ttyyt"))

pattern1 = r'\S{5,}'
if re.fullmatch(pattern1, m):
    print('Rule 1 is done')
else:
    print('Rule 1 is not done')

if re.search(r'[1-9]', m):
    print('Rule 2 is done')
else:
    print('Rule 2 is not done')

if re.search(r'[A-Z]', m):
    print('Rule 3 is done')
else:
    print('Rule 3 is not done')

if re.search(r'[\W]', m):
    print('Rule 4 is done')
else:
    print('Rule 4 is not done')

match = re.findall(r'[1-9]', m)
if sum(int(x) for x in match) > 25:
    print('Rule 5 is done')
else:
    print('Rule 5 is not done')

if re.search(r'(?:january|february|march|april|may|june|july|august|september|october|november|december)', m, re.IGNORECASE):
    print('Rule 6 is done')
else:
    print('Rule 6 is not done')

