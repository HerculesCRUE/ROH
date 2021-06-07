from json2html import *
import json
from os import listdir

folder_jsons = sys.argv[1]
fordel_html = sys.argv[2]

files = listdir(folder_jsons)
# unicamente nos interesan los archivos .json
json_files = []
for i in files:
    if i.endswith(".json"):
        json_files.append(i)
files = [folder_jsons  + '/' + f for f in json_files]

for f in files:
    # Obtenemos el json 
    json_result = open(f, 'r')
    content = json_result.read()
    jsondecoded = json.loads(content) 
    json_result.close()

    # pasamos el json  a html. Esta estepcion capta cuando el ficheor json esta vacio.
    try:
        json_in_html = json2html.convert(json=jsondecoded['result'])
    except :
        json_in_html = json2html.convert(json=jsondecoded)

    # Obtemeos el nombre de la pregunta esparql par apoder denominar a nuestro dichero html con el mismo nombre 
    index_start = f.index('Q')
    index_end = f.index('.')
    id_query_string = f[index_start:index_end]

    
    string_destino = fordel_html + '/' + id_query_string + '.html' 
    file_html = open(string_destino, "w") 
    file_html.write(json_in_html) 
    file_html.close()
