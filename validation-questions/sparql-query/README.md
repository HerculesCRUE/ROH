![](https://github.com/HerculesCRUE/ROH/blob/gh-pages/media/CabeceraDocumentosMD.png)

# ROH
Research Domain Ontology (ROH) Competence Questions 

Some exemplary competence questions are shown below, which demonstrate the ROH ontology usage are shown below:
* CQ01. Listado de centros de investigación los cuales tiene area de conocimiento `uneskos:120304`. Se devuelve la URI de dicho centro (columna *centro*) y en caso de que este declarado en el grafo de conocmiento, tambien se devolvera el nombre de dicho centro (columna *centerName*) y el nombre de la universidad a la que pertenece (columna *universityName*). -  LINK.
* CQ02. Listado de los investigadores (columna *researcher*) que tienen el area de conocimiento `unekos:120304` y cuya posicion (columna *positionClass*) es de `ResearcherPosition` en un centro de investigacion concreto (columna center). - LINK
* CQ3. Devuelve un listado de investigadores (columna *researcher*) con área de investigación `uneskos:120304`. Por cada investigador se devuelve el número de citas de su CV (columna *n_citas*), la metrica h_index de su CV (columna *h_index*), la métrica i10-index de su CV (columa *i10-index*), el numero de publicaciones en las que ha participado (columna *n_pubicaciones*) y el numero de publicaciones de las que es autor principal (columna *n_corresponding_author*). Los usuarios estan ordenados según la suma de todos estos parametros. Como maximo se devolveran 10 usuarios. 
* CQ4 - Listado de los centros de investigación (columna *center*) que tienen sellos de calidad y el número de sellos que tienen (columna *numberOfSeals*). Solo se muestran los 10 primeros centros con un ordenacion descendiente.
* CQ05 - Listado de centros de investigacion (columna *centre*) que han partipado en proyectos (columna *proyect*). Ademas se incluira, en caso de que dicho proyecto lo tenga definido en el grafo de conocimiento su clasificacion (columna *classClasificacionProject*). 
* CQ06 - Listado de la producción científica (columna *ResearchObject*) en un determinado rango de fechas (`de 1980 a 2022`) de un centro/estructura de investigación (columna *Organization*) en un área/disciplina  `uneskos:120304`). Como infromacion adicional se incluyen los siguientes datos: el tipo de archivo que es (columna *researchObjectClass*), autor de correspondencia (columna *author*), fecha (columna  *dateTime*) , titulo (columna title), si esta en un repo se devolvera la URI de dicho repo (columna *repository*), palabras claves del recurso (columna *keyword*)  y doi (columna *doi*) .
* CQ7A - Listado del numero de articulos publicados (columna *publicationCount*) en revistas de todos los centros de investigacion segun la comunidad autonoma (columna *comunidadAutonoma*) del centro de investigacion que publica. 
* CQ7B- Listado del numero de ResearchObject (columna *CountResarchObject*), según la comunidad autónoma del centro al que pertenece (columna *comunidadAutonoma*).
* CQ08 - Listado del numero de artículos, en el area de conocimiento `uneskos:12030`, publicados en revistas (columna *publicationCount*) segun el centro correspondiente (columna *organization*) de dicha organización. Además si dicho centro pertenece a una universidad tambien se obtendra (columna *university*).
* CQ09 - Listado de patentes (columna *patent*) de un centro/estructura de investigación (columna *centre*) en un área/disciplina (columna *knowledgeArea*).
* CQ10 - Listado de los proyectos (columna *project*) adjudicados/desarrollados por un centro/estructura de investigación, (columna *center*)  en el área/disciplina `uneskos:120304`. Ademas se incluira el rol de dicho centro en dicho projecto (columna *roleClass*).  Como parametros del proyectos opciones se devuelven: el titulo (columna *title*), el estatus del proyectos (columna *classStatus*), si es publico o no (columna *public*), la cuantia del proyecto (columna *cuantia*), el programa de financiacion (columna *FundingProgram*), los ROsa que  se han producido en la realizacion de este rpoyecto. 
* CQ11-Listado de ResearchObject (columna *RO*) en el area `uneskos:120304` ordenados desde el mas antiguo al mas actual (columa *dateTimeValue*). Ademas se incluye el tipo de RO que es (columa *researchObjectClass*). 
* CQ12 - Listar los proyectos (columna *project*) agrupados por ámbito geográfico (columna *location*).
* CQ13- Listado de los documentos (columa *document*) que componen el dossier delproyecto `<http://w3id.org/roh/data#a-project>`. Se devuelve ademas el tipo de documento que es (columa *documentType*).
* 
* CQ15 - Listar los proyectos (columa *project*) con el mismo subject area o con subjects areas relacionadas por parentesco, mirando en el árbol `uneskos:1203`.  
* CQ16A - Listado de los proyectos (columna *project*) en los que un investigador (columna *researcher*) ha intervenido y ademas la organización (columna *organization*)  para la que trabaja en dicho proyecto.   
* CQ16B - Listardo de los research objects que son resultado de un proyectos (*project*) en los que ha contribuido un investigador (`investigador-2`). Además se devolveran algunos metadatos de mnera opcional: la organizacion correspondiente de dicho RO (*organization*), el tipo de RO (*researchObjectClass*), el autor princiapl del RO (*author*), titulo del RO (*title*), URI del repositorio en el que esta dicho RO (*repository*), las palabras claves (*keyword*), el doi (*doi*).
* CQ18 - Listar las publicaciones (*publication*) publicadas  en una revista (*journal*). Esta publicacion debe contener los siguentes atributos los cuales se devuelven tambien en la pregunta spaql: organizacion correspondiente *organizacion*, autor principal *autor*, numero de citas de dicha publicacion *citationCount*, nombre de la metrica de la publicacion *nameOfMetric*, fecha de publicacion *dateMetricPublication*, nombre del impact factor usado en la metrica de la revista *impactFactorName*, el factor de impacto *impactFacor*, el cuartil *quartile*.
* CQ20 - Listar las áreas de conocimiento (*knowledgeArea*) tanto de ROs como de proyectos y el numero (*n_kowledgeArea*) de RO y proyectos que tiene dicha area de concomiento.
* CQ23A- Por cada año (year) que una organizacion ha sido responsable de una publicacion en un area tematica concreta (knowledgeArea) se indicara el numero de publicaciones (CountPublication) de dicha organizacion (`http://w3id.org/roh/data#grupo-investigacion-1`), el numero de investigadores (`researchers`) que en dicho año tenian una posicion de investigadores en dicha organizacion y el ratio (*rate*) de personas por publicación ese año.  
* CQ23B -  Por cada año  (year) que una organizacion (`http://w3id.org/roh/data#grupo-investigacion-1`) ha iniciado un proyecto en un area tematica (knowledgeArea) se indicara el numero de projectos (*CountProject*), el numero de investigadores (*researchers*) que en dicho año tenian una posicion de investigadores en dicha organizacion y el ratio (*rate*) de personas por proyecto.
* CQ24 - Dada una empresa `http://purl.org/roh_edma/data#company-one` se listan los proyectos (columna *project*) en los que ha colaborado con diversos centros de investigacion (*centre*).
* CQ25- Listado de las Thesis (thesis) (sean TFG, TFM o PhD)  y el tipo de tesis que es (classThesis), el alumno y el profesor. 
* CQ26 - Listado de congresos/workshops (*conference*) en los que ha participado un investigador (*researcher*) indicando el rol que ha tenido (*roleClass*).
* CQ27-Listado de patentes (patent) en las que `http://w3id.org/roh/data#investigador-3` consta como patentInventor. 
* CQ28- Listado de investigadores  *researcher* que han participado en proyectos *project* y su rol en ellos *roleClass*.
* CQ29- Listado de recursos en los que `http://w3id.org/roh/data#investigador-1` aparece como autor correspondiente. 
* CQ30A- Listado de start up (*company*) y de sus fundadores (*founder*).
* CQ30B- Listado de spinf of (*company*) y de sus fundadores (*founder*).
* CQ33 - Dado un periodo de 6 años, se lista el número (*count*) de articulos publicados con un cierto factor de impacto (que debe ser Q3 par arriba). Ademas se devolvera el factor de impactor que es (*nameOfImpactFactor*). Se ha añadido un filtrado en el que exigue que este numero de articulos debe ser mayor que `0`. 
* CQ34 - Listado de los proyectos (*project*)  en estado `PROPOSAL_SUBMITTED` dirigidos por una empresa (*company*) y el funding Amounts (*fundingAmounts*) asociado a cada centro (*center*) involucrado en el proyecto.
* CQ35A - Listar los documentosde tipo reporte (*report*) asociados a un proyecto (*proyect*).
* CQ35B - Listar las fechas (*date*) de justificación de un proyecto.
* CQ35C - Listar los gastos (expense), su descripcion (*description*) de un proyecto (*proyect*).
* CQ36 - Listado de universidades (*university*) y cuantia que han recibido (*totalFunding*).
* CQ37 - Listar los proyectos (*proyect*)  en los que  han participado varias organizaciones (*organization*, *otherOrganization*). Los resultados se muestan de dos en dos organizaciones.
* CQ38- Lista los investigadores (*researcher*) que han realizado PhD y los documentos publicados en revistas (*publication*) de dichos investigadores realizados en fechas posteriores a la publicacion de sus doctorados. 
* CQ39 - Financiación atraída (*totalFunding*) por todos los investigadores a un centro (*organization*) de un área de conocimiento (*knowledgeArea*) .
* CQ41- Listado por años (*year*) de inicio de cada proyecto, tematica (*knowledgeArea*) , clasificacion (*classClasificacionProject*) y el numero de proyectos (*CountProject*).
* CQ42 - Investigadores (*researcher*) que tienen ERCs, Marie Curie, etc.
* CQ44 - Listado de los proyectos (*project*) en convocatorias competitivas de un grupo de investigación en un rango de años con grupos de investigación (*otherOrganization*) de otras Universidades (*university*). Se realizo la pregunta respecto al grupo `http://w3id.org/roh/data#grupo-investigacion-1`. También se devueleve el *fundingProgram* de los proyectos.ç
* CQ45 - Obtener el total de publicaciones de impacto por mujeres y hombres. Se muestra una columna con el *genero* y otra con el *count*.
* CQ47- Listado de todos los proyectos (*project*) en los que una cierta organizacion `http://purl.org/roh_edma/data#grupo-investigacion-1` no ha participado.  
* CQ48 - Listado de las citas en `Web Of Science` de un grupo de investigación o instituto de investigación por año.
* CQ49 - Listado de *publicaciones*, *centro* y el *autor* que ha publicado en una revista. 
* CQ50 - Listar el *impactFactor*, el nombre del impactFactor (*impactFactorName*) y el año (*year*) de cada publiacion del investigador `http://w3id.org/roh/data#investigador-1`.
* CQ51- Lista los articulos con metricas `jcr` y el año de dicha metrica (*year*) junto con el numero de citas (*citationCount*) desde su poublicacion hasta el momento de la publaicion de dicha metrica. Por cada una de ellas se devuelve ademas el journal en el que ha sido publicado, el quartile y el impactFactor de la revista en la que se publico.
* CQ52 - pListad de las publicaciones (*publication*) de un centro de investigacion concreto (`http://w3id.org/roh/data#grupo-investigacion-1`) en las que aparezce en el título de la publicación los tokens (great).
* CQ53-Dado un centro de investigacion (`http://purl.org/roh_edma/data#grupo-investigacion-1`) listar las revista en la que se ha publicado con metrica Q2 (*journal*) y el area de concomiento del articulo (*knowledgeArea*), opcionalmente se devolvera tambien el nombre de la metrica en caso de tenerla (*metricName*).
* CQ54- Listar el impactor (*impactFactor*) de las metricas `jcr`, es decir cuyo nombre sea JCR, de los investigadores (*researcher*) de un grupo  de investigacion en una revista (*journal*) en un rango de años.  
* CQ55-Listar los proyects con financiacion privada (*proyect*) obtenidos por un grupo de investigacion (*organization*) contratados por una organizacion privada (*company*).
* CQ56- Listar las universidades (*university*) en la que se ha realzado una tesis, el tipo de tesis (*ClassThesis*) y la thesis (*Thesis*) , en los que aparezca en el título los tokens (fabulous). 


