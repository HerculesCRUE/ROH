![](https://github.com/HerculesCRUE/ROH/blob/gh-pages/media/CabeceraDocumentosMD.png)

# ROH
Research Domain Ontology (ROH). 

ROH main documentation can be found in the following files:
- `0 - OntologyTutorial.pdf` - [PDF](https://herculescrue.github.io/ROH/0%20-%20OntologyTutorial.pdf) corresponding to a tutorial about the ROH network of ontologies. This a PDF document including essentially the contents of section 2 of this Markdown document.
- `1- OntologyDocumentation.pdf` - [PDF](https://herculescrue.github.io/ROH/1-%20OntologyDocumentation.pdf) describing in tabular form, every entity modelled in ROH.

Notice that ROH network of ontologies is divided into 2 main parts as depicted in the following figure:
- *The generic ontology, core module* - contains the most important entities and properties to model information in the academic domain. It contains the central part of the network of ontologies. It covers the academic domain, being agnostic to the country or the research organization whose information wants to be modelled with.
- *A set of vertical modules*	 which include, on one hand, specializations of some academic concepts for a given country domain. For instance, the figure Associate Professor in the Spanish academic domain would be encountered in the vertical module university-HR-es and is assigned the URI `http://purl.org/roh/university-hr/es#ProfesorTitularDeUniversidad`. On the other hand, these vertical modules, include controlled vocabularies, according to SKOS ontology, for different important areas in the academic domain, namely,  geographical locations (`geopolitical`) , knowledge areas (including concepts for `scientific-domains`, `subject-areas` or `unesco-codes`), classification of project types (`project-classification`), resource positions in universities (`university-HR` for Spain, UK or Portugal), controlled vocabulary with all universities in Spain (`university-structure`) or  some extensions for the Spanish university system (`extensions-es`).

![](https://github.com/HerculesCRUE/ROH/blob/gh-pages/media/ROH-core-vertical-modules.png)

**Figura** **1**. ROH core module and its associated vertical modules.

Some exemplary competence questions are shown below, which demonstrate the ROH ontology usage:

* Q5 - Listado de los centros/estructuras de investigación que hayan realizado proyectos y su respectiva convocatoria: https://tinyurl.com/y4v39hy3
* Q7
* Q9 - Listado de patentes, diseños industriales, etc. de un centro/estructura de investigación en un área/disciplina:  https://tinyurl.com/yxhp5wky
* Q10 - Listado de los proyectos adjudicados/desarrollados, de un centro/estructura de investigación, de un área/disciplina, en un determinado año de búsqueda: https://tinyurl.com/y5e7o855
* Q12 - Listar proyectos agrupados por ámbito geográfico: https://tinyurl.com/yy39bv58
* Q16A - Dada una persona listar proyectos en los que ha intervenido filtrados por periodo y/o organización: https://tinyurl.com/yyejunrz
* Q16B - Dada una persona listar research objects a los que ha contribuido, filtrados por periodo y/o organización: https://tinyurl.com/y42p6ah4
* Q18 - Contar las publicaciones con índice de impacto de un usuario en un periodo de tiempo: https://tinyurl.com/yxjxrz8s
* Q24
* Q26 - Obtener el listado de congresos/workshops y eventos de divulgación científica en los que haya participado indicando el rol que he tenido: organizador, expositor, etc: https://tinyurl.com/y58ly6k7
* Q28 - Obtener el listado de proyectos en los que he participado incluyendo el rol que he desempeñado, por ejemplo, investigador principal: https://tinyurl.com/y2fa474q
* Q34 - Proyecto en estado PROPOSAL_SUBMITTED dirigidas a una empresa e incluso detalles económicos de la misma, el Funding propuesto y los Funding Amounts associado: https://tinyurl.com/yxgu6pg7
* Q36 - Listar los grupos ordenados por financiación recibida: https://tinyurl.com/yywd4maz
* Q42 - Investigadores que tienen ERCs, Marie Curie, etc.: https://tinyurl.com/y4g937q3
* Q44 - Cuantificar los proyectos en convocatorias competitivas de un grupo de investigación en un rango de años con grupos de investigación de otras Universidades: https://tinyurl.com/y6fz6zfm
* Q45 - Obtener el total de publicaciones de impacto por mujeres y hombres: https://tinyurl.com/y2uoovj2
* Q50 - Dados unos años listar los JRCs en los mismos para poder visualizar evolución: https://tinyurl.com/y2waftyb
* Q55 - Listar los proyectos privados obtenidos por un grupo de investigación contratados por una organización privada: https://tinyurl.com/y5jpdtpg
