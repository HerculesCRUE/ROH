![](.//media/CabeceraDocumentosMD.png)

| Fecha         | 3/3/2021                                                   |
| ------------- | ------------------------------------------------------------ |
|Titulo|Buenas prácticas del esquema de URIs| 
|Descripción|Buenas prácticas en la definición de los URI para el proyecto Hércules ASIO|
|Versión|1.2|
|Módulo|Documentación|
|Tipo|Manual|
|Cambios de la Versión|Reordenación de la documentación y cambio de ruta|


# Hércules Backend ASIO. Buenas prácticas del esquema de URIs

[INTRODUCCIÓN](#introducción)

[Buenas prácticas de URIs](#buenas-prácticas-de-uris)

[Normalización de los componentes de los URI](#normalización-de-los-componentes-de-los-uri)

[Prácticas relativas a la gestión de recursos semánticos a través de URI](#prácticas-relativas-a-la-gestión-de-recursos-semánticos-a-través-de-uri)

[Política de persistencia y Contrato de buenas prácticas](#política-de-persistencia-y-contrato-de-buenas-prácticas)

[Ejemplos de uso](#ejemplos-de-uso)

INTRODUCCIÓN
============

El presente documento describe las buenas prácticas en la definición de los URI
para el proyecto Hércules ASIO y tiene en cuenta lo indicado en la 
[Especificacion-Esquema-de-URIs](Especificacion-Esquema-de-URIs.md).

En su elaboración aplicamos las recomendaciones de la [Norma
Técnica de Interoperabilidad de Reutilización de recursos de la
Información](https://www.boe.es/boe/dias/2013/03/04/pdfs/BOE-A-2013-2380.pdf)
(NTI), de la Secretaría de Estado de Administraciones Públicas, que se
basan, a su vez, en las iniciativas de datos abiertos y las
recomendaciones procedentes del mundo de la Web Semántica para los 
datos enlazados (Linked Data).

Las recomendaciones de multilingüismo se incluyen y detallan en el documento
[Modelo de multilingüismo.md](https://github.com/HerculesCRUE/ROH/blob/main/docs/4-%20Modelo%20de%20multiling%C3%BCismo.md).

Buenas prácticas de URIs
========================

Un URI (Uniform Resource Identifier, ver IETF [RFC 3986](https://tools.ietf.org/html/rfc3986), [RFC 6874](https://tools.ietf.org/html/rfc6874) y [RFC 7320](https://tools.ietf.org/html/rfc7320)) 
establece un identificador único que permite referenciar la información 
mediante mecanismos HTTP.

Las siguientes reglas y recomendaciones tienen en cuenta las propuestas de la [NTI](https://www.boe.es/boe/dias/2013/03/04/pdfs/BOE-A-2013-2380.pdf) 
para espacios de datos enlazados (Linked data), que adoptan los principios de diseño y
las políticas de persistencia de URIs del W3C, en particular 
[Best Practices for Publishing Linked Data. The Role of "Good URIs" for Linked Data](https://www.w3.org/TR/ld-bp/#HTTP-URIS) y 
[Architecture of the World Wide Web. Uri persistence](https://www.w3.org/TR/webarch/#URI-persistence).

Agrupamos las Buenas prácticas en unas **Reglas de normalización** de
los componentes de los URI y en unas recomendaciones en la **Gestión de
recursos semánticos** a través de URI.

Normalización de los componentes de los URI
-------------------------------------------

Para garantizar la coherencia y el mantenimiento posterior del esquema
de URI se aplicarán las siguientes reglas para normalizar las distintas
partes que componen los URI:

a)  Seleccionar identificadores alfanuméricos cortos (en lo posible) únicos, 
    que sean representativos, intuitivos y, si fuera posible, semánticos.

b)  Usar siempre minúsculas, salvo en los casos en los que se utilice el
    nombre de la clase o concepto. Habitualmente, los nombres de las
    clases se representan con el primer carácter de cada palabra en
    mayúsculas.

c)  Eliminar todos los acentos, diéresis y símbolos de puntuación. Como
    excepción puede usarse el guion (--).

d)  Eliminar conjunciones y artículos en los casos de que el concepto a
    representar contenga más de una palabra.

e)  Usar el guion (--) como separador entre palabras.

f)  Evitar en la medida de lo posible la abreviatura de palabras, salvo
    que la abreviatura sea intuitiva.

g)  Los términos que componen los URI deberán ser legibles e
    interpretables por el mayor número de personas posible, por lo que
    se utilizará el castellano, cualquiera de las lenguas oficiales de
    España o el inglés como lengua franca de la investigación.

Prácticas relativas a la gestión de recursos semánticos a través de URI
-----------------------------------------------------------------------

Las siguientes prácticas se desarrollarán como requisitos del servidor
Linked Data de Hércules ASIO y se aplicarán para la gestión de recursos
semánticos descritos en RDF:

a)  Los URI son HTTP, que permiten el desreferenciado y el acceso a la 
    información identificada como un componente más de la WWW, lo que 
    supone además otros beneficios, como el enlazado, el uso 
    de cache de contenido y la indexación por parte de motores de búsqueda.

b)  Cumplir el principio de **persistencia de los URIs**, lo que significa
    que los que ya han sido creados previamente nunca deberían variar, y
    que el contenido al que hacen referencia debería ser accesible. En
    el caso de que sea necesario cambiar o eliminar el recurso al que
    apunta un identificador, se deberá establecer un mecanismo de
    información sobre el estado del recurso usando los códigos de estado
    de HTTP. En el caso de poder ofrecer una redirección a la nueva
    ubicación del recurso, se utilizarán los códigos de estado HTTP 3XX,
    mientras que para indicar que un recurso ha desaparecido
    permanentemente se utilizará el código de estado HTTP 410

c)  Siempre que sea posible, y existan versiones del recurso en formato
    legible para personas HTML o similar y RDF, el servidor que gestiona
    los URI realizará negociación del contenido en función de la
    cabecera del agente que realiza la petición. En el caso de que el
    cliente acepte un formato de representación RDF en cualquiera de sus
    notaciones (p.e., especificando en su cabecera que acepta el tipo
    MIME application/rdf+xml) se servirá el documento RDF a través del
    mecanismo de redirecciones alternativas mediante los códigos de
    estado HTTP 3XX.

d)  En el caso de que la universidad no pudiera seguir gestionando el 
    espacio de URIs, debería proporcionar un volcado de los datos públicos, 
    para que otra entidad del ámbito universitario se hiciera cargo 
    de los mismos; y establecer un mecanismo de redirección hacia el nuevo 
    espacio el tiempo suficiente para que las redirecciones HTTP sean
    reconocidas por los actores implicados.

e)  En el caso de que no se realice una negociación del contenido desde
    el servidor y, para favorecer el descubrimiento de contenido RDF
    desde los documentos HTML relacionados con las descripciones de los
    recursos, se incluirán enlaces a la representación alternativa en
    cualquiera de las representaciones en RDF desde los propios
    documentos HTML de la forma \<link rel=«alternate»
    type=«application/rdf+xml» href=«documento.rdf»\> o similar. En esa
    sentencia se incluye el tipo de formato MIME del documento
    (application/rdf+xml, text/n3, etc.).
    
f)  El resultado del desreferenciado de los URI será independiente del
    servidor implementado (Linked Data Server de ASIO), pudiendo cambiar
    éste sin que varíe la información servida.

g)  Cuando se establezcan enlaces entre distintos recursos de
    información, se procurará la generación de enlaces que conecten los
    recursos bidireccionales para facilitar la navegación sobre los
    recursos de información en ambos sentidos.
    
