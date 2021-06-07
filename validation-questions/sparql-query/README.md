![](https://github.com/HerculesCRUE/ROH/blob/gh-pages/media/CabeceraDocumentosMD.png)

# Research Domain Ontology (ROH) Validation Questions 

This folder is composed with couples of files with the same name but with different endings, `.sparql`, `.result` and `.html`.
* The `.sparql` file is the query in [SPARQL](https://www.w3.org/TR/rdf-sparql-query/) language, and 
* The `.result` file is the expected result of the execution of the sparql file with the same name, using the data located in the [validation-data/rdf](https://github.com/HerculesCRUE/ROH/tree/main/validation-data/rdf) folder. 
* The `.html` file is the result, in table format, of running the sparql file with the same name, using the data located in the [validation-data/rdf](https://github.com/HerculesCRUE/ROH/tree/main/validation-data/rdf) folder. This file is created automatically, by the [workflow](https://github.com/HerculesCRUE/ROH/blob/main/.github/workflows/widoco-and-validation-questions.yaml) deployed on Github Actions,, when a modification is made.

For more details on how the validation questions are executed in this project or how other validation questions could being executed, the reader is encouraged to read [`3- Ejecución de preguntas de competencia.md`](https://github.com/HerculesCRUE/ROH/blob/main/docs/3-%20Ejecuci%C3%B3n%20de%20preguntas%20de%20competencia.md).

# Validation questions 

The validation questions defined to demonstrate the [ROH ontology](https://github.com/HerculesCRUE/ROH/blob/main/roh/modules/core/roh-core.ttl) usage are shown below. 

* CQ01. Listado de centros de investigación los cuales tiene area de conocimiento `uneskos-individuals:120304`. Se devuelve la URI de dicho centro (columna *centro*) y en caso de que este declarado en el grafo de conocmiento, tambien se devolvera el nombre de dicho centro (columna *centerName*) y el nombre de la universidad a la que pertenece (columna *universityName*). - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q01.html)

* CQ02. Listado de los investigadores (columna *researcher*) que tienen el area de conocimiento `unekos:120304` y cuya posicion (columna *positionClass*) es de `ResearcherPosition` en un centro de investigacion concreto (columna center). - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q02.html)

* CQ3. Devuelve un listado de investigadores (columna *researcher*) con área de investigación `uneskos-individuals:120304`. Por cada investigador se devuelve el número de citas de su CV (columna *n_citas*), la metrica h_index de su CV (columna *h_index*), la métrica i10-index de su CV (columa *i10-index*), el numero de publicaciones en las que ha participado (columna *n_pubicaciones*) y el numero de publicaciones de las que es autor principal (columna *n_corresponding_author*). Los usuarios estan ordenados según la suma de todos estos parametros. Como maximo se devolveran 10 usuarios. - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q03.html)

* CQ4 - Listado de los centros de investigación (columna *center*) que tienen sellos de calidad y el número de sellos que tienen (columna *numberOfSeals*). Solo se muestran los 10 primeros centros con un ordenacion descendiente. - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q04.html)

* CQ05 - Listado de centros de investigacion (columna *centre*) que han partipado en proyectos (columna *proyect*). Ademas se incluira, en caso de que dicho proyecto lo tenga definido en el grafo de conocimiento su clasificacion (columna *classClasificacionProject*). - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q05.html)

* CQ06 - Listado de la producción científica (columna *ResearchObject*) en un determinado rango de fechas (`de 1980 a 2022`) de un centro/estructura de investigación (columna *Organization*) en un área/disciplina  `uneskos-individuals:120304`). Como infromacion adicional se incluyen los siguientes datos: el tipo de archivo que es (columna *researchObjectClass*), autor de correspondencia (columna *author*), fecha (columna  *dateTime*) , titulo (columna title), si esta en un repo se devolvera la URI de dicho repo (columna *repository*), palabras claves del recurso (columna *keyword*)  y doi (columna *doi*) . - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q06.html)

* CQ7A - Listado del numero de articulos publicados (columna *publicationCount*) en revistas de todos los centros de investigacion segun la comunidad autonoma (columna *comunidadAutonoma*) del centro de investigacion que publica.  - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q07A.html)

* CQ7B- Listado del numero de ResearchObject (columna *CountResarchObject*), según la comunidad autónoma del centro al que pertenece (columna *comunidadAutonoma*). - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q07B.html)

* CQ08 - Listado del numero de artículos, en el area de conocimiento `uneskos-individuals:12030`, publicados en revistas (columna *publicationCount*) segun el centro correspondiente (columna *organization*) de dicha organización. Además si dicho centro pertenece a una universidad tambien se obtendra (columna *university*). - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q08.html)

