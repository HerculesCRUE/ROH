from json2html import *
from os import listdir

folder_jsons = sys.argv[1]
fordel_html = sys.argv[1]

files = listdir(folder_jsons)
json_files = []
for i in files:
    if i.endswith(".json"):
        json_files.append(i)
files = [folder_jsons  + '/' + f for f in json_files]

for f in files:
    json_result = open(f, 'r')
    json_string=json_result.read()
    json_result.close()

    json_in_html = json2html.convert(json=json_string)
    index_start = f.index('Q')
    index_end = f.index('.')
    id_query_string = f[index_start:index_end]

    string_destino = fordel_html + '/' + id_query_string + '.html'
    file_html = open(string_destino, "w")
    file_html.write(json_in_html)
    file_html.close()