Política de persistencia y Contrato de buenas prácticas
-------------------------------------------------------

De entre las prácticas de gestión de recursos semámticos del apartado
anterior, hay que destacar el cumplimiento del principio de persistencia 
de los URIs. Como se ha indicado, los URIs que ya han sido creados 
nunca deberían variar y el contenido al que hacen referencia debería ser 
accesible. 

Sin embargo, en los sistemas informáticos los recursos pueden sufrir 
cambios o ser eliminados. En esos casos, se deberá establecer un 
mecanismo que informe sobre el estado del recurso usando los códigos 
de estado de HTTP. Por ejemplo, si se pudiera ofrecer una redirección 
a la nueva ubicación del recurso (el nuevo URI), se utilizarían los
códigos de estado [HTTP 3XX](https://tools.ietf.org/html/rfc2616#page-61); mientras que si el recurso ha sido eliminado
se utilizaría el código de estado [HTTP 410](https://tools.ietf.org/html/rfc2616#page-68).

Es fundamental que los URI (Uniform Resource Identifier), usados para 
referenciar la información del SGI mediante Hércules ASIO, compartan un 
esquema común, apliquen unas reglas de normalización similares y, 
especialmente, persistan según lo identificado, es decir, que cumplan el
principio de persistencia para que la información sea accesible en el 
futuro y se mantenga la integridad de la información.

Como garantía de cumplimiento, las universidades que implanten ASIO se
comprometen, mediante la adhesión a un "Contrato de buenas prácticas de URIs",
a cumplir las recomendaciones de este documento, particularmente el
principio de persistencia.

Ejemplos de uso
---------------

En cuanto a la normalización de URIs, la carga de los datos de la 
Universidad de Murcia genera URIs para instancias como los siguientes: 

http://graph.um.es/res/project/RAYD-A-2002-6237

http://graph.um.es/res/researchproposal/RAYD-A-2002-6237

http://graph.um.es/res/dossier/RAYD-A-2002-6237

http://graph.um.es/res/contract/0bbb9175-6c05-4b18-8e34-d38cffa678cd

http://graph.um.es/res/person/5089

Los grafos con nombre definidos para las pruebas de carga de datos 
tienen los siguientes URIs:

http://graph.um.es/graph/um_sgi (provenientes de una exportación de datos)

http://graph.um.es/graph/um_cvn (provenientes de la carga de CVN)

Las categorías SKOS tienen URIs como:

http://graph.um.es/kos/hr/0006B (BECAS POSTDOCTORALES)

En la siguiente fase del proyecto ASIO estos URI podrían tener algún 
cambio, en particular en el segmento del identificador, y también 
tendremos ejemplos diferentes procedentes de otras cargas de datos.

En cuanto a la gestión de recursos semánticso, contamos con una 
versión preliminar de un servidor Linked Data, basado en Trifid, 
que se encarga de desreferenciar los URI anteriores. No es el 
servidor definitivo del proyecto ASIO, si bien permite el 
desreferenciado de los URI, una presentación básica en HTML y la
obtención de la información en varios formatos. 
Para ver un ejemplo podemos ejecutar los siguientes comandos CURL:

    curl http://graph.um.es/res/project/RAYD-A-2002-6237
    curl -H "Accept: application/rdf+xml" http://graph.um.es/res/project/RAYD-A-2002-6237
