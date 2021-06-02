![](.//media/CabeceraDocumentosMD.png)

#  Validation Questions

In this project, different SPARQL queries are executed to test the ROH ontology. These [validation questions](https://github.com/HerculesCRUE/ROH/blob/main/docs/2-%20CoberturaPreguntasCompetencia.pdf) are located in the [validation-questions/sparql-query](https://github.com/HerculesCRUE/ROH/tree/main/validation-questions/sparql-query/) folder. This folder is composed of couples of files with the same name but with different endings, `.sparql`, `.result` and `.html`.
* The `.sparql` file is the query in [SPARQL](https://www.w3.org/TR/rdf-sparql-query/) language, and 
* The `.result` file is the expected result of the execution of the sparql file with the same name, using the data located in the [validation-data/rdf](https://github.com/HerculesCRUE/ROH/tree/main/validation-data/rdf) folder. 
* The `.html` file is the result, in a table format, of running the sparql file with the same name, using the data located in the [validation-data/rdf](https://github.com/HerculesCRUE/ROH/tree/main/validation-data/rdf) folder. This file is created automatically, by the [workflow](https://github.com/HerculesCRUE/ROH/blob/main/.github/workflows/widoco-and-validation-questions.yaml) deployed on Github Actions, when a modification is made.

Each group of three files with the same name allows, through the workflow deployed in GitHub Actions, to check one validation question.


# The execution of validation questions

The execution of [validation questions](https://github.com/HerculesCRUE/ROH/blob/main/docs/2-%20CoberturaPreguntasCompetencia.pdf) is deployed in Github Actions and allows the automatic verification of each sparql query each time a modification is made. For each validation question, [this workflow](https://github.com/HerculesCRUE/ROH/blob/main/.github/workflows/widoco-and-validation-questions.yaml) executes the corresponding sparql query. This result is compared to the result file with the same name as the sparql query. If they are the same this query passes since the expected result and the obtained result are the same. 
Also, this workflow generates the [Widoco 
documentation](https://herculescrue.github.io/ROH/roh/) of the ROH ontology.  

# Run new validation questions

The goal of the [validation questions](https://github.com/HerculesCRUE/ROH/blob/main/docs/2-%20CoberturaPreguntasCompetencia.pdf) is to verify the correct modeling performed by the [ROH ontology](https://github.com/HerculesCRUE/ROH/blob/main/roh/modules/core/roh-core.ttl). The [sparql questions](https://github.com/HerculesCRUE/ROH/tree/main/validation-questions/sparql-query/) are executed, through a [workflow](https://github.com/HerculesCRUE/ROH/blob/main/.github/workflows/widoco-and-validation-questions.yaml), each time a modification is made in the repository. However, this is a verification system, and does not allow a dynamic execution of sparql questions. If it's desired perform sparql queries dynamically, it is recommended to install Fuseki locally and leave the Github Action as a verification system. For more information about hoy to install Fuseki to use with ROH, readers are recommended to visit [How use Fuseki with ROH]()



<!-- The next two items point to the JavaDoc documentation generated from the implemented tests and to a page reporting details about the tests executed through Maven plugin surefire: -->
<!--
* [Documentation](https://deustohercules.github.io/validation-questions/testapidocs/index.html)
* [Test Results](https://deustohercules.github.io/validation-questions/surefire-report.html)
-->



<!--
# USAGE WITH Github Actions 

Download the repository and cd to validation-questions path:

```
$ git clone https://github.com/HerculesCRUE/ROH
$ cd ROH/validation-questions
```


-->


<!--
# USAGE

Asumptions: it requires to have installed the following tools:
- git client
- mvn tool
- jdk 8.0

If you do not want to install neither Java or Maven in your system, please go to section entitled Docker, where how to lauch a container including these tools is explained. 

Download the repository and cd to validation-questions path:

```
$ git clone https://github.com/HerculesCRUE/ROH
$ cd ROH/validation-questions
```

Add required submodules, ROH (ontology) and pellet (reasoner):

```
$ git submodule add https://github.com/HerculesCRUE/ROH
$ git submodule add https://github.com/stardog-union/pellet/
$ git submodule init
$ git submodule update
```

Install pellet reasoner:

```
$ cd pellet
$ mvn install
```

Go back and execute tests:

```
$ cd ..
$ mvn compile
$ mvn test -DontFile=https://raw.githubusercontent.com/HerculesCRUE/ROH/main/roh/modules/core/roh-core.ttl -DdataFile=https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-data/rdf/roh_data_edma.ttl  -Duneskos=https://raw.githubusercontent.com/HerculesCRUE/ROH/main/mirror/uneskos/unesco-individuals.rdf
```
# Docker

If you want to create your Java environment at Docker, you could follow next steps:

Download the repository and cd to validation-questions path:

```
$ git clone https://github.com/HerculesCRUE/ROH
$ cd ROH/validation-questions
```

Add required submodules, ROH (ontology) and pellet (reasoner):

```
$ git submodule add https://github.com/HerculesCRUE/ROH
$ git submodule add https://github.com/stardog-union/pellet/
$ git submodule init
$ git submodule update
```

Create a Docker container:

```
cd </path/to/ROH/validation-questions>
$ docker run --rm -ti -v ${PWD}:/source maven:3-jdk-8 /bin/bash
```

In Windows, you may use the following command instead:

```
$ docker run --rm -ti -v %cd%:/source maven:3-jdk-8 /bin/bash
```

From the container, execute the following commands to install pellet:

```
$ cd /source/pellet
$ mvn install
```

Go back and execute tests:

```
$ cd ..
$ mvn compile
$ mvn test -DontFile=https://raw.githubusercontent.com/HerculesCRUE/ROH/main/roh/modules/core/roh-core.ttl -DdataFile=https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-data/rdf/roh_data_edma.ttl  -Duneskos=https://raw.githubusercontent.com/HerculesCRUE/ROH/main/mirror/uneskos/unesco-individuals.rdf
```

# Deployment on GitHub Actions

This project could be deployed on Github Actions to enable the automatic generation of the tests when a modification is done. To do that, the following steps must be followed:

1. The owner of the repository must [create its Personal Access Token](https://help.github.com/en/github/authenticating-to-github/creating-a-personal-access-token-for-the-command-line).
2. [A secret must be created in the repository](https://help.github.com/en/actions/configuring-and-managing-workflows/creating-and-storing-encrypted-secrets) with the content of the Personal Access Token.
3. A workflow file must be created under `.github/workflows` folder. 

When the workflow is configured, it will be launched every time the repository is updated.


Remember to play with the validation questions by interacting with the example queries at [`sparql-query`](https://github.com/HerculesCRUE/ROH/tree/main/validation-questions/sparql-query) directory.
-->