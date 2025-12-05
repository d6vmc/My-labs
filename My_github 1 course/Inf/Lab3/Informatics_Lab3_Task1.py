# Author = Salpagarov Eldar Elbrusovich
# Group = P3132
# Date = 23.10.2025

import re
str = r"""
id="next"
id="sfns;janv_fs"
<div id= ”_next”>
<div class= "panel-body">
<div id= "LEFT_CONTENT" class="panel
no-border no-padding no-margin">
"""

pattern = r'id=\"\S+\"'
pattern2 = r'(?<=\")\S+(?=\")'

matches = re.findall(pattern, str)
for match in matches:
    finall = re.search(pattern2, match)
    print(finall[0])
