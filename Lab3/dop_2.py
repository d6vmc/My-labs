from lark import Lark, Transformer
import sys
sys.path.append("..")
import os
import yaml
from dicttoxml import dicttoxml
from xml.dom.minidom import parseString

ron_grammar = r"""
?start: value

?value: struct
    | list
    | string
    | number
    | boolean
    | enum

struct: "(" [pair ("," pair)* [","]] ")"

list: "[" [value ("," value)* [","]] "]"

pair: IDENTIFIER ":" value

string: ESCAPED_STRING
number: SIGNED_NUMBER
boolean: TRUE | FALSE
enum: IDENTIFIER

IDENTIFIER: /[a-zA-Z_][a-zA-Z0-9_]*/

TRUE: "true"
FALSE: "false"
%import common.ESCAPED_STRING
%import common.SIGNED_NUMBER
%import common.WS

%ignore WS
"""

class RonTransformer(Transformer):
    def list(self, items):
        return list(items)
    def pair(self, items):
        tuple = (items[0].value, items[1])
        return tuple
    def struct(self, items):
        d = dict(items)
        return d
    def enum(self, items):
        return items[0].value
    def string(self, items):
        return items[0][1:][:-1]
    def number(self, items):
        return int(items[0])
    def boolean(self, items):
        if items[0].type == "TRUE":
            return True
        elif items[0].type == "FALSE":
            return False

ron_parser = Lark(ron_grammar, start='start', parser='lalr', transformer=RonTransformer())
def parse_ron_lark(text):
            return ron_parser.parse(text)

if __name__ == "__main__":
    filename = os.path.join(os.path.dirname(__file__), "../Lab3/input.ron")
    with open(filename, "r", encoding="utf-8") as f:
        text = f.read()
    try:
        parsed_data = parse_ron_lark(text)
        print(parsed_data)
    except SyntaxError as e:
        print(f"Ошибка {e}")

    try:
        output_folder_1 = os.path.join(os.path.dirname(__file__), "../Lab3")
        output_filepath_1 = os.path.join(output_folder_1, "output_lib.yaml")
        yaml_output = yaml.dump(parsed_data, allow_unicode=True, default_flow_style=False)
        with open(output_filepath_1, "w", encoding="utf-8") as f:
            f.write(yaml_output)
        

        xmll = dicttoxml(parsed_data, custom_root = 'root', attr_type=False, item_func=lambda x: 'item')
        norm = parseString(xmll)
        pretty_xml = norm.toprettyxml()

        output_folder_2 = os.path.join(os.path.dirname(__file__), "../Lab3")
        output_filepath_2 = os.path.join(output_folder_1, "output_lib.xml")
        with open(output_filepath_2, "w", encoding="utf-8") as f:
            f.write(pretty_xml)
    except Exception as e:
        print(f"Ошибка: {e}")
