import json
from os import listdir
import sparqlwrapper
nombre=[f for f in listdir('C:/Users/mpuer/Documents/GitHub/ROH_oficial/validation-questions/json/')]
files = ['C:/Users/mpuer/Documents/GitHub/ROH_oficial/validation-questions/json/' + f for f in nombre]
for m in range(0,len(files)):
    f=files[m]
    sol=[]
    doc = open(f, "r")
    content = doc.read()
    docc = json.loads(content)
    print(docc)
    parametros = docc['head']['vars']
    columnas= len(docc['results']['bindings'])

    for i in range(0,columnas):
        element={}
        for j in range(0,len(parametros)):
            element[parametros[j]]= docc['results']['bindings'][i][parametros[j]]['value']
        print(element)
        sol.append(element)
    final={}
    final['result']=sol
    with open('C:/Users/mpuer/Documents/GitHub/ROH_oficial/validation-questions/result/'+nombre[m], 'w') as outfile:

        json.dump(final, outfile)
