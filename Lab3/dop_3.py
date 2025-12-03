import time
import os
import yaml
from dicttoxml import dicttoxml
from main import parse_ron, to_yaml, to_xml
from dop_2 import parse_ron_lark

iter = 100
filename = os.path.join(os.path.dirname(__file__), "../Lab3/input.ron")

with open(filename, "r", encoding="utf-8") as f:
    ron_text = f.read()

start_time = time.time()
for i in range(iter):
    data = parse_ron(ron_text)

    i = to_yaml(data)

    i = to_xml(data)

end_time = time.time()

total_time_manual = end_time - start_time
print(f"Ручная реализация заняла {total_time_manual:.4f} секунд!")

start_time = time.time()
for i in range(iter):
    data = parse_ron_lark(ron_text)

    i = yaml.dump(data, allow_unicode=True, default_flow_style=False)

    i = dicttoxml(data, custom_root='root', attr_type=False, item_func=lambda x: 'item')

end_time = time.time()
total_time_lib = end_time - start_time
print(f"Библиотечная реализация заняла {total_time_lib:.4f} секунд!")

if total_time_manual < total_time_lib:
    d = total_time_lib/total_time_manual
    print(f"Ручная реализация быстрее в {d:.3} раз")
else:
    d = total_time_manual/total_time_lib
    print(f"Библиотечная реализация быстрее в {d} раз")