* CQ09 - Listado de patentes (columna *patent*) de un centro/estructura de investigación (columna *centre*) en un área/disciplina (columna *knowledgeArea*). - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q09.html) 

* CQ10 - Listado de los proyectos (columna *project*) adjudicados/desarrollados por un centro/estructura de investigación, (columna *center*)  en el área/disciplina `uneskos-individuals:120304`. Ademas se incluira el rol de dicho centro en dicho projecto (columna *roleClass*).  Como parametros del proyectos opciones se devuelven: el titulo (columna *title*), el estatus del proyectos (columna *classStatus*), si es publico o no (columna *public*), la cuantia del proyecto (columna *cuantia*), el programa de financiacion (columna *FundingProgram*), los ROsa que  se han producido en la realizacion de este rpoyecto.  - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q10.html)

* CQ11-Listado de ResearchObject (columna *RO*) en el area `uneskos-individuals:120304` ordenados desde el mas antiguo al mas actual (columa *dateTimeValue*). Ademas se incluye el tipo de RO que es (columa *researchObjectClass*). -[Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q11.html)

* CQ12 - Listar los proyectos (columna *project*) agrupados por ámbito geográfico (columna *location*). - 
[Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q12.html)

* CQ13- Listado de los documentos (columa *document*) que componen el dossier delproyecto `<http://w3id.org/roh/data#a-project>`. Se devuelve ademas el tipo de documento que es (columa *documentType*). - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q13.html)

* CQ15 - Listar los proyectos (columa *project*) con el mismo subject area o con subjects areas relacionadas por parentesco, mirando en el árbol `uneskos-individuals:1203`.  - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q15.html)

* CQ16A - Listado de los proyectos (columna *project*) en los que un investigador (columna *researcher*) ha intervenido y ademas la organización (columna *organization*)  para la que trabaja en dicho proyecto.  - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q16A.html)

* CQ16B - Listardo de los research objects que son resultado de un proyectos (*project*) en los que ha contribuido un investigador (`investigador-2`). Además se devolveran algunos metadatos de mnera opcional: la organizacion correspondiente de dicho RO (*organization*), el tipo de RO (*researchObjectClass*), el autor princiapl del RO (*author*), titulo del RO (*title*), URI del repositorio en el que esta dicho RO (*repository*), las palabras claves (*keyword*), el doi (*doi*). - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q16B.html)

* CQ18 - Listar las publicaciones (*publication*) publicadas  en una revista (*journal*). Esta publicacion debe contener los siguentes atributos los cuales se devuelven tambien en la pregunta spaql: organizacion correspondiente *organizacion*, autor principal *autor*, numero de citas de dicha publicacion *citationCount*, nombre de la metrica de la publicacion *nameOfMetric*, fecha de publicacion *dateMetricPublication*, nombre del impact factor usado en la metrica de la revista *impactFactorName*, el factor de impacto *impactFacor*, el cuartil *quartile*. - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q18.html)

* CQ20 - Listar las áreas de conocimiento (*knowledgeArea*) tanto de ROs como de proyectos y el numero (*n_kowledgeArea*) de RO y proyectos que tiene dicha area de concomiento. - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q20.html)

* CQ23A- Por cada año (year) que una organizacion ha sido responsable de una publicacion en un area tematica concreta (knowledgeArea) se indicara el numero de publicaciones (CountPublication) de dicha organizacion (`http://w3id.org/roh/data#grupo-investigacion-1`), el numero de investigadores (`researchers`) que en dicho año tenian una posicion de investigadores en dicha organizacion y el ratio (*rate*) de personas por publicación ese año.   - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q23A.html)

* CQ23B -  Por cada año  (year) que una organizacion (`http://w3id.org/roh/data#grupo-investigacion-1`) ha iniciado un proyecto en un area tematica (knowledgeArea) se indicara el numero de projectos (*CountProject*), el numero de investigadores (*researchers*) que en dicho año tenian una posicion de investigadores en dicha organizacion y el ratio (*rate*) de personas por proyecto. - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q23B.html)

* CQ24 - Dada una empresa `http://purl.org/roh_edma/data#company-one` se listan los proyectos (columna *project*) en los que ha colaborado con diversos centros de investigacion (*centre*). - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q24.html)

* CQ25- Listado de las Thesis (thesis) (sean TFG, TFM o PhD)  y el tipo de tesis que es (classThesis), el alumno y el profesor.  - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q25.html)

* CQ26 - Listado de congresos/workshops (*conference*) en los que ha participado un investigador (*researcher*) indicando el rol que ha tenido (*roleClass*). - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q26.html)

* CQ27-Listado de patentes (patent) en las que `http://w3id.org/roh/data#investigador-3` consta como patentInventor.  - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q27.html)

