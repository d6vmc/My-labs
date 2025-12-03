#<RON> ::= <value>
#<value> ::= <string> | <number> | <boolean> | <identifier> | <struct> | <list>
#<struct> ::= "(" <pairs> ")"
#<pairs> ::= <pair> | <pair> "," <pairs>
#pair ::= <identifier> ":" <value>
#<list> :: = "[" <elements> "]"
#<elements> ::= <value> | <value> "," <elements>

import sys
sys.path.append("..")
import os

T_IDENTIFIER = 'IDENTIFIER'
T_STRING = 'STRING'
T_NUMBER = 'NUMBER'
T_LEFT_PAREN = 'LEFT_PAREN'
T_RIGHT_PAREN = 'RIGHT_PAREN'
T_LEFT_BRACKET = 'LEFT_BRACKET'
T_RIGHT_BRACKET = 'RIGHT_BRACKET'
T_COMMA = 'COMMA'
T_COLON = 'COLON'
T_QUOTE = 'QUOTE'
T_BOOLEAN = 'BOOLEAN'
T_EOF = 'EOF'

RON_COMMA = ','
RON_COLON = ':'
RON_LEFT_BRACKET = '['
RON_RIGHT_BRACKET = ']'
RON_LEFT_PAREN = '('
RON_RIGHT_PAREN = ')'
QUOTE = '"'
WHITESPACE = [' ', '\t', '\b', '\n', '\r']

class Token:
    def __init__(self, type, value = None):
        self.type = type
        self.value = value
    def __repr__(self):
        if self.value is not None:
            return f'Token({self.type}, {repr(self.value)})'
        else:
            return f'Token({self.type})'

class Lexer:
    def __init__(self, text):
        self.text = text
        self.pos = 0
    
    def make_tokens(self):
        tokens = []
        while True:
            token = self._next_token()
            tokens.append(token)
            if token.type == T_EOF:
                break
        return tokens

    def _get_string(self):
        self.pos += 1
        start_pos = self.pos

        while self.pos < len(self.text) and self.text[self.pos] != QUOTE:
            self.pos += 1
        
        if self.pos >= len(self.text):
            raise ValueError("Незакрытая строка в файле")
        
        value = self.text[start_pos:self.pos]
        self.pos+=1
        return Token(T_STRING, value)
    
    def _get_number(self):
        start_pos = self.pos

        while self.pos < len(self.text) and self.text[self.pos] in '0123456789':
            self.pos+=1
        
        value = int(self.text[start_pos:self.pos])
        return Token(T_NUMBER, value)

    def _get_identifier(self):
        start_pos = self.pos
        
        if self.text[self.pos].isalpha():
            while self.pos < len(self.text) and (self.text[self.pos].isalnum() or self.text[self.pos] == '_'):
                self.pos += 1
        else:
            return None

        value = self.text[start_pos:self.pos]
        if value == 'true':
            return Token(T_BOOLEAN, True)
        if value == 'false':
            return Token(T_BOOLEAN, False)
        else:
            return Token(T_IDENTIFIER, value)

    def _next_token(self):
        while self.pos < len(self.text):
            if self.text[self.pos] in WHITESPACE:
                self.pos+=1
                continue
            elif self.text[self.pos] == RON_LEFT_PAREN:
                self.pos += 1
                return Token(T_LEFT_PAREN)
            elif self.text[self.pos] == RON_RIGHT_PAREN:
                self.pos += 1
                return Token(T_RIGHT_PAREN)
            elif self.text[self.pos] == RON_COLON:
                self.pos += 1
                return Token(T_COLON)
            elif self.text[self.pos] == RON_COMMA:
                self.pos += 1
                return Token(T_COMMA)
            elif self.text[self.pos] == RON_LEFT_BRACKET:
                self.pos += 1
                return Token(T_LEFT_BRACKET)
            elif self.text[self.pos] == RON_RIGHT_BRACKET:
                self.pos += 1
                return Token(T_RIGHT_BRACKET)
            elif self.text[self.pos] in '0123456789':
                return self._get_number()
            elif self.text[self.pos].isalpha():
                return self._get_identifier()
            elif self.text[self.pos] == QUOTE:
                return self._get_string()
            else:
                raise ValueError("Неизвестный символ")
        return Token(T_EOF)
    