* CQ58- Dado un software (`http://w3id.org/roh/data#software-1`) devuelve el readme que tiene asociado. 
* Q58B - Dado un repositorio (`http://w3id.org/roh/data#software-1`) devuelve el readme que tiene asociado.
* CQ58C- Devuelve todos los readme, el archivo que describe el readme (*readmeOf*) y la clase a la que pertenecen dichos archivo (*classReadmeOf*).
* CQ59- Devuelve  el software software, su readme (readme), en caso de estar en un repositorio devuelve la URI del repositorio  (repository ) y el tipo de repositorio que es (classRepository), qen caso de ser producido por un proyecto devuelve le proyecto (project), la organizacion correspondiente  (en caso de ternerla) organization , devuelve el autor (author)   y en caso de que dicho autor halla declarado  un ResearchResult en el que dicho software esta includio tambien se devuelve (ResearchResult).
* CQ60- Devuelve el software, software, el repositorio (*repository*)  en el que esta dicho software y el tipo de repositorio que es classRepository. 	
* CQ61A- Devuelve los ExperimentalProtocol (*experimentalProtocol*) y los resultados que produce (*experimentalProtocolResult*).	
* CQ61B- Devuelve  el *experimentalProtoco*l, en caso de estar en un repositorio devuelve la URI del repositorio  (*repository*) y el tipo de repositorio que es (*classRepository*), en caso de ser producido por un proyecto devuelve le proyecto que lo ha creado (*project*), devuelve la organizacion correspondiente  (en caso de ternerla) *organization* , devuelve el autor (*author*)  y en caso de que dicho autor (el que ha salido en dicha fila) halla declarado  un ResearchResult en el que dicho software esta includio tambien se devuelve (*ResearchResult*)	
* CQ62- Devuelve el repositorio (*repository*) y los repositorios que son fork (*repositoryFork*) de este primero ademas devuelve el tipo de repositorio del que estabamos hablando (*classRepository*).

