![](https://github.com/HerculesCRUE/ROH/blob/gh-pages/media/CabeceraDocumentosMD.png)

# ROH: Hercules Network of Ontologies, ASIO project
Research Domain Ontology (ROH). 

[ROH main documentation](https://herculescrue.github.io/ROH/) can be found in the following PDF files:
- `0- OntologyTutorial.pdf` - [PDF](https://github.com/HerculesCRUE/ROH/blob/main/docs/0-%20OntologyTutorial.pdf) corresponding to a tutorial about the ROH network of ontologies. This a PDF document including essentially the contents of section 2 of this Markdown document.
- `1- OntologyDocumentation.pdf` -[PDF](https://github.com/HerculesCRUE/ROH/blob/main/docs/1-%20OntologyDocumentation.pdf) describing in tabular form, every entity modelled in ROH.

Full documentation of the ROH ontology in Markdown format can be accessed in [ROH GitHub Pages](https://herculescrue.github.io/ROH/) and in the [Widoco tool generated documentation](https://herculescrue.github.io/ROH/roh/).

Notice that ROH network of ontologies is divided into 2 main parts as depicted in the following figure:
- *The generic ontology, core module* - contains the most important entities and properties to model information in the academic domain. It contains the central part of the network of ontologies. It covers the academic domain, being agnostic to the country or the research organization whose information wants to be modelled with.
- *A set of vertical modules*	 which include, on one hand, specializations of some academic concepts for a given country domain. For instance, the figure Associate Professor in the Spanish academic domain would be encountered in the vertical module university-HR-es and is assigned the URI `http://w3id.org/roh/university-hr/es#ProfesorTitularDeUniversidad`. On the other hand, these vertical modules, include controlled vocabularies, according to SKOS ontology, for different important areas in the academic domain, namely,  geographical locations (`geopolitical`) , knowledge areas (including concepts for `scientific-domains`, `subject-areas` or `unesco-codes`), classification of project types (`project-classification`), resource positions in universities (`university-HR` for Spain, UK or Portugal), controlled vocabulary with all universities in Spain (`university-structure`) or  some extensions for the Spanish university system (`extensions-es`).

![](https://github.com/HerculesCRUE/ROH/blob/gh-pages/media/ROH-core-vertical-modules.png)

**Figura** **1**. ROH core module and its associated vertical modules.
