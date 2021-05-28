![](../Docs/media/CabeceraDocumentosMD.png)

#  Validation Questions

In this project different SPARQL queries are executed in order to test the ROH ontology. Those [validations questions](https://github.com/HerculesCRUE/GnossDeustoOnto/blob/master/Documentation/2-%20CoberturaPreguntasCompetencia.pdf) can be reviewed by selecting the sub-folder [sparql-query](https://github.com/HerculesCRUE/ROH/tree/main/validation-questions/sparql-query). The next two items point to the JavaDoc documentation generated from the implemented tests and to a page reporting details about the tests executed through Maven plugin surefire: 

* [Documentation](https://deustohercules.github.io/validation-questions/testapidocs/index.html)
* [Test Results](https://deustohercules.github.io/validation-questions/surefire-report.html)

Remember to play with the validation questions by interacting with the example queries at [`sparql-query`](https://github.com/HerculesCRUE/ROH/tree/main/validation-questions/sparql-query) directory.


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

Add required submodules, GnossDeustoOnto (ontology) and pellet (reasoner):

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

Add required submodules, GnossDeustoOnto (ontology) and pellet (reasoner):

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