* CQ63A- Fijado el repositorio `http://w3id.org/roh/data#repository-1` devuelve todos los elementos (element) que estan almacenados en dicho repositorio.	
* CQ63B- Metadatos del repositorio, en los que no estaran incluido los elemtnos que forman parte de dicho repositorio (pregunta Q63A). Se incluye, la organizacion o autor correspondinte *correspondingAuthor*, los autores *author*, el repositorio *repository*, la clase del repositorio *classRepository*.	
* CQ64- Devuelve el RO utilizado de base (*researchObjectBase*), el nuevo RO (*anotherResearchObject*) y la clase de ambos porque se supone que ambos deben ser de la misma clase (*classResearchObject*).
* CQ65- Devuelve todas las anotaciones anotation con su target (*target*), su body (*body*), su autor *author* y su motivation *motivation*. 	
* CQ66- Devuelve una anotacion que ha sido target de otra, es decir que han creado un hilo. Devuelve el target (*target*) de la primera anaotacion (*anotation*), la anotacion, el body (*body*) de esta primera anotación, la URI de la segunda anotacion (*annotation2*)  y su body (*body2*).	
* CQ68A - Recursos citados por el articulo `http://w3id.org/roh/data#journal-article-1`.	
* CQ68B- Los ResearchResult *researchResult* que ha realizado una autor y los recursos resource  que contiene. 	
* CQ69- Devuelve las anotaciones (*annotation*) realizadas sobre un ResearchResult (*researchResult*) que no es un ResearchObject. Devuelve tambien el body (*body*) de dicha annotacion.	
