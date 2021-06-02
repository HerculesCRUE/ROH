![](.//media/CabeceraDocumentosMD.png)

# Fuseki

The goal of the [validation questions](https://github.com/HerculesCRUE/ROH/blob/main/docs/2-%20CoberturaPreguntasCompetencia.pdf) is to verify the correct modeling performed by the [ROH ontology](https://github.com/HerculesCRUE/ROH/blob/main/roh/modules/core/roh-core.ttl). The [sparql questions](https://github.com/HerculesCRUE/ROH/tree/main/validation-questions/sparql-query/) are executed, through a [workflow](https://github.com/HerculesCRUE/ROH/blob/main/.github/workflows/widoco-and-validation-questions.yaml), each time a modification is made in the repository. However, this is a verification system, and does not allow a dynamic execution of sparql questions. In case you want to perform sparql queries dynamically, it is recommended to install Fuseki locally and leave the Github Action as a verification system. 

# Fuseki with ROH. 

1. At [Apache Jena dowload](https://jena.apache.org/download/) the reader can dowloader Fuseki. Recomended version 4.0.0. Dowload apache-jena-fuseki-4.0.0.tar.gz in Linux or apache-jena-fuseki-4.0.0.zip in windowns. 

2. Unzip this file, name the folder "Fuseki" and save it in the root directory of the computer (`C\`). 

3. Clone this repository and dowload. 
```
git clone https://github.com/HerculesCRUE/ROH
```

4. Unzziped  [extra.rar](https://github.com/HerculesCRUE/ROH/blob/main/validation-questions/Fuseki/extra.rar) in `Fuseki/run/`.
 
5. Copy [edma.ttl](https://github.com/HerculesCRUE/ROH/blob/main/validation-questions/Fuseki/edma.ttl) to the local folder `Fuseki/configuration/` and open this file in a editor. Edit `path` in the nexts lines:

```  
ja:content [ja:externalContent <file:path/ROH_oficial/validation-data/rdf/roh_data_edma.ttl>,  <http://w3id.org/roh/unesco-individuals#>, <https://sws.geonames.org/3128026/> ] ;

ja:content [ja:externalContent  <file:path/ROH_oficial/roh/modules/core/roh-core.ttl> ];
```
6.  Open `fuseki-serve.bat` in a editor and coment next line:

```
REM java ..jvmarsg... -cp $JAR org.apache.jena.fuseki.cmd.FusekiCmd %*
```

7. Open `fuseki-serve.bat` in a editor and add this below 

```
java -cp fuseki-server.jar;run/extra/jaxb-api-2.3.0.jar;run/extra/jgrapht-ext-1.1.0.jar;run/extra/jgrapht-core-1.1.0.jar;run/extra/original-openllet-distribution-2.6.5.jar org.apache.jena.fuseki.cmd.FusekiCmd %*
```

8. Execute `fuseki-serve.bat` and go to http://localhost:3030/.

For more information about Fuseki, see the official documentation [Apache Jena](https://jena.apache.org/documentation/fuseki2/).