class Parser:
    def __init__(self, tokens):
        self.tokens = tokens
        self.pos = 0
        self.current_token = self.tokens[self.pos]

    def eat_tokens(self, type):
        if self.current_token.type == type :
            self.pos += 1
            if self.pos < len(self.tokens):
                self.current_token = self.tokens[self.pos]
            else:
                self.current_token = None
        else:
            raise SyntaxError(f'Синтаксическая ошибка, ожидался токен {type}, а получен {self.current_token.type}')
        
    def parse_struct(self):
        result = {}
        self.eat_tokens(T_LEFT_PAREN)

        while self.current_token.type != T_RIGHT_PAREN:
            id, value = self.parse_pair()
            result[id] = value
            if self.current_token.type == T_RIGHT_PAREN:
                break
            elif self.current_token.type == T_COMMA:
                self.eat_tokens(T_COMMA)
            else:
                raise SyntaxError("Неопознаный объект")
        self.eat_tokens(T_RIGHT_PAREN)
        return result
        
    def parse_list(self):
        result = []
        self.eat_tokens(T_LEFT_BRACKET)
        while self.current_token.type != T_RIGHT_BRACKET:
            value = self.parse_value()
            result.append(value)
            if self.current_token.type == T_RIGHT_BRACKET:
                break
            elif self.current_token.type == T_COMMA:
                self.eat_tokens(T_COMMA)
            else:
                raise SyntaxError("Неопознаный объект")
        self.eat_tokens(T_RIGHT_BRACKET)
        return result

    def parse_value(self):
        if self.current_token.type == T_STRING:
            val = self.current_token.value
            self.eat_tokens(T_STRING)
            return val
        elif self.current_token.type == T_NUMBER:
            val = self.current_token.value
            self.eat_tokens(T_NUMBER)
            return val
        elif self.current_token.type == T_BOOLEAN:
            val = self.current_token.value
            self.eat_tokens(T_BOOLEAN)
            return val
        elif self.current_token.type == T_IDENTIFIER:
            val = self.current_token.value
            self.eat_tokens(T_IDENTIFIER)
            return val
        elif self.current_token.type == T_LEFT_PAREN:
            return self.parse_struct()
        elif self.current_token.type == T_LEFT_BRACKET:
            return self.parse_list()
        else:
            raise SyntaxError("Неопознанный символ")
            
    def parse_pair(self):
        if self.current_token.type == T_IDENTIFIER:
            id = self.current_token.value 

            self.eat_tokens(T_IDENTIFIER)
            self.eat_tokens(T_COLON)

            val = self.parse_value()
            
            return(id, val)
        else:
            raise SyntaxError("Ожидался идентификатор")

    def parse(self):
        result = self.parse_value()
        if self.current_token.type != T_EOF:
            raise ValueError("Ошибка в файле")
        return result
    
def to_yaml(data, id = 0):
    yaml_string = ''
    if isinstance(data, dict):
        for key, value in data.items():
            if isinstance(value, (list, dict)):
                yaml_string += f"{' ' * id}{str(key)}:\n"
                yaml_string += to_yaml(value, id + 2)
            else:
                yaml_string += f"{' ' * id}{str(key)}: {to_yaml(value, id + 2)}\n"
        return yaml_string
    elif isinstance(data, list):
        for item in data:
            yaml_string += f"{' ' * id}-"
            if isinstance(item, (dict, list)):
                yaml_string += '\n' + to_yaml(item, id + 2)
            else:
                yaml_string += " " + to_yaml(item, 0) + "\n"
        return yaml_string
    elif isinstance(data, str):
        return f"'{data}'"
    elif isinstance(data, (int, float)):
        data = str(data)
        return data
    elif isinstance(data, bool):
        return str(data).lower()    
    elif data is None:
        return "null"
    else:
        return str(data)
    
def to_xml(data, key = "root", level = 0):
    xml_string = ''
    ident = ' ' * level
    if isinstance(data, dict):
        xml_string += f'{ident}<{key}>\n'
        for k, value in data.items():
            xml_string += to_xml(value, k, level + 2)
        xml_string += f'{ident}</{key}>\n'
        return xml_string
    elif isinstance(data, list):
        xml_string += f'{ident}<{key}>\n'
        for item in data:
            xml_string += f'{to_xml(item, key = "item", level = level + 2)}'
        xml_string += f'{ident}</{key}>\n'
        return xml_string
    elif isinstance(data, (int, str, float)):
        return f'{ident}<{key}>{data}</{key}>\n'
    elif isinstance(data, bool):
        return f'{ident}<{key}>{str(data).lower()}</{key}>\n'
    elif data is None:
        return f'{ident}<{key}></{key}>'
    else:
        return f'{ident}<{key}>{data}</{key}>\n'

def parse_ron(text):
    lexer = Lexer(text)
    tokens = lexer.make_tokens()
    parser = Parser(tokens)
    return parser.parse()

if __name__ == "__main__":
    filename = os.path.join(os.path.dirname(__file__), "../Inf_Lab3/input.ron")

    try:
        with open(filename, 'r', encoding='utf-8') as f:
            s = f.read()
        parsed_object = parse_ron(s)
        print(parsed_object)

        yaml_output = to_yaml(parsed_object)
        print(yaml_output)
        output_folder_1 = os.path.join(os.path.dirname(__file__), "../Inf_Lab3")
        output_filepath_1 = os.path.join(output_folder_1, "output.yaml")
        with open(output_filepath_1, 'w', encoding='utf-8') as f:
            f.write(yaml_output)

        xml_output = to_xml(parsed_object)
        print(xml_output)
        output_folder_2 = os.path.join(os.path.dirname(__file__), "../Inf_Lab3")
        output_filepath_2 = os.path.join(output_folder_2, "output.xml")
        with open(output_filepath_2, 'w', encoding='utf-8') as f:
            f.write(xml_output)
    except SyntaxError as e:
        print(f"Ошибка: {e}")
