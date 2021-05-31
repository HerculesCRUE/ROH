![](.//media/CabeceraDocumentosMD.png)

| Fecha         | 3/3/2021                                                   |
| ------------- | ------------------------------------------------------------ |
|Titulo|Especificación de Esquema de URIs| 
|Descripción|Especificación del Esquema de URIs para el proyecto Hércules ASIO|
|Versión|1.2|
|Módulo|Documentación|
|Tipo|Especificación|
|Cambios de la Versión|Reordenación de la documentación y cambio de ruta|


# Hércules Backend ASIO. Especificación de Esquema de URIs

[1 INTRODUCCIÓN](#introducción)

[2 Características del Esquema de URIs](#características-del-esquema-de-uris)

[3 Estructura del Esquema de URIs](#estructura-del-esquema-de-uris)

[3.1 Base](#base)

[3.2 Carácter de la información](#carácter-de-la-información)

[3.3 Sector o ámbito](#sector-o-ámbito)

[3.4 dominio o temática](#dominio-o-temática)

[3.5 conceptos específicos](#conceptos-específicos)

[4 Tipos de URIs](#tipos-de-uris)

[4.1 URI para identificar a cualquier instancia física o conceptual](#uri-para-identificar-a-cualquier-instancia-física-o-conceptual)

[4.2 URI para identificar vocabularios](#uri-para-identificar-vocabularios)

[4.3 URI para identificar esquemas de conceptos](#uri-para-identificar-esquemas-de-conceptos)

[4.4 URI para identificar named graphs](#uri-para-identificar-named-graphs)

[4.5 URI para identificar datasets](#uri-para-identificar-datasets)

[4.6 URI para identificar entidades VoID](#uri-para-identificar-entidades-void)

[4.7 URI para identificar entidades PROV](#uri-para-identificar-entidades-prov)

[5 Definición del Esquema de URIs](#definición-del-esquema-de-uris)

INTRODUCCIÓN
============

El presente documento describe la Especificación del Esquema de URIs para el
proyecto Hércules ASIO.

En su elaboración tenemos en cuenta las recomendaciones de la [Norma
Técnica de Interoperabilidad de Reutilización de recursos de la
Información](https://www.boe.es/boe/dias/2013/03/04/pdfs/BOE-A-2013-2380.pdf)
(NTI), de la Secretaría de Estado de Administraciones Públicas; que se
basan, a su vez, en las iniciativas de datos abiertos y las
recomendaciones procedentes del mundo de la Web Semántica.

Como veremos, estas recomendaciones serán adaptadas al ámbito de un
sistema de investigación universitaria; y tendrán presente la resolución
y conexión de las entidades identificadas por los URIs.

Características del Esquema de URIs
===================================

Este documento de diseño del Esquema de URIs Hércules tendrá unos
requisitos genéricos similares a los utilizados en la citada NTI. Estos
requisitos serán:

a)  Utilizar el protocolo HTTP, de forma que se garantice la resolución
    de cualquier URI en la web.

b)  Usar una estructura de composición de URI consistente, extensible y
    persistente. Las normas de construcción de los URI seguirán unos
    patrones determinados que ofrezcan coherencia en la uniformidad, los
    cuales podrán ser ampliados o adaptados en caso de necesidad.

c)  Seguir una estructura de composición de URIs comprensible y
    relevante. Esto significa que el propio identificador debe ofrecer
    información semántica autocontenida, lo que permitirá a cualquier
    agente reutilizador disponer de información sobre el propio recurso,
    así como su procedencia.

d)  No exponer información sobre la implementación técnica de los
    recursos que representan los URIs. En la medida de lo posible se
    omitirá información específica sobre la tecnología subyacente del
    recurso representado; por ejemplo, no se incluirán las extensiones
    correspondientes a tecnologías con las que se generan los recursos
    web como .php, .jsp, etc.

El punto c descrito anteriormente implica que **los URI de Hércules no
serán totalmente "opacos" sino parcialmente "visibles"**, es decir, que 
contendrán información semántica que un humano pueda interpretar, lo que
consideramos una ventaja. Decimos que son parcialmente visibles porque 
los URIs no contendrán elementos que permitan que un humano 
reconozca directamente una entidad, como podría ser el nombre de
un investigador. Además, URIs que podríamos calificar como "opacos", 
como las de ORCiD (p.e. <https://orcid.org/0000-0001-8055-6823>), 
en realidad lo son porque se lo pueden permitir sin que los humanos 
tengan problemas de interpretación: en ese dominio sólo hay 
investigadores. La legibilidad por humanos es la mayor ventaja de 
los URI parcialmente "visibles", además de ser la recomendación de 
la [NTI de Reutilización](https://www.boe.es/boe/dias/2013/03/04/pdfs/BOE-A-2013-2380.pdf), referencia del proyecto Hércules.

Además, hay que indicar que para un sistema informático todos los URI
son igualmente "visibles", por lo que no hay diferencia en cuanto a la
capacidad de computación. La única desventaja es que los URI "visibles"
son, en general, más largos, por lo que ocupan más espacio en los
sistemas de gestión de datos. El coste decreciente de la memoria RAM y
la CPU, que son los parámetros más importantes en los sistemas de
gestión datos semánticos, convierte a este incremento de espacio en un
asunto poco relevante.

Los URI generados por ASIO serán resueltos con el protocolo HTTP por el
servidor Linked Data de ASIO, que será implementado cumpliendo la 
recomendación LDP, según se indica en el documento [Evaluación de 
cumplimiento Linked Data Platform (LDP)](../Hercules-ASIO-Evaluacion-de-cumplimiento-Linked-Data-Platform.md).

Estructura del Esquema de URIs
==============================

Los URIs generados tendrán una estructura uniforme que proporcionará
coherencia al esquema de URIs de Hércules ASIO como sistema de
representación de los recursos, de acuerdo con los requisitos genéricos
previamente descritos y proporcionará información intuitiva sobre la
procedencia y el tipo de información identificada.

Además de la base, los elementos que compondrán la ruta del URI serán:
carácter de la información y, opcionalmente, dominio, concepto y
extensión/formato. El orden de los elementos es el siguiente (se señalan
entre corchetes los elementos opcionales):

http://{base}/{carácter}\[/{dominio}\]\[/{concepto}\]\[.{ext}\]

Definimos a continuación cada uno de los elementos de la ruta del URI.

Base
----

Es un componente obligatorio que define el espacio y dominio dedicado
por cada universidad al albergue de la plataforma de datos abiertos y
reutilizables. Recomendamos el uso de un subdominio, por ejemplo:

http://datos.um.es

http://datos.crue.org

http//sgi-data.deusto.es

Carácter de la información
--------------------------

Es un componente obligatorio, que puede tomar una de las siguientes
formas:

-   def. Indica que el recurso identificado es un vocabulario u
    ontología definido por OWL.

-   kos. Indica que se trate de un sistema de organización del
    conocimiento (Knowledge Organization System) en el dominio de SGI:
    tesauros, taxonomías, etc.

-   res. Indica que se trata de una entidad del dominio.

-   graph. Indica que se trata de un grafo con nombre.

-   catalogo o cat. Indica que se trata de un dataset.


Sector o ámbito
---------------

El elemento "sector" sólo se debería usar si "dominio" no fuera 
suficiente para proporcionar significado a la URI.

Es un componente opcional de posible aplicación en URIs de organización
de conocimiento, por lo que sólo se usará en [URIs para identificar vocabularios](#uri-para-identificar-vocabularios) o en [URIs para identificar esquemas de conceptos](#uri-para-identificar-esquemas-de-conceptos).

Dominio o temática
------------------

Es un componente opcional de posible aplicación en URIs de organización
de conocimiento o en entidades que puedan tener subclases. Por ejemplo,
podría servir para distinguir el tema de una publicación.

Conceptos específicos
---------------------

Es un componente opcional del URI, pero funcionalmente obligatorio si se
trata de declarar entidades del ámbito de SGI, como: investigador,
publicación, proyecto, etc.

Tipos de URIs
=============

A continuación, se especifican los tipos de URI específicos para
recursos semánticos de una iniciativa basada en *Linked Data*.

URI para identificar a cualquier instancia física o conceptual
--------------------------------------------------------------

Estos recursos Linked Data son las representaciones atómicas de los documentos y
recursos de información. A su vez suelen ser instancias de las clases
que se definen en los vocabularios. Estos recursos se identifican
mediante el esquema:
http://{base}/res/\[{dominio}\]/{clase}/{ID}

Por ejemplo: http://graph.um.es/res/researcher/{id-investigador}

Las instancias físicas o conceptuales que se incluirán como fragmentos
en las URIs se corresponderán con las entidades identificadas en la Red
de Ontologías Hércules (ROH), como: researcher/investigador,
project/proyecto, publication/publicación, etc.

URI para identificar vocabularios
---------------------------------

Cualquier vocabulario u ontología seguirá el esquema:
http://{base}/def/\[{sector}\]/{dominio}

URI para identificar esquemas de conceptos
------------------------------------------

Cualquier sistema de organización del conocimiento --taxonomías,
diccionarios, tesauros, etc.-- sobre un dominio concreto será
identificado mediante un esquema de URI basado en la estructura:
http://{base}/kos/\[{sector}\]/{dominio}/{ID}

Por ejemplo (sin "sector"): http://graph.um.es/kos/research-area/{id-categoría}

URI para identificar named graphs
---------------------------------

El esquema de los URI para grafos con nombre es:
http://{base}/graph/\[{sector}\]/{dominio}

Por ejemplo (sin "sector"):
http://graph.um.es/graph/research/{id-grafo}

URI para identificar datasets
-----------------------------

El dataset resultante del proyecto ASIO necesitará un URI con el que 
pueda ser referenciado desde otros sistemas o datasets. Este 
identificador (URI) proporcionará información de catálogo de datos
(expresada en DCAT), información de datasets enlazados (expresada 
en VoID) o con información de procedencia (expresada en PROV).

En cualquiera de los casos, el dataset referenciado será el mismo, 
resultante de la incorporación de datos del sistema de investigación de
una universidad o de todas ellas en el nodo Unidata.

El esquema propuesto para identificar el dataset hacia estos sistemas
externos es:
http://{base}/cat/\[{sector}\]/{dominio}/{ID}

Por ejemplo (sin "sector"): 
http://graph.um.es/cat/research/{id-dataset}


Definición del Esquema de URIs
==============================

El Esquema de URIs tiene que declararse en un formato informático que
pueda ser interpretado por la Factoría de URIs para devolver el
identificador único que precisa cada entidad (cada instanci) cargada
en ASIO.

La propuesta se declara como un JSON y tiene la siguiente forma:

\[

{

\"base\": {url},

\"characters\": \[

{

\"character\": {nombre del carácter},

\"labelCharacter\": {etiqueta del carácter}

}

\]

\"uriResourceStructure\": \[

{

\"uriComponent\": {identificador del componente},

\"uriComponentValue\": {origen del valor del componente},

\"uriComponentOrder\": {orden del componente en el URI},

\"mandatory\": {true or false},

\"finalCharacter\": {si lo tiene, será una barra "/"}

}

\]

\"resourcesClasses\": \[

{

\"resourceClass\": {se corresponde con una entidad de ROH},

\"labelResourceClass\": {opcional, etiqueta de la entidad},

\"resourceURI\": {nombre de la estructura de URI que aplica}

}

\]

}

\]

Las partes del formato anterior son:

-   base. Contendrá el dominio en el que se encuentra el servidor que
    resolverá el URI.

-   characters. Podrá tener más de uno, pero al menos necesitará el que
    indica las entidades (res). Por cada carácter se indica:

    -   character. Identifica al tipo de carácter del URI.

    -   labelCharacter. Devuelve el fragmento del URI para el tipo de
        carácter.

-   uriResourceStructure. Podrá tener más de uno, pero al menos
    necesitará uno que indique como se compone el URI de las entidades
    de la ROH. La composición del URI se declara mediante elementos que
    tienen los siguientes atributos:

    -   uriComponent. Identifica el componente.

    -   uriComponentValue. Identifica como se obtiene el valor del
        componente. Las opciones pueden ser:

        -   {porción del JSON\@IDENTIFICADOR}, cuando el valor está
            definido en el propio esquema.

        -   {\@IDENTIFICADOR}, cuando el valor se suministra desde la
            aplicación que invoca a la factoría de URIs.

    -   uriComponentOrder. Define el orden del componente en el URI
        devuelto.

    -   mandatory. Indica si el componente del URI es obligatorio.

    -   finalCharacter. Indica si el componente lleva una barra para la
        composición de un URI correcto.

-   resourceClasses. Tendrá tantos elementos como entidades de ROH
    necesiten disponer de un URI a través de la Factoría de URIs. Los
    atributos por los que se declara como se compone el URI para cada
    entidad son:

    -   resourceClass. Identificador del tipo de entidad.

    -   labelResourceClass. Opcional, se declara si se desea que la URL
        tenga otro texto, habitualmente por requisitos de idioma.

    -   resourceURI. Identifica el elemento uriResourceStructure que se
        usará para componer el URI de la entidad.

Se indica a continuación un ejemplo:

\[

{

\"base\": \"http://datos.um.es\",

\"characters\": \[

{

\"character\": \"resource\",

\"labelCharacter\": \"res\"

}

{

\"character\": \"kos\"

\"labelCharacter\": \"kos\"

}

\]

\"uriResourceStructure\": \[

{

\"uriComponent\": \"base\",

\"uriComponentValue\": \"base\",

\"uriComponentOrder\": 1,

\"mandatory\": true,

\"finalCharacter\": \"/\"

}

{

\"uriComponent\": \"character\",

\"uriComponentValue\": \"character\@RESOURCE\",

\"uriComponentOrder\": 2,

\"mandatory\": true,

\"finalCharacter\": \"/\"

}

{

\"uriComponent\": \"resourceClass\",

\"uriComponentValue\": \"resourceClass\@RESOURCECLASS\",

\"uriComponentOrder\": 3,

\"mandatory\": true,

\"finalCharacter\": \"/\"

}

{

\"uriComponent\": \"identifier\",

\"uriComponentValue\": \"\@ID\",

\"uriComponentOrder\": 4,

\"mandatory\": true,

\"finalCharacter\": \"\"

}

\]

\"uriPublicationStructure\": \[

{

\"uriComponent\": \"base\",

\"uriComponentValue\": \"base\",

\"uriComponentOrder\": 1,

\"mandatory\": true,

\"finalCharacter\": \"/\"

}

{

\"uriComponent\": \"character\",

\"uriComponentValue\": \"character\@RESOURCE\",

\"uriComponentOrder\": 2,

\"mandatory\": true,

\"finalCharacter\": \"/\"

}

{

\"uriComponent\": \"sector\",

\"uriComponentValue\": \"\@SECTOR\",

\"uriComponentOrder\": 3,

\"mandatory\": true,

\"finalCharacter\": \"/\"

}

{

\"uriComponent\": \"resourceClass\",

\"uriComponentValue\": \"resourceClass\@RESOURCECLASS\",

\"uriComponentOrder\": 4,

\"mandatory\": true,

\"finalCharacter\": \"/\"

}

{

\"uriComponent\": \"identifier\",

\"uriComponentValue\": \"\@ID\",

\"uriComponentOrder\": 5,

\"mandatory\": true,

\"finalCharacter\": \"\"

}

\]

\"resourcesClasses\": \[

{

\"resourceClass\": \"researcher\",

\"labelResourceClass\": \"investigador\",

\"resourceURI\": \"uriResourceStructure\"

},

{

\"resourceClass\": \"publication\",

\"labelResourceClass\": \"publicacion\",

\"resourceURI\": \"uriPublicationStructure\"

}

\]

}

\]

