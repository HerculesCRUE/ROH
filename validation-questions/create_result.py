import json
from os import listdir
from SPARQLWrapper import SPARQLWrapper, JSON

nombre = [f for f in listdir('C:/Users/mpuer/Documents/GitHub/ROH_oficial/validation-questions/sparql-query-edma/')]

files = ['C:/Users/mpuer/Documents/GitHub/ROH_oficial/validation-questions/sparql-query-edma/' + f for f in nombre][:-1]
for m in range(0, len(files)):
    f = files[m]
    doc = open(f, "r")
    content = doc.read()
    sparql = SPARQLWrapper("http://localhost:3030/edma/query")
    sparql.setQuery(content)
    sparql.setReturnFormat(JSON)
    results = sparql.query().convert()

    print(results)
    parametros = results['head']['vars']
    columnas = len(results['results']['bindings'])
    sol = []
    for i in range(0, columnas):
        element = {}
        for j in range(0, len(parametros)):
            try:
                element[parametros[j]] = results['results']['bindings'][i][parametros[j]]['value']
            except:
                element[parametros[j]] = ''
        sol.append(element)
    final = {}
    final['result'] = sol
    with open('C:/Users/mpuer/Documents/GitHub/ROH_oficial/validation-questions/result/' + nombre[m][:-7] + '.json',
              'w') as outfile:

        json.dump(final, outfile)