* CQ28- Listado de investigadores  *researcher* que han participado en proyectos *project* y su rol en ellos *roleClass*. - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q28.html)

* CQ29- Listado de recursos en los que `http://w3id.org/roh/data#investigador-1` aparece como autor correspondiente. - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q29.html)

* CQ30A- Listado de start up (*company*) y de sus fundadores (*founder*). - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q30A.html)

* CQ30B- Listado de spinf of (*company*) y de sus fundadores (*founder*). - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q30B.html)

* CQ33 - Dado un periodo de 6 años, se lista el número (*count*) de articulos publicados con un cierto factor de impacto (que debe ser Q3 par arriba). Ademas se devolvera el factor de impactor que es (*nameOfImpactFactor*). Se ha añadido un filtrado en el que exigue que este numero de articulos debe ser mayor que `0`. - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q33.html)

* CQ34 - Listado de los proyectos (*project*)  en estado `PROPOSAL_SUBMITTED` dirigidos por una empresa (*company*) y el funding Amounts (*fundingAmounts*) asociado a cada centro (*center*) involucrado en el proyecto. - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q34.html)

* CQ35A - Listar los documentosde tipo reporte (*report*) asociados a un proyecto (*proyect*). - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q35A.html)

* CQ35B - Listar las fechas (*date*) de justificación de un proyecto. - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q35B.html)

* CQ35C - Listar los gastos (expense), su descripcion (*description*) de un proyecto (*proyect*). - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q35C.html)

* CQ36 - Listado de universidades (*university*) y cuantia que han recibido (*totalFunding*). - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q36.html)

* CQ37 - Listar los proyectos (*proyect*)  en los que  han participado varias organizaciones (*organization*, *otherOrganization*). Los resultados se muestan de dos en dos organizaciones. - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q37.html)

* CQ38- Lista los investigadores (*researcher*) que han realizado PhD y los documentos publicados en revistas (*publication*) de dichos investigadores realizados en fechas posteriores a la publicacion de sus doctorados.  - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q38.html)

* CQ39 - Financiación atraída (*totalFunding*) por todos los investigadores a un centro (*organization*) de un área de conocimiento (*knowledgeArea*) . - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q39.html)

* CQ41- Listado por años (*year*) de inicio de cada proyecto, tematica (*knowledgeArea*) , clasificacion (*classClasificacionProject*) y el numero de proyectos (*CountProject*). - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q41.html)

* CQ42 - Investigadores (*researcher*) que tienen ERCs, Marie Curie, etc. - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q42.html)

* CQ44 - Listado de los proyectos (*project*) en convocatorias competitivas de un grupo de investigación en un rango de años con grupos de investigación (*otherOrganization*) de otras Universidades (*university*). Se realizo la pregunta respecto al grupo `http://w3id.org/roh/data#grupo-investigacion-1`. También se devueleve el *fundingProgram* de los proyectos. - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q44.html)

* CQ45 - Obtener el total de publicaciones de impacto por mujeres y hombres. Se muestra una columna con el *genero* y otra con el *count*. - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q45.html)

* CQ47- Listado de todos los proyectos (*project*) en los que una cierta organizacion `http://purl.org/roh_edma/data#grupo-investigacion-1` no ha participado.   - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q47.html)

* CQ48 - Listado de las citas en `Web Of Science` de un grupo de investigación o instituto de investigación por año. - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q48.html)

* CQ49 - Listado de *publicaciones*, *centro* y el *autor* que ha publicado en una revista. - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q49.html)

* CQ50 - Listar el *impactFactor*, el nombre del impactFactor (*impactFactorName*) y el año (*year*) de cada publiacion del investigador `http://w3id.org/roh/data#investigador-1`. - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q50.html)

* CQ51- Lista los articulos con metricas `jcr` y el año de dicha metrica (*year*) junto con el numero de citas (*citationCount*) desde su poublicacion hasta el momento de la publaicion de dicha metrica. Por cada una de ellas se devuelve ademas el journal en el que ha sido publicado, el quartile y el impactFactor de la revista en la que se publico. - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q51.html)

* CQ52 - pListad de las publicaciones (*publication*) de un centro de investigacion concreto (`http://w3id.org/roh/data#grupo-investigacion-1`) en las que aparezce en el título de la publicación los tokens (great). - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q52.html)

* CQ53-Dado un centro de investigacion (`http://purl.org/roh_edma/data#grupo-investigacion-1`) listar las revista en la que se ha publicado con metrica Q2 (*journal*) y el area de concomiento del articulo (*knowledgeArea*), opcionalmente se devolvera tambien el nombre de la metrica en caso de tenerla (*metricName*). - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q53.html)

