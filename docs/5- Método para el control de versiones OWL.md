![](.//media/CabeceraDocumentosMD.png)

# Hércules Backend ASIO. Método para el control de versiones OWL 

[INTRODUCCIÓN](#introducción)

[PROCESO DE CONTROL DE VERSIONES](#proceso-de-control-de-versiones)

-   [Control de versiones en el desarrollo de ROH](#control-de-versiones-en-el-desarrollo-de-roh)

-   [Control de versiones en el refinamiento de ROH](#control-de-versiones-en-el-refinamiento-de-roh)

-   [Control de versiones en el mantenimiento de ROH](#control-de-versiones-en-el-mantenimiento-de-roh)

[Procedimiento de desarrollo ontológico](#procedimiento-de-desarrollo-ontológico)

-   [Pasos del procedimiento de gestión de cambios en ROH](#pasos-del-procedimiento-de-gestión-de-cambios-en-roh)

[Herramientas de control de versiones](#herramientas-de-control-de-versiones)

-   [Edición y versionado en WebProtégé](#edición-y-versionado-en-webprotégé)

-   [Publicación en GitHub](#publicación-en-github)

INTRODUCCIÓN
============

El presente documento describe el método para el control de versiones OWL en el proyecto Hércules ASIO (Arquitectura Semántica e Infraestructura Ontológica, ASIO en adelante). Algunos de los procesos de este método se realizan mediante dos aplicaciones de control de versiones: WebProtégé (https://webprotege.stanford.edu/) y GitHub (https://github.com/).

ASIO se desplegará en varias universidades, se integrará con otros proyectos de Hércules en cada universidad, como SGI (Sistema de Gestión de la investigación) y EDMA (Enriquecimiento de Datos y Métodos de Análisis), y podría ser reutilizado como datos enlazados o en procesos de consulta, particularmente en los realizados con los datos de todo el Sistema Universitario Español (SUE), que en el presente proyecto se ejecutarán contra un nodo central Unidata. Para más información, se puede consultar la [información sobre los proyectos Hércules](https://www.um.es/web/hercules/proyectos) en la web de la Universidad de Murcia.

En todas las fases de diseño de ASIO tenemos en cuenta el uso efectivo y viable del sistema resultante como un producto, desde una perspectiva industrial. Es por ellos por lo que, para este caso, **Proponemos por tanto un proceso semiautomatizado, con moderación humana y asistido por unas herramientas de control de versiones y por un conjunto de pruebas automatizadas**, en el que la liberación de una nueva versión de la ontología tenga un control humano e incorpore toda la información y utilidades que un administrador de ASIO necesitaría para actualizar la versión de ROH en su universidad, con la seguridad de que no van a perder sus datos ni dejar de funcionar sus integraciones. Desde el punto de vista de la obtención de información de todo el SUE, actuar de estar forma en cada nodo de ASIO garantiza la mantenibilidad y utilidad de los procesos de consulta sobre los datos de investigación de la universidad española.

La construcción de un proceso totalmente automatizado nos parece inviable en este caso, un sistema que puede crecer, ramificarse y evolucionar en cada nodo de modo no controlado, que ha de ajustarse en cada caso para responder a casuísticas locales no anticipables  y potencialmente muy numerosas . En este ecosistema, que puede llegar a ser muy complejo, las modificaciones relevantes de la ontología afectarían, al mismo tiempo y de modo no previsible a priori a todos los componentes software dependientes del mismo. Concretamente, a los datos existentes en ASIO, a las integraciones con otros sistemas de Hércules, a las integraciones con otros sistemas de la universidad, a posibles reutilizadores de la información y a los procesos de consulta sobre la información de todo el SUE. Es decir, la modificación del modelo ontológico desarrollado en la Red de Ontologías Hércules (ROH) no es sólo un ejercicio de definición y representación formal de un sistema de conocimiento, sino que se trata de la redefinición del núcleo de un sistema de conocimiento complejo y actuante, que contiene datos y cuenta con múltiples instancias y conexiones, cuyos cambios deben suceder de manera controlada y contenida.

Los [principios de diseño que hemos aplicado en la creación de ROH](https://github.com/HerculesCRUE/GnossDeustoOnto/blob/master/Documentation/README.md#11-design-rationale) tienen que seguir respetándose en el mantenimiento posterior de ROH. Estos principios son **reusabilidad** de ontologías y vocabularios existentes; **extensibilidad** para contemplar casos específicos del sistema universitario español o de cada universidad sin perder la **integridad** de la información; y **usabilidad** para que otros sistemas puedan conectarse y reutilizar datos con facilidad y fiabilidad.

En particular, consideramos que, en el mantenimiento de ROH, debe tenerse en cuenta la **extensibilidad sujeta al cumplimiento de la integridad**, que supone que no se vean perjudicadas las instancias e integraciones existentes.

Por tanto, los objetivos del proceso de control de versiones OWL son: 
-   Mantener la integridad de los datos en todas las instancias de ASIO.
-   Conservar la integración con otros sistemas, particularmente SGI y EDMA, pero también con cualquier otro Sistema de Gestión de la       Investigación Universitaria existente, o de enriquecimiento de datos o de interrogación que terceras partes hayan podido desarrollar sobre ASIO.
-   Mantener la operatividad de las consultas realizadas, sobre todo las relativas al total del SUE.

PROCESO DE CONTROL DE VERSIONES
===============================

Este proceso tiene unos condicionantes distintos según el momento en el que se generen las versiones de la ROH. En este sentido, distinguimos tres fases, cada una de ellas de una creciente complejidad de gestión:
-   **Desarrollo de ROH** en la ejecución del proyecto ASIO, hasta la finalización del Hito 1.
-   **Refinamiento de ROH** en la ejecución del proyecto ASIO, desde el Hito 1 hasta su finalización.
-   **Mantenimiento de ROH**, una vez terminado el proyecto ASIO.

Control de versiones en el desarrollo de ROH
--------------------------------------------

El desarrollo de ROH, hasta la finalización del Hito 1, ha sido un proceso de diseño ontológico, cuyos detalles se pueden consultar en [Ontological design](https://github.com/HerculesCRUE/GnossDeustoOnto/blob/master/Documentation/README.md#headSection1), en el que las interacciones con otros sistemas o modelos de datos han sido muy limitadas y sólo como caso de modelado de datos o fuente de datos para pruebas.

Como se describe más adelante en [Procedimiento de desarrollo ontológico](#procedimiento-de-desarrollo-ontológico), hemos usado Protégé para las tareas de diseño y git en la plataforma GitHub para el control de versiones. 

Durante esta fase se han definido y preparado unos tests de regresión que ejecutan las cuestiones de validación ([validation-questions](https://github.com/HerculesCRUE/GnossDeustoBackend/tree/master/validation-questions)) de ROH contra un conjunto de datos de prueba y un [workflow en Github Actions](https://github.com/HerculesCRUE/GnossDeustoBackend/blob/master/validation-questions/.github/workflows/maven.yml), que permiten comprobar, automáticamente, que las modificaciones de la ontología siguen siendo conformes al funcionamiento esperado.

Control de versiones en el refinamiento de ROH
----------------------------------------------

Como se indica en el documento [Tareas pendientes en la definición de ROH](https://github.com/HerculesCRUE/GnossDeustoOnto/blob/master/Documentation/Pr%C3%B3ximos%20pasos.md), en esta fase, que va desde la finalización del Hito 1 hasta el final de la ejecución del proyecto ASIO, la infraestructura tecnológica será mejorada y ampliada con cambios que provendrán de:
-   Alineación con el modelo de datos del proyecto Hércules SGI, que constituye la fuente de datos fundamental de Hércules.
-   Alineación con otras fuentes de datos (otros SGI) que van a ser mapeadas como parte del desarrollo de ASIO.
-   Entidades y mejoras identificadas en la finalización del Hito 1 o que puedan surgir durante la ejecución del proyecto.

En esta fase del proyecto se van a ir desarrollado las integraciones de ASIO con otros sistemas al tiempo que se termina la definición de ROH. Esta complejidad creciente tiene que acompañarse de la preparación de los procesos semi-automáticos que serán utilizados y puestos a prueba en esta fase y serán la base del control de versiones de ROH en su posterior mantenimiento.

La respuesta del control de versiones OWL al aumento de complejidad creciente consistirá en: 
1.  Mejorar y ampliar las preguntas de competencia ([validation-questions](https://github.com/HerculesCRUE/GnossDeustoBackend/tree/master/validation-questions)).
2.  Modificar el flujo de GitHub actions que lanzan los tests de regresión, añadiéndole nuevos pasos:
    1.  Creación de *shapes* SHACL temporales con las restricciones de la nueva ontología.
    2.  Si la ontología debe acompañarse de un migrador de datos: 
        1.  Generación automática de una copia de los grafos del RDF Store.
        2.  Paso del migrador de datos sobre la copia de los grafos.
    3.  Ejecución de las cuestiones de validación. Si se necesita un migrador, contra un nuevo juego de datos de prueba.
    4.  Validar los *shapes* temporales contra los datos ya cargados en los grafos del RDF Store, que se corresponderán con la anterior versión de la ontología, o contra los datos de los grafos copiados y migrados si se necesitase un migrador, que se corresponderían con la nueva versión de la ontología.

El control de versiones necesitará que el desarrollo del API e interfaz de Carga tenga preparadas las siguientes funcionalidades:
-   Función en API Carga para publicación de la ontología en el repositorio RDF.
-   Función en API Carga para creación de shapes temporales a partir de las restricciones de una ontología arbitraria (ROH en nuestro caso).
-   Interfaz de Administración de Carga para la ejecución de las dos funciones anteriores.

Si la nueva ontología supera los tests de regresión, podría publicarse en el grafo del RDF Store sin más comprobaciones. Sin embargo, podría suceder que los *shapes* temporales detectasen inconsistencias entre la nueva ontología y los datos publicados en el RDF, o que estas inconsistencias fueran esperadas durante el proceso de reingeniería, debido al tipo de acciones llevadas a cabo (ver en [Pasos del procedimiento de gestión de cambios en ROH la tabla de acciones](#pasos-del-procedimiento-de-gesti%C3%B3n-de-cambios-en-roh)).

En cualquiera de los dos casos, la nueva versión tendrá que ir acompañada de un migrador de datos, desarrollado en paralelo al proceso de reingeniería ontológica, que se encargue de modificar los datos existentes en el grafo del RDF Store, para que cumplan con el diseño y restricciones de la nueva versión de la ontología. Además, tendrá que prepararse un nuevo juego de datos de prueba para las cuestiones de validación, correspondiente a la nueva ontología.

En el caso de necesitar un migrador de datos, la publicación de una nueva versión de la ontología tendrá que acompañarse, además de por el documento *changelog* con los cambios de la ontología (generado automáticamente por WebProtégé) y del propio migrador, de aquellos componentes que se vayan a ver afectados por los cambios. Los componentes que deberían formar parte de la misma *release* son:
-   Mapeo y API de integración con Hércules SGI.
-   API de Carga de Unidata.
-   Mapeo y API de integración con Hércules EDMA (probablemente ya en la fase de mantenimiento, posterior a la finalización de ASIO).
-   Mapeo y API de integración con otros SGI (p.e. Universitas XXI).

En resumen, proponemos un proceso semiautomatizado que permite que la liberación de una nueva versión de ROH cuente con un conjunto de validaciones automáticas, pero en el que la decisión final de despliegue, especialmente en el caso de cambios de importancia, queda en manos de los administradores de ASIO. Además, estos administradores dispondrán de la información y herramientas necesarias para el despliegue de la nueva *release* de ROH, que se acompañará, en el caso de cambios de importancia, de nuevas versiones de los módulos de la arquitectura semántica afectados.

Control de versiones en el mantenimiento de ROH
-----------------------------------------------

Una vez terminado el proyecto, el control de versiones OWL necesitará sobre todo unos procedimientos de trabajo y comunicación, soportados con los procesos indicados en el punto anterior, que serán puestos a prueba y ajustados durante la finalización del proyecto.

Como ya hemos indicado, ASIO tendrá múltiples instalaciones e integraciones, lo que implica que los cambios en ROH, que será el núcleo del sistema de conocimiento de la investigación universitaria española, deben afrontarse con unos procedimientos estrictos que van más allá del simple control de versiones OWL. Apuntamos aquí algunos de los aspectos a considerar, que deberían desarrollarse antes de la finalización del proyecto ASIO:
-   Establecimiento de un grupo de trabajo responsable del mantenimiento de ROH.
-   Definición del procedimiento de elaboración de una nueva versión de ROH.
-   Definición del procedimiento de comunicación de una nueva versión de ROH.
-   Definición del procedimiento de migración a una nueva versión.


PROCEDIMIENTO DE DESARROLLO ONTOLÓGICO
======================================

El equipo de diseño ontológico de GNOSS-Deusto ha trabajado en paralelo 
en diferentes aspectos de la ontología que luego han podido fusionarse 
sin problemas, con un control de cambios a nivel de documento de texto. 
Se ha utilizado la herramienta Protegé para edición de la ontología y git
con la plataforma GitHub para llevar a cabo la gestión de cambios. 

Durante el desarrollo del proyecto no se ha utilizado WebProtegé ya que 
hemos considerado que no es la herramienta de desarrollo más adecuada, ya 
que no dispone de todas las funcionalidades de su versión de escritorio. 
Al utilizar la herramienta de control de versiones git, y la plataforma 
GitHub, se pueden utilizar todas las herramientas de Integración Continua (CI) 
disponibles para cualquier proyecto software. De esta manera, se podría 
emplear la técnica de pull-request para que, cuando un colaborador externo 
desease modificar la ontología, el equipo mantenedor del proyecto pudiera 
revisar esos cambios y aceptarlos si proceden.

Sin embargo, el proceso de revisión de un cambio no tiene por qué ser 
totalmente manual. A través de la herramienta [Github Actions](https://github.com/features/actions) se pueden 
definir unos flujos de ejecución que, por ejemplo, realicen una serie de
tests para comprobar que a pesar de los cambios realizados en la ontología
el resto de las herramientas dependientes siguen funcionando correctamente. De hecho un [workflow en Github Actions](https://github.com/HerculesCRUE/GnossDeustoBackend/blob/master/validation-questions/.github/workflows/maven.yml) ha sido creado para poner en marcha el conjunto de cuestiones de validación ([validation-questions](https://github.com/HerculesCRUE/GnossDeustoBackend/tree/master/validation-questions)) de ROH. 
Una vez pasados estos tests (batería de tests de regresión), el pull-request
se puede integrar en la rama principal del repositorio de la ontología. 
Si se desease utilizar una solución independiente de la plataforma Github, 
se podría utilizar la popular herramienta de integración continua [Jenkins](https://www.jenkins.io/).

En definitiva, consideramos que WebProtégé es una herramienta que permite
discernir los cambios efectuados dentro de la ontología no como cambios 
en contenidos textuales, sino como operaciones sobre la ontología. Por 
ejemplo, “se ha modificado una entidad”, “se ha añadido una nueva object
property”, etc. Sin embargo, no permite la integración de razonadores y 
otras características importantes en el diseño ontológico. 

No obstante, la solución final que proponemos es combinar WebProtegé con 
GitHub y herramientas de integración continua para garantizar una gestión
de cambios de calidad dentro de ROH. Por ejemplo, utilizaríamos WebProtegé
para implementar el prototipo de un cambio importante dentro de la ontología,
pero este cambio, una vez consensuado, tendría que ser trasladado al
repositorio git para ser integrado dentro de la rama principal a través del
mecanismo pull-request.

Pasos del procedimiento de gestión de cambios en ROH
----------------------------------------------------

El procedimiento de gestión de cambios en la red de ontologías ROH tendrá 
los siguientes pasos:

1.	**Edición colaborativa en WebProtégé**. Hacer un Branch de la ontología en GitHub y trabajar de modo colaborativo con WebProtégé. El fichero que genera Protégé (la versión de escritorio) no deja los contenidos en el mismo orden cuando diferentes usuarios modifican concurrentemente el mismo, lo cual produce quebraderos de cabeza durante la integración (merge) de los mismos. Webprotegé, sin embargo, permite mantener el historial de cambios dentro de una ontología, independientemente de la serialización. 
2.	**Pull request para solicitar revisión y aceptación de cambios**. Cuando una revisión consolidada de la ontología desarrollada en modo colaborativo en WebProtégé es concluida, se realizará un PULL REQUEST para integrar cambios realizados con WebProtégé con la versión release en la rama principal de la ontología, mantenida en el repositorio GitHub.  
3.	**Aceptación de los cambios e integración en rama principal**. Si los cambios realizados no *rompen* el sistema ASIO, lo que se comprueba mediante los tests de regresión (cuestiones de validación y *shapes* SHACL), entonces son aceptados como nueva versión de ROH. Los tests de regresión comprueban el buen funcionamiento de la ontología y que las herramientas que dependen de ella se ejecutarán correctamente.

Todos los cambios a los que estaría sujetos la ontología serían supervisados
por el equipo mantenedor del proyecto, por lo que la aceptabilidad de los 
mismos dependerá de dicho equipo. Sin embargo, hay ciertos cambios, como 
modificaciones en propiedades como rdfs:label o rdfs:comment, que, en principio,
no deberían tener mayores repercusiones en el resto de componentes. Para otros
cambios, como la introducción o modificación de clases y propiedades o de las
relaciones entre ellas, se propone un enfoque híbrido a través de tests 
automatizados y la confirmación final del equipo mantenedor.


| Acción  | Tratamiento manual          |      Tratamiento automático     |
| --------| ----------------------------|---------------------------------|
| Cambios en la documentación o etiquetas  (modificar la descripción de una clase - label, comment, etc-)  |  | Cambios son aceptados si pasan tests de regresión en proceso de integración continua (cuestiones de validación y verificaciones SHACL)  |
| Modificación incremental de entidades o propiedades (creación de nueva entidad, crear una nueva propiedad, extensión de jerarquía de subclase a superclase)   | Moderador comprueba en descripción de pull request que el cambio solo altera de modo incremental la ontología. Decide si lanzar proceso de integración automática.               |Se lanza batería de tests de regresión. Si pasan, entonces se integran en rama principal    |
| Creación o borrado de entidades o propiedades (eliminar propiedad, borrado de una entidad, introducir nueva superclase de clases existentes, añadir o modificar restricciones, cambiar propiedades entre subclasses y superclasses)     | Moderador comprueba en detalle los cambios y decide si activar actualización automática de cambios        | Se lanza batería de tests de regresión. Si pasan, entonces se integran en rama principal |


Herramientas de control de versiones
====================================

Como ya hemos comentado, proponemos el uso de WebProtégé para gestionar
el prototipado de los cambios importantes dentro de la ontología, mientras
que las versiones liberadas de la(s) ontología(s) se subirán a GitHub.

WebProtégé está disponible en la infraestructura de desarrollo de
Hércules ASIO en:

http://herc-as-webprotege-desa.atica.um.es/

Contacte con el equipo de Hércules ASIO si desea acceso al repositorio.

Edición y versionado en WebProtégé
----------------------------------

Los desarrolladores pueden crear y editar ontologías de dos maneras
distintas:

-   Online: A través del editor de ontologías de WebProtégé. Los
    desarrolladores pueden hacer los cambios que necesiten desde el
    editor online de WebProtégé y automáticamente se guarda y versiona
    la ontología.

> ![](.//media/image2.png)

-   Offline: A través del entorno que deseen (Protégé, FluentEditor,
    OWLGrEd...). El desarrollador debe descargarse desde WebProtégé la
    última versión de la ontología y abrirla desde su editor de
    ontologías preferido. Para ello, vamos a la pestaña History y
    pinchamos en la versión que nos queremos descargar (en este caso, la
    R7 que es la última). Nos aparece un menú, donde debemos seleccionar
    la opción "Download":

![](.//media/image3.png)

> Inmediatamente se descarga la ontología en formato OWL.
>
> Una vez ha terminado la edición, debe subir la ontología en la que ha
> trabajado a WebProtégé. La ontología puede subirse en múltiples
> formatos (owl, ttl, rdfs...). Para ello, nos dirigimos al menú
> "Project" y pinchamos en la opción Apply External Edits:
>
> ![](.//media/image4.png)
>
> Seleccionamos el archivo que hemos editado y aceptamos. Antes de
> guardar la nueva versión de la ontología, WebProtégé muestra un
> diálogo con los cambios detectados respecto a la versión almacenada.
>
> ![](.//media/image5.png)

Podemos añadir un mensaje para dejar reflejado en el histórico de
versiones el motivo de la modificación.

Publicación en GitHub
---------------------

WebProtégé sirve para versionar el trabajo diario de los desarrolladores
y resolver los posibles conflictos de edición que puedan aparecer de
manera eficaz, mientras que en GitHub se generan ramas y se consolidan 
nuevas versiones de las ontologías. 

Además, GitHub permite la descarga de las versiones de las ontologías y 
mantiene un registro de las versiones consolidadas.

El repositorio de código fuente GitHub para el desarrollo de la 
infraestructura ontológica del proyecto Hércules está en
<https://github.com/HerculesCRUE/GnossDeustoOnto/>.

