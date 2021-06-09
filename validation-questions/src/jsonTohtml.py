from json2html import *
import json
from os import listdir

folder_jsons = sys.argv[1]
fordel_html = sys.argv[2]
prefix = open('prefix.json', 'r')
content = prefix.read()
dic_of_prefix = json.loads(content)
prefix.close()

queries = open('queries.json', 'r')
content = queries.read()
dic_of_queries = json.loads(content)
queries.close()

files = listdir(folder_jsons)
# unicamente nos interesan los archivos .json
json_files = []
sparql_files = []
for i in files:
    if i.endswith(".json"):
        json_files.append(i)
    if i.endswith(".sparql"):
        sparql_files.append(i)
files = [folder_jsons + '/' + f for f in json_files]
# files_sparql = [folder_jsons + '/' + f for f in sparql_files]


for f in files:
    # Obtenemos el string del fichero json y eliminados el fichero json.
    json_result = open(f, 'r')

    content = json_result.read()
    jsondecoded = json.loads(content)
    # json_string = json_result.read()
    # print(json_string)
    json_result.close()

    try:
        jsondecoded = jsondecoded['result']
    except:
        pass
    columns = []
    List_of_string_of_prefix = [
        '<head><style> html {font-family: sans-serif;}p{width:85%}'
        ' div { white-space:pre;display: inline-block; width:85%;text-align:center;}div >left {display: inline-block;float:left;width:85px;}div > center {display: inline-block;width:100px;}div > right {display: inline-block;float:right;width:100px;}'
        'details > p {white-space:pre; letter-spacing: 0,5px;font-family: "Courier New"; padding: 4px;  background-color: #eeeeee;  border: none;  box-shadow: 1px 1px 2px #bbbbbb;  cursor: pointer;width:100%;}'
        'p {text-align: justify; letter-spacing: 1px;  font-size: 0.9rem; }'
        'table { white-space:pre; border-collapse: collapse;  border: 2px solid rgb(200,200,200); letter-spacing: 1px;  font-size: 0.8rem;}td, th {border: 1px solid rgb(190,190,190);  padding: 10px 20px;}th {  background-color: rgb(235,235,235);}td {  text-align: center;}tr:nth-child(even) td {  background-color: rgb(250,250,250);}tr:nth-child(odd) td {background-color: rgb(245,245,245);}caption { padding: 10px;}</style></head>']


    try:
        for i in jsondecoded[0].keys():
            columns.append(i)
    except:
        for i in jsondecoded.keys():
            columns.append(i)
    List_of_string_of_prefix.append('<details><summary>Pregunta Sparql</summary><p>')

    for i in range(0, len(jsondecoded)):
        for j in columns:
            for t in dic_of_prefix:
                primer_valor = jsondecoded[i][j]
                segundo_valor = jsondecoded[i][j].replace(t, dic_of_prefix[t] + ':')
                jsondecoded[i][j] = segundo_valor
                if primer_valor != segundo_valor:
                    if dic_of_prefix[t] == "roh-data":
                        string = 'prefix %s : <a href="%s">< %s></a>.</br>' % (dic_of_prefix[t],
                                                                              'https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-data/rdf/roh_data_edma.ttl',
                                                                              t)
                    else:
                        string = 'prefix %s : <a href="%s">< %s></a>.</br>' % (dic_of_prefix[t], t, t)
                    if string not in List_of_string_of_prefix:
                        List_of_string_of_prefix.append(string)

    index_start = f.index('Q')
    index_end = f.index('.')
    id_query_string = f[index_start:index_end]

    if id_query_string + '.sparql' in sparql_files:
        string_sparql_query = ''
        with open(folder_jsons + '/' + id_query_string + '.sparql') as line:
            for linea in line.readlines():
                if linea[0]=="P" or linea[0]=="p":
                    for l in dic_of_prefix:
                        if l in linea:
                            string_aaa ='prefix '+dic_of_prefix[l]+' : <<a href="'+l+'">'+ l+'></a>.</br>'
                            List_of_string_of_prefix.append(string_aaa)
                elif linea=='\n':
                    pass
                else:
                    #linea=linea.replace(' ','&nbsp')
                    string_sparql = ' %s </br>' % linea
                    string_sparql_query=string_sparql_query+ string_sparql
        #string_sparql_query= string_sparql_query +

        List_of_string_of_prefix.append(string_sparql_query)
        List_of_string_of_prefix.append( '</p></details>')
    if id_query_string in dic_of_queries.keys():
        string_query = '<p> %s </p>' % dic_of_queries[id_query_string]
        List_of_string_of_prefix.append(string_query)
    json_in_html = json2html.convert(json=jsondecoded)
    caption = '<table border="1"><caption> %s </caption>' % id_query_string
    json_in_html = json_in_html.replace('<table border="1">', caption, 1)
    cadena = ''
    for i in List_of_string_of_prefix:
        cadena = cadena + i

    json_in_html = cadena + json_in_html
    string='</br><div style = "width:82%" >'
    string=string+'<left > <a href="https://github.com/HerculesCRUE/ROH"> Repositorio Printcipal.</a> </left >'
    string=string + '<center  > <a href="hhttps://github.com/HerculesCRUE/ROH/blob/main/docs/2-%20CoberturaPreguntasCompetencia.pdf"> CoberturaPreguntasCompetencia.pdf.</a> </center>'
    string = string + '<right > <a href="https://github.com/HerculesCRUE/ROH/blob/main/validation-questions/sparql-query/README.md"> Preguntas Sparql y presults.</a> </right>'
    string =string +'</div>'

    json_in_html=json_in_html+string


    # pasamos el string a html.

    # Obtemeos el nombre de la pregunta esparql par apoder denominar a nuestro dichero html con el mismo nombre

    string_destino = fordel_html + '/' + id_query_string + '.html'
    file_html = open(string_destino, "w")
    file_html.write(json_in_html)
    file_html.close()
