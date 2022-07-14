
## 2022-02-18 
### Fixed 
    - El prefijo de importacion de las ontologias skos y oa al prefiko w3id, en la ontologia, los datos, las preguntas y en la ontologia de unesko-individuals. 


## 2022-02-18 
### Added
	- Actualización de la tabla ResearchObject, se añade la propiedad vivo:relates para explicar y enfatizar que esta propiedad se puede usar para modelar las relaciones existentes entre los RO que componen un RS. También se añadió unas líneas explicativas en la documentación pertinente.


## 2021-12-01 - 2021-12-11 
### Fixed
    - Se corrigieron types y ciertos axiomas que estaban mal aunque no producían ningún tipo de error. Todos estos cambios están documentados y descritos en los commits. Estas descripciones se pueden ser en el siguiente enlace: [https://github.com/HerculesCRUE/ROH/commits/main](https://github.com/HerculesCRUE/ROH/commits/main). 
    

## 2022-06-16
### Fixed
    - changed the prefix of skos ontology to http://w3id.org/roh/mirror/skos#>  in es-scientific-domain.ttl and \es-subject-area.ttl


## 2022-07-14
## Fixed 
	-The following has been removed from bibo:AcademicArticle: vivo:hasPublicationVenue only  bibo:Journal
	- The following has been added from bibo:AcademicArticle: vivo:hasPublicationVenue only (bibo:Journal or bibo:Proceedings)
	- The following has been removed from  vivo:ConferencePaper:		vivo:hasPublicationVenue only  bibo:Journal
	- The following has been added from  vivo:ConferencerPaper: vivo:hasPublicationVenue only  bibo:Proceedings

    