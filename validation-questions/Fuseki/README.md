![](https://github.com/HerculesCRUE/ROH/blob/gh-pages/media/CabeceraDocumentosMD.png)

# Fuseki

The purpose of these [validation questions](https://github.com/HerculesCRUE/ROH/blob/main/docs/2-%20CoberturaPreguntasCompetencia.pdf) is to demonstrate the use of the ROH ontology. The [workflow](https://github.com/HerculesCRUE/ROH/blob/main/.github/workflows/widoco-and-validation-questions.yaml) that implements this repository, verifies that the expected result of each [sparql question](https://github.com/HerculesCRUE/ROH/tree/main/validation-questions/sparql-query/) is the same as the actual result. This is a verification system but it does not allow to execute new sparql queries dynamically. For this, it is recommended to install Fuseki locally and leave the Github action as a verification system. 

The next section describe how install Fuseki to do this task.

## Fuseki with ROH. 

1. At [Apache Jena dowload](https://jena.apache.org/download/) the reader can dowloader Fuseki. Recomended version 4.0.0. Dowload apache-jena-fuseki-4.0.0.tar.gz in Linux or apache-jena-fuseki-4.0.0.zip in windowns. Unzip this file, name the folder "Fuseki" and save it in the root directory of the computer (`C\`).
```
wget -c hhttps://ftp.cixug.es/apache/jena/binaries/apache-jena-4.1.0.tar.gz -O - | tar -xz
```

2. Clone this repository and dowload. 
```
git clone https://github.com/HerculesCRUE/ROH
```

3. Unzziped [extra.rar](https://github.com/HerculesCRUE/ROH/blob/main/validation-questions/Fuseki/extra.rar) and copy the files in this folder in `Fuseki/run/`.
 
4. Copy [edma.ttl](https://github.com/HerculesCRUE/ROH/blob/main/validation-questions/Fuseki/edma.ttl) to the local folder `Fuseki/configuration/` and open in a editor. Edit `path`  with the local path of the copy of this reposotory in the nexts lines:
```  
ja:content [ja:externalContent <file:path/ROH_oficial/validation-data/rdf/roh_data_edma.ttl>,  <http://w3id.org/roh/unesco-individuals#>, <https://sws.geonames.org/3128026/> ] ;

ja:content [ja:externalContent  <file:path/ROH_oficial/roh/modules/core/roh-core.ttl> ];
```
5.  Open `fuseki-serve.bat` in a editor and coment next line:

```
REM java ..jvmarsg... -cp $JAR org.apache.jena.fuseki.cmd.FusekiCmd %*
```

6. Open `fuseki-serve.bat` in a editor and add this line below 

```
java -cp fuseki-server.jar;run/extra/jaxb-api-2.3.0.jar;run/extra/jgrapht-ext-1.1.0.jar;run/extra/jgrapht-core-1.1.0.jar;run/extra/original-openllet-distribution-2.6.5.jar org.apache.jena.fuseki.cmd.FusekiCmd %*
```

8. Execute `fuseki-serve.bat` and go to http://localhost:3030/.

For more information about Fuseki, see the official documentation [Apache Jena](https://jena.apache.org/documentation/fuseki2/).



