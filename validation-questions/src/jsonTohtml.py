from json2html import *
import json
from os import listdir

# Obtngo las capetas donde estan los .json y .sparql y la carpeta destino de los html. 
folder_jsons = sys.argv[1]
fordel_html = sys.argv[2]

# voy a leer el diccionario con los prefijos, con los enunciados de las queries. Ambos son ficheros json. 
prefix = open('prefix.json', 'r')
content = prefix.read()
dic_of_prefix = json.loads(content)
prefix.close()

queries = open('queries.json', 'r')
content = queries.read()
dic_of_queries = json.loads(content)
queries.close()


# Obtener la lista con ficheros .json y .sparql. 
files = listdir(folder_jsons)
json_files = []
sparql_files = []
for i in files:
    if i.endswith(".json"):
        json_files.append(i)
    if i.endswith(".sparql"):
        sparql_files.append(i)
# lista con los ficheros .json con las direcciones completas. 
files = [folder_jsons + '/' + f for f in json_files]


for f in files:
    # Obtenemos el json
    json_result = open(f, 'r')
    content = json_result.read()
    jsondecoded = json.loads(content)
    json_result.close()
    # Puede estar vacio de aqui este try
    try:
        jsondecoded = jsondecoded['result']
    except:
        pass

    # Obtenemos las keys! 
    columns = []
    try:
        for i in jsondecoded[0].keys():
            columns.append(i)
    except:
        for i in jsondecoded.keys():
            columns.append(i)

    index_start = f.index('Q')
    index_end = f.index('.')
    id_query_string = f[index_start:index_end]

    # Este primer string es la configuracion del estilo del html. 
    string_html_file = '<head><style> html {font-family: sans-serif;}p{width:85%}'+' div { white-space:pre;display: inline-block; width:85%;text-align:center;}div >left {display: inline-block;float:left;width:85px;}div > center {display: inline-block;width:100px;}div > right {display: inline-block;float:right;width:100px;}'+ 'details > p {white-space:pre;font-family: "Courier New"; padding: 4px;  background-color: #eeeeee;  border: none;  box-shadow: 1px 1px 2px #bbbbbb;  cursor: pointer;width:100%;}'+'p {text-align: justify; letter-spacing: 1px;  font-size: 0.9rem; }'+ 'table { white-space:pre; border-collapse: collapse;  border: 2px solid rgb(200,200,200); letter-spacing: 1px;  font-size: 0.8rem;}td, th {border: 1px solid rgb(190,190,190);  padding: 10px 20px;}th {  background-color: rgb(235,235,235);}td {  text-align: center;}tr:nth-child(even) td {  background-color: rgb(250,250,250);}tr:nth-child(odd) td {background-color: rgb(245,245,245);}caption { padding: 10px;}</style></head>'
    
    #Lo primero que se va a mostrar es la caja con la pregunta sparql. 
    string_html_file= string_html_file +'<details><summary>Pregunta Sparql</summary><p>'

    # Esta lista contendra cada una de las filas de la caja de la pregunta sparql. 
    string_contenido_pregunta_esparql=[]
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
                    if string not in string_contenido_pregunta_esparql:
                        string_contenido_pregunta_esparql.append(string)


    if id_query_string + '.sparql' in sparql_files:
        string_sparql_query = ''
        with open(folder_jsons + '/' + id_query_string + '.sparql') as line:
            for linea in line.readlines():
                if linea[0]=="P" or linea[0]=="p":
                    for l in dic_of_prefix:
                        if l in linea:
                            string_aaa ='prefix '+dic_of_prefix[l]+' : <<a href="'+l+'">'+ l+'></a>.</br>'
                            string_contenido_pregunta_esparql.append(string_aaa)
                elif linea=='\n':
                    pass
                else:
                    linea=linea.replace('\n','')
                    string_sparql = ' %s </br>' % linea
                    string_sparql_query=string_sparql_query+ string_sparql
        #string_sparql_query= string_sparql_query +

        string_contenido_pregunta_esparql.append(string_sparql_query)
        result_content=''
        for i in string_contenido_pregunta_esparql:
            result_content = result_content + i

        string_html_file= string_html_file + result_content + '</p></details>'

    # llegado aqui hemos terminado la caja de la pregunta sparql.  ahora hay que añadir el contenido
    if id_query_string in dic_of_queries.keys():
        string_query = '<p> %s </p>' % dic_of_queries[id_query_string]
        string_html_file =string_html_file + string_query
    
    # llegado aqui hemos insertado todo menos la tabla y los enlaces de referencia. 
    # cohemos el json de la pregunta y lo convertimos en tabla html!
    json_in_html = json2html.convert(json=jsondecoded)
    # Le pongo el caption con el nombre de la pregunta! 
    caption = '<table border="1"><caption> %s </caption>' % id_query_string
    json_in_html = json_in_html.replace('<table border="1">', caption, 1)
   
    # unimos la tabla con el string del fichero que ya tenemos! 
    json_in_html = string_html_file + json_in_html

    # Por ultimo añadimos los tres enlaces que aparecen abajo! 
    string='</br><div style = "width:82%" >'
    string=string+'<left > <a href="https://github.com/HerculesCRUE/ROH"> Repositorio Principal.</a> </left >'
    string=string + '<center  > <a href="https://github.com/HerculesCRUE/ROH/blob/gh-pages/2-%20CoberturaPreguntasCompetencia.pdf"> CoberturaPreguntasCompetencia.pdf.</a> </center>'
    string = string + '<right > <a href="https://github.com/HerculesCRUE/ROH/blob/main/validation-questions/sparql-query/README.md"> Preguntas Sparql y results.</a> </right>'
    string =string +'</div>'

    # Unimos estos tres enlaces a lo que ya tenemos! 
    json_in_html=json_in_html+string


    

    #Escribiros el estring en html en un fichero html. 

    string_destino = fordel_html + '/' + id_query_string + '.html'
    file_html = open(string_destino, "w")
    file_html.write(json_in_html)
    file_html.close()
