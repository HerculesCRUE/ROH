![](https://github.com/HerculesCRUE/ROH/blob/gh-pages/media/CabeceraDocumentosMD.png)

# Fuseki

The purpose of these [validation questions](https://github.com/HerculesCRUE/ROH/blob/main/docs/2-%20CoberturaPreguntasCompetencia.pdf) is to demonstrate the use of the ROH ontology. The [workflow](https://github.com/HerculesCRUE/ROH/blob/main/.github/workflows/widoco-and-validation-questions.yaml) that implements this repository, verifies that the expected result of each [sparql question](https://github.com/HerculesCRUE/ROH/tree/main/validation-questions/sparql-query/) is the same as the actual result. This is a verification system but it does not allow to execute new sparql queries dynamically. For this, it is recommended to install Fuseki locally and leave the Github action as a verification system. 

The next section describe how install Fuseki to do this task.

## Fuseki with ROH. 

1. At oficial web of Apache Jena, [Apache Jena download](https://jena.apache.org/download/), download Fuseki. Recommended version  is 4.0.0. Download apache-jena-fuseki-4.0.0.tar.gz in Linux or apache-jena-fuseki-4.0.0.zip in Windowns and unpacked. 
    * Set the environment variable `FUSEKI_HOME` to point to an unpacked Fuseki distribution. 

2. Clone this repository and download. 
```
git clone https://github.com/HerculesCRUE/ROH
```

3. The reasoner used is `openllet` and for this reason are needed some libreries. In this [pom.xml](https://github.com/Galigator/openllet/blob/2.6.5/pom.xml) you can find the libraries you may need to run Fuseki. These libraries must be stored at `run/extra/` in the unpacked folder of Fuseki. In our case the libraries needed were: jaxb-api-2.3.0.jar, jgrapht-core-1.1.0.jar, jgrapht-ext-1.1.0.jar and original-openllet-distribution-2.6.5.jar. But if you need any more, download and stored at [maven centrar](https://search.maven.org/). 

<!-- Unzip [extra.rar](https://github.com/HerculesCRUE/ROH/blob/main/validation-questions/Fuseki/extra.rar) and copy the files in this folder to `Fuseki/run/`.--> 
 

4. Copy [edma.ttl](https://github.com/HerculesCRUE/ROH/blob/main/validation-questions/Fuseki/edma.ttl) to the local folder `configuration/` in your unpacked folder of Fuseki, and open in an editor. This file is the configuration of the data. Edit `path` with your local path in the next lines:
```  
ja:content [ja:externalContent <file:path/ROH_oficial/validation-data/rdf/roh_data_edma.ttl>,  <http://w3id.org/roh/unesco-individuals#>, <https://sws.geonames.org/3128026/> ] ;

ja:content [ja:externalContent  <file:path/ROH_oficial/roh/modules/core/roh-core.ttl> ];
```

5.  Open `fuseki-server.bat` in an editor and comment next line:
```
REM java ..jvmarsg... -cp $JAR org.apache.jena.fuseki.cmd.FusekiCmd %*
```

6. Open `fuseki-server.bat` in an editor and add this line below. In case you need more libraries add the relative path of this file in the following line as we did with the libraries we needed. 
```
java -cp fuseki-server.jar;run/extra/jaxb-api-2.3.0.jar;run/extra/jgrapht-ext-1.1.0.jar;run/extra/jgrapht-core-1.1.0.jar;run/extra/original-openllet-distribution-2.6.5.jar org.apache.jena.fuseki.cmd.FusekiCmd %*
```

8. Execute `fuseki-server.bat` and go to http://localhost:3030/.


For more information about Fuseki, see the official documentation at [Apache Jena](https://jena.apache.org/documentation/fuseki2/).