* CQ54- Listar el impactor (*impactFactor*) de las metricas `jcr`, es decir cuyo nombre sea JCR, de los investigadores (*researcher*) de un grupo  de investigacion en una revista (*journal*) en un rango de años.  - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q54.html)

* CQ55-Listar los proyects con financiacion privada (*proyect*) obtenidos por un grupo de investigacion (*organization*) contratados por una organizacion privada (*company*). - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q55.html)

* CQ56- Listar las universidades (*university*) en la que se ha realzado una tesis, el tipo de tesis (*ClassThesis*) y la thesis (*Thesis*) , en los que aparezca en el título los tokens (fabulous). - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q56.html)


* CQ58A- Dado un software (`http://w3id.org/roh/data#software-1`) devuelve el readme que tiene asociado.  - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q58A.html)

* Q58B - Dado un repositorio (`http://w3id.org/roh/data#software-1`) devuelve el readme que tiene asociado. - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q58B.html)

* CQ58C- Devuelve todos los readme, el archivo que describe el readme (*readmeOf*) y la clase a la que pertenecen dichos archivo (*classReadmeOf*). - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q58C.html)

* CQ59- Devuelve  el software software, su readme (readme), en caso de estar en un repositorio devuelve la URI del repositorio  (repository ) y el tipo de repositorio que es (classRepository), qen caso de ser producido por un proyecto devuelve le proyecto (project), la organizacion correspondiente  (en caso de ternerla) organization , devuelve el autor (author)   y en caso de que dicho autor halla declarado  un ResearchResult en el que dicho software esta includio tambien se devuelve (ResearchResult). - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q59.html)

* CQ60- Devuelve el software, software, el repositorio (*repository*)  en el que esta dicho software y el tipo de repositorio que es classRepository. 	- [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q60.html)

* CQ61A- Devuelve los ExperimentalProtocol (*experimentalProtocol*) y los resultados que produce (*experimentalProtocolResult*).	- [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q61A.html)

* CQ61B- Devuelve  el *experimentalProtoco*l, en caso de estar en un repositorio devuelve la URI del repositorio  (*repository*) y el tipo de repositorio que es (*classRepository*), en caso de ser producido por un proyecto devuelve le proyecto que lo ha creado (*project*), devuelve la organizacion correspondiente  (en caso de ternerla) *organization* , devuelve el autor (*author*)  y en caso de que dicho autor (el que ha salido en dicha fila) halla declarado  un ResearchResult en el que dicho software esta includio tambien se devuelve (*ResearchResult*)	 - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q61B.html)

* CQ62- Devuelve el repositorio (*repository*) y los repositorios que son fork (*repositoryFork*) de este primero ademas devuelve el tipo de repositorio del que estabamos hablando (*classRepository*). - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q62A.html)


* CQ63A- Fijado el repositorio `http://w3id.org/roh/data#repository-1` devuelve todos los elementos (element) que estan almacenados en dicho repositorio. - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q63A.html)

* CQ63B- Metadatos del repositorio, en los que no estaran incluido los elemtnos que forman parte de dicho repositorio (pregunta Q63A). Se incluye, la organizacion o autor correspondinte *correspondingAuthor*, los autores *author*, el repositorio *repository*, la clase del repositorio *classRepository*.	- [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q63B.html)

* CQ64- Devuelve el RO utilizado de base (*researchObjectBase*), el nuevo RO (*anotherResearchObject*) y la clase de ambos porque se supone que ambos deben ser de la misma clase (*classResearchObject*). - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q64.html)

* CQ65- Devuelve todas las anotaciones anotation con su target (*target*), su body (*body*), su autor *author* y su motivation *motivation*. - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q65.html)

* CQ66- Devuelve una anotacion que ha sido target de otra, es decir que han creado un hilo. Devuelve el target (*target*) de la primera anaotacion (*anotation*), la anotacion, el body (*body*) de esta primera anotación, la URI de la segunda anotacion (*annotation2*)  y su body (*body2*). - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q66.html)
	
* CQ68A - Recursos citados por el articulo `http://w3id.org/roh/data#journal-article-1`. - [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q68A.html)

* CQ68B- Los ResearchResult *researchResult* que ha realizado una autor y los recursos resource  que contiene. 	- [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q68B.html)

* CQ69- Devuelve las anotaciones (*annotation*) realizadas sobre un ResearchResult (*researchResult*) que no es un ResearchObject. Devuelve tambien el body (*body*) de dicha annotacion.	- [Link](https://htmlpreview.github.io/?https://raw.githubusercontent.com/HerculesCRUE/ROH/main/validation-questions/html/Q69.html)

