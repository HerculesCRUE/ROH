![](.//media/CabeceraDocumentosMD.png)

ROH Network of Ontology documentation 
=====================================

[**1. Ontological design**](#headSection1)

[***1.1. Design rationale***](#headSection1-1)

[***1.2. Ontology design process***](#headSection1-2)

[**2. Conceptual diagram of ontology ROH**](#headSection2)

[**3. Project entity**](#headSection3)

[**4. Person entity**](#headSection4)

[**5. Organization entity**](#headSection5)

[**6. Funding entity**](#headSection6)

[**7. Research Object entity**](#headSection7)

[**8. Research Activity entity**](#headSection8)

[**9. Other entities in ROH**](#headSection9)

[**Bibliography**](#bibliography)

This documentation area includes the following files:
- `0 - OntologyTutorial.pdf` - [PDF](https://herculescrue.github.io/ROH/0%20-%20OntologyTutorial.pdf) corresponding to a tutorial about the ROH network of ontologies. This a PDF document including essentially the contents of section 2 of this Markdown document.
- `1- OntologyDocumentation.pdf` - [PDF](https://herculescrue.github.io/ROH/1-%20OntologyDocumentation.pdf) describing in tabular form, every entity modelled in ROH.
- `2- CoberturaPreguntasCompetencia.pdf` - [PDF](https://herculescrue.github.io/GnossDeustoOnto/2-%20CoberturaPreguntasCompetencia.pdf) with [competence questions which have been assessed against ROH](https://herculescrue.github.io/GnossDeustoOnto/2-%20CoberturaPreguntasCompetencia.pdf).
- `3- Ejecución de preguntas de competencia.md`	- [Link](https://github.com/HerculesCRUE/GnossDeustoBackend/tree/master/validation-questions) to page to execute competency questions against ROH
- `4- Modelo de multilingüismo.md`	- [Markdown document](https://herculescrue.github.io/ROH/4-%20Modelo%20de%20multiling%C3%BCismo) explaining how multilinguism is supported in ROH
- `5- Método para el control de versiones OWL.md` - [Markdown document](https://herculescrue.github.io/ROH/5-%20M%C3%A9todo%20para%20el%20control%20de%20versiones%20OWL) explaining how version control is carried out in ROH
- `README.md` - This very file which includes documentation in Markdown of ROH, describing with text and diagrams the relationships among the main entities in ROH

**<a name="headSection1"></a>1. Ontological design**
====================================================

This section is going to break down from minor to major detail the
design of the ROH ontology network. Starting in [section 2](#headSection2) with a high
level diagram, the most important entities will be shown. Then, the main
entities modelled are broken down (sections [section 3](#headSection3) to [section 9](#headSection9)). But, first notice that readers are encouraged to read through the rationale behind the ontology design in [section 1.1](#headSection1-1)

Notice that ROH network of ontologies is divided into 2 [main parts](https://github.com/HerculesCRUE/ROH/tree/main/roh/modules). 
- The generic ontology, **core module** , contains the most important entities and properties to model information in the academic domain. It contains the central part of the network of ontologies. It covers the academic domain, being agnostic to the country or the research organization whose information wants to be modelled with.
- A set of **vertical modules** which include, on one hand, specializations of some academic concepts for a given country domain. For instance, the figure Associate Professor in the Spanish academic domain would be encountered in the vertical module `university-HR-es` and is assigned the URI `http://purl.org/roh/university-hr/es#ProfesorTitularDeUniversidad`. On the other hand, these vertical modules, include controlled vocabularies, according to SKOS ontology, for different important areas in the academic domain, namely, geographical locations (`geopolitical`) , knowledge areas (including concepts for `scientific-domains`, `subject-areas` or `unesco-codes`), classification of project types (`project-classification`), resource positions in universities (`university-HR` for Spain, UK or Portugal), controlled vocabulary with all universities in Spain (`university-structure`) or some extensions for the Spanish university system (extensions-es).

![](.//media/ROH-core-vertical-modules.png)
**Figura**  **1**. Hierarchical module structure of ROH network of ontologies.

To incorporate [specific modules](https://github.com/HerculesCRUE/ROH/tree/main/roh/modules) to the ontology, it is enough to create a new ontology, import the required higher level ontology entities and create the new classes or properties needed. Both classes and properties can be integrated within the existing hierarchy in the imported ontology. Let's say, for example, that a new university (e.g. University of Castilla La-Mancha) wants to make use of ROH and needs to add a series of positions of its own through which to classify research technicians. To do this, you can import the core ontology, and under `vivo:Position`, in which the hierarchies for the typical university positions appear, create your own as subclasses.

The automatically generated documentation, through the Widoco tool, for each ontological part is referenced below:
- Widoco generated documentation for [ROH core module](https://herculescrue.github.io/GnossDeustoOnto/roh/)
- Widoco generated documentation for [ROH vertical modules](https://herculescrue.github.io/GnossDeustoOnto/rohes/)

The following table shows a summary of the reused ontologies together with their respective user licenses. All reused ontologies have been evaluated for compatibility with their
import and extension.

| prefix | Ontology names | License | Ontology website |
| --- | --- | --- | --- |
| **bibo** | Bibliographic Ontology | Creative Commons Attribution 1.0 Generic (CC BY 1.0) | [http://purl.org/ontology/bibo](http://purl.org/ontology/bibo) |
| **foaf** | FOAF (Friend of a Friend) Vocabulary Specification | Creative Commons Attribution License 1.0 | [http://xmlns.com/foaf/0.1](http://xmlns.com/foaf/0.1) |
| **geonames** | Geonames ontology | Creative Commons Attribution License 3.0 | [http://www.geonames.org/ontology#](http://www.geonames.org/ontology) |
| **obo** | Open Biological and Biomedical Ontology (OBO) | Creative Commons Attribution License 4.0 | [http://purl.obolibrary.org/obo/](http://purl.obolibrary.org/obo/) |
| **obo-bfo** | OBO Foundry, Basic Formal Ontology | Creative Commons Attribution License 4.0 | http://www.obofoundry.org/ontology/bfo.html |
| **obo-ero** | OBO Foundry, eagle-i Research Resource Ontology (ERO) | Creative Commons Attribution License 4.0 | https://open.catalyst.harvard.edu/wiki/display/eaglei/Ontology |
| **obo-iao** | OBO Foundry, Information Artifact Ontology | Creative Commons Attribution License 4.0 | https://github.com/information-artifact-ontology/IAO/ |
| **obo-ro** | OBO Foundry, Relations Ontology | Creative Commons Attribution License 4.0 | http://www.obofoundry.org/ontology/ro.html |
| **rdfs** | RDF Schema | Creative Commons Attribution License 4.0 | [http://www.w3.org/2000/01/rdf-schema#](http://www.w3.org/2000/01/rdf-schema) |
| **roh** | Red de Ontologías Hercules | Creative Commons Attribution License 4.0 | [http://purl.org/roh](http://purl.org/roh) |
| **skos** | SKOS Simple Knowledge Organization System RDF Schema | Creative Commons Attribution License 4.0 | [http://www.w3.org/2004/02/skos/core#](http://www.w3.org/2004/02/skos/core) |
| **terms** | DCMI Metadata Terms | Creative Commons Attribution License 4.0 | https://www.dublincore.org/specifications/dublin-core/dcmi-terms/ |
| **vcard** | vCard Ontology - for describing People and Organizations | Creative Commons Attribution License 4.0 | [https://www.w3.org/2006/vcard/ns#](https://www.w3.org/2006/vcard/ns) |
| **vivo** | VIVO Ontology for Researcher Discovery | Creative Commons Attribution License 4.0 | [http://vivoweb.org/ontology/core#](http://vivoweb.org/ontology/core) |

***<a name="headSection1-1"></a>1.1. Design rationale***
===========================

In order to maximize the reuse of ontologies established in the community and the compatibility of new developments within the framework of ASIO, priority has been given to the reuse of all those entities (both classes and properties) that already fulfilled the objective of modeling the different aspects required. These reused entities have been combined among them and with entities explicitly created in the ROH ontology in order to model the data properly.

When designing and developing the ontology, priority has been given to its flexibility to ensure easy extensibility. This has been achieved thanks to two factors: the categorization of concepts instead of the use of hierarchies and the modularity of the ontology. By avoiding hierarchies, the ontology can be much more flexible, since different institutions can use different hierarchies to classify their projects (for example, universities that classify their projects according to the geographical scope of the call, as opposed to other universities that classify them according to the public or private nature of the call). Against this, the use of categories has been prioritized, properties that allow the categorization of entities according to different criteria. However, thanks to its modular design ([core and vertical modules](https://github.com/HerculesCRUE/ROH/tree/main/roh/modules)), our ontology allows any European country, territorial administrative entity or university to develop its own sub-ontology (refinements and extensions) that adapts to its reality. This way, if an institution wants, for example, to create its own project hierarchy, it would only have to import the ontology of the immediately superior area and create its own hierarchy from the vivo:Project entity.

In the same way, and to avoid explicit declaration of hierarchies, a series of Defined Classes have been defined. A Defined Class is a class that cannot be an instance directly, but rather, an instance will belong to it only if it complies with a series of restrictions. These classes have been used to define, for example, when an organization is a Funding Organization. Instead of having to explicitly define the organization as a Funding Organization, the organization will be defined with its corresponding class (University, Research Organization, Government Agency, etc.) and in the event that it meets a series of restrictions, in this case, being a funder of some call, the OWL reasoner will automatically classify it as a Funding Organization.

![](.//media/restrictions.png)

When implementing this ontology, the reuse of ontologies has been prioritized, thus allowing the compatibility of the data represented through ROH with other data represented through other ontologies. 

Another feature has been the extensive use of OWL constraints (owl:allValuesFrom, and owl:someValuesFrom properties). Through these properties it is possible to indicate, for a specific class of the ontology, which properties are optional for an entity to belong to this class, as well as the corresponding range. This makes the ontology itself serve as documentation when modelling data.

In the process of creating this ontology network, the following design principles have been considered:
- *Reusability* - modelling of concepts again has been avoided if an ontology has been located that comprehensively models a given concept. 
E.g, the concept of a position held by a person in an academic organization, which is extensively documented in the ontology VIVO Ontology for Research Discovery.
- *Extensibility* - since, although academic information modelling shares many aspects universally, there are aspects that are specific to the country in question. 
E.g, 6-year periods in Spain, or the University or research centre in question, for example, job positions considered at the University of Murcia. 
- *Maintainability* – the modularization of the network of ontologies in distinct contextualized refinements seeks an easier maintenability of ROH
- *Integrity* – by the application of ontological restrictions and validation scripts in languages like SHACL, to preserve also Integrity. The OWL `some` and `only` restrictions to express if a property is compulsory and the valid data type associated to it, respectively, have been used. Besides, the cardinality restrictions `min`, `max` and `exactly` have been applied to the ontology. This allows to ensure a good validation of the instance data generated through ROH regarding the academic domain, since only the valid relationships in the correct cardinality can be established among entities.
- *Usability* - in the design of ROH we did not only consider to make this network of ontologies comprehensive and exhaustive, i.e. covering the maximum number of academic world concepts and their properties, but also, and very importantly, making it USABLE.In ontological design, often entities and properties are very superficially described, following the Open World principle. However, we wanted, from the start, to make the devised network of ontologies usable by those that need to instantiate it, independently on whether they are ontology engineers or just developers. Developers working in a CRIS (current research information system) need to understand what properties are compulsory, which are optional, and what data types they need to use to populate RDF graphs through ROH. This explains why in ROH a big effort has been paid to document well the ontology and to introduce ontological restrictions that validate the correct instantiation of entities.  

The most commonly used ontological design patterns in ROH design have been the following: 
- [*PartOf*](http://ontologydesignpatterns.org/wiki/Submissions:PartOf): allows the representation of entities and their corresponding parts. For example, in ROH, a `foaf:Organization` `obo-ro:hasPart` `foaf:Organization`, that is, an organization can be composed of sub-organizations, and this can be `obo-ro:partOf`, that is, be part of a parent organization. 
- [*Participation*](http://ontologydesignpatterns.org/wiki/Submissions:Participation): allows you to represent the participation of an object in an activity or event. In ROH, we have used this design pattern to model, for example, the roles that a `foaf:Agent` through an `obo-bfo:Role` can play (`obo-bfo:realizedIn`) in a `roh:Activity`. 

***<a name="headSection1-2"></a>1.2. Ontology design process***
===============================================================

The process followed to design an ontology that models a Research Management System (RMS) for Spanish universities and that is piloted and validated at the University of Murcia, has been the following: 
1. Meet the requirements defined in "Annex I. Ontology requirements analysis" and "Annex II. Ontologies and other resources to be used". Delivered by GNOSS-DEUSTO as part of the feasibility study for "R&D service for the development of the ontological infrastructure and semantic architecture of the research management system (sgi) of the Hercules initiative", file number: 2018/88/OT-AM
2. Selection and analysis of the main ontologies that model the academic environment. Including contrast with [CERIF - ERD](https://www.eurocris.org/Uploads/Web%20pages/CERIF-1.5/cerif.html) vocabulary, entity relationship diagram, since it is the standard information model for CRIS (Current Research Information System) systems.
3.	Identification of the main entities and relationships to model the knowledge of the academic world. Fulfilling the requirements of of the ASIO project. 
4.	Validation of the flexibility, completeness and integrity of the ROH ontology network through the following evaluations:
  - Review the [questions/competency queries](https://github.com/HerculesCRUE/GnossDeustoOnto/blob/master/Documentation/2-%20CoberturaPreguntasCompetencia.pdf) of the network listed by the University of Murcia (UM) and implement as a [suite of SPARQL queries](https://github.com/HerculesCRUE/GnossDeustoBackend/tree/master/validation-questions/sparql-query) to validate their compliance. As a result of this validation, some new data and object properties were added. 
  - Review datasets offered by the University of Murcia and check that their data can be modelled with the entities and properties defined within ROH. Extension and adaptation of ROH entities according to the determined needs. 
  - [Mapping of FECYT CVN format data to the ROH ontology](https://github.com/HerculesCRUE/GnossDeustoBackend/tree/master/cvn). Where there were unmodelled entities or relationships, they were included. Details of the mapping between CVN and ROH entities appear in the cvn/config folder.  
5.	Continuous refinement validated by a Continous Integration (CI) process. A battery of regression tests regulate that new changes introduced continue to guarantee the quality of ROH, its flexibility and extensibility to accommodate new requirements. 



**<a name="headSection2"></a>2. Conceptual diagram of ontology ROH**
====================================================================

Figura 2 shows the main entities modelled in the Hercules Ontology
Network (HON in English, ROH-Red de Ontologías Hércules in Spanish).
Note that in the diagram, the arrows with a filled tip denote kinship
(inheritance) relationships while the arrows that end in a non-filled
tip indicate that there is an Object Property relationship between these
entities. Finally, the dashed arrows reflect the fact that several
entities in ROH have geographic (class Geonames:Feature) and temporal
(class vivo:DateTimeInterval) constraints.

![](.//media/ROH-high-level-diagram.png)

**Figura** **2**. High level diagram of ROH --Red de Ontologías
Hércules.

Notice that section 3 of the [ROH Ontology Specification document](https://github.com/HerculesCRUE/GnossDeustoOnto/blob/master/Documentation/0%20-%20OntologySpecification.pdf) includes a detailed discussion on how entities modelled in ROH have been imported and aligned with other existing entities in widely adopted ontologies that have successfully modelled parts of the Academic domain. 


**<a name="headSection3"></a>3. Project entity**
================================================
    
The main ROH entity is `vivo:Project` (see Figura 3), a new entity defined within ROH. In ROH, a project models a collaborative activity in business and science that often involves research or design and is carefully planned to achieve a particular goal. Its configuration is inspired by the `swrc:Project` and takes into account the data properties of the `cerif:Project` and `vivo:Project`. It comprises all those properties and adds some new ones, for example, `roh:projectStatus`, `roh:modality` or `roh:title`.

It includes the Data Properties `roh:identifier`, `vivo:abbreviation`, `vivo:description`, `roh:title`, `vivo:freeTextKeyword`, `roh:modality`, `roh:foreseenJustificationDate`, `roh:projectObjective` and `roh:needsEthicalValidation`.

An `vivo:Project` includes a property `roh:hasKnowledgeArea` with allows to associate a project with different instances of knowledge areas, e.g. instances of `skos:Concept` belonging to `roh:UNESCOKnowledgeArea` controlled vocabulary or concept scheme. Besides, it allows a project to be classified (`roh:hasProjectCategorization`) according to the project categories defined in a hierarchy defined under the concept scheme `roh:ProjectClassification`, e.g. the concept instance `http://purl.org/roh/project-classification#Horizon2020`. Likewise a project might be associated to the recruitment of a new human resource, in that case `roh:hasHRClassification` allows to link a project with a `roh:HumenResourceClassification`. A project may go through different stages, i.e. `roh:projectStatus` during its lifetime, e.g. `roh:Open`, `roh:ProposalSubmitted`, `roh:Rejected` or `roh:Closed`.

Besides, an instance of a `vivo:Project` is associated to the following entities through object properties:

- An `roh:Activity` is `roh:participatedBy` by a project, describes what activities a project participates in.
- `skos:Concept` is linked through `roh:knowledgeAreaOf` to a project, indicating the topics/concepts a project deals with. A project may be classified according to distinct taxonomies (concept schemes) for roh:`ProjectClassification` and `roh:HRClassification` (human resources).
- `roh:Dossier` through relationship `vivo:relates` binds a set of documents, including the proposal, evaluation document, reports and so on with a `vivo:Project`. A dossier is an administrative file collection in which all assets related to a project are stored, including the research pProposal, approval documents, viability plans and so on associated to a project are stored.
- `roh:Funding` through `roh:supports` a `vivo:Project`, where a funding is divided into (through `obo-ro:BFO_0000051 (has part)`) several Funding Amounts (`roh:FundingAmount`). A `roh:FundingAmount` `roh:grants` a `foaf:Organization` and describes the details about the funding associated to a project to given organization, indcluding period and amounts. A `roh:FundingSource` is `roh:promotedBy` a `vivo:FundingProgram` which is `roh:promotedBy` a `vivo:FundingOrganization`. Different organizations (`foaf:Organization`) may play different (`obo-bfo:BFO_0000023`) roles in a project, e.g. `vivo:MemberRole` or `vivo:AdministratorRole`. Notice that the object property `vivo:relates` allows to link a `foaf:Agent`, being it either an Organization or a Person, with a (`obo-bfo:BFO_0000023`) role.
- `roh:Justification` through relationship `vivo:relates` binds justifications with a `vivo:Project`.
- `foaf:Person`, where a person may play different (`obo-bfo:BFO_0000023`) roles, e.g. `vivo:PrincipalInvestigatorRole` or `vivo:ResearcherRole`.
- `vivo:ProjectContract` subtype of `vivo:Contract` allows a project to be associated to a contract through relationship `roh:hasContract`.
- `roh:ProjectExpense` is `roh:spentBy` a project, details allows to associate a project with its expenses.
- `roh:ResearchObject`, where a project `roh:produces` several `roh:ResearchObject`, where some results of a project might be for example of types `bibo:Journal`, `obo-iao:JournalArticle`, or `roh:PhDThesis`.

Notice that a `vivo:Project` may also be part (`obo-ro:BFO_0000051`) of another project, e.g. child of a parent project. Besides, every instance of a `vivo:Project` is time bound by being associated with an instance of `vivo:DateTimeInterval` and geographically bound to an instance of `gn:Feature` (through relationship (`gn:locatedIn`).

The following table shows the object and data properties associated to vivo:Project:

![](.//media/1.roh-project-table.png)

![](.//media/1.roh-project.png)

**Figura** **3**. Ontological diagram for entity Project.



**<a name="headSection4"></a>4. Person entity**
===========================================

In ROH, there is a `foaf:Person` entity (see Figura 4) that inherits from `foaf:Agent`. The specialization of this entity imported from the VIVO ontology already adds some DataType properties of the research domain, but in ROH we have also incorporated `roh:taxID`, `roh:ORCID`, `vivo:researcherId` or `vivo:scopusId` (all of them are subtypes of `vivo:identifier`, a given person may use or several alternatives of those identifiers) and also several object specific properties of the research domain as &quot;has a Role&quot; (`roh:hasRole`) in an Organization, &quot;has a CurriculumVitae&quot; (`roh:hasCV`), &quot;has some Accreditations&quot; (`roh:hasAccreditation`), &quot;has an Employment Contract&quot; (`roh:hasContract`), &quot;has some Knowledge Areas&quot; (`roh:hasKnowledgeArea`) or &quot;has some Roles&quot; (`roh:hasRole`) in Projects or participates through `bibo:authorList` with Research Objects of subclass `bibo:Document`. A person can &quot;have different roles&quot; in the Project over time. As a subclass of `foaf:Agent` inherits some additional object properties such as `roh:hasAccreditation` or `roh:hasContactInfo`.

As mentioned above, `foaf:Person` in ROH is based on FOAF (Friend of a Friend [2], following patterns used in VIVO. That explains why it includes some of the basic FOAF properties such as `foaf:name`, `foaf:nickname`, `foaf:title`, `foaf:mbox` (note that this in fact an object property), `foaf:img` (note that this in fact an object property), `vivo:description`, `foaf:firstName` and `foaf:surname`. However, it considers all attributes and links defined in CERIF through the `cfPers` entity. `foaf:Person` incorporates the following data properties declared as attributes in `cfPers`, especially: identifier (`vivo:identifier` but preferably `roh:ORCID`), `roh:birthdate`, `foaf:gender`, `foaf:homepage` (note that this in fact an object property), `roh:researchLine`, `vivo:freeTextKeyword`. Some important CERIF relationships that have also been adopted: Curriculum Vitae (`roh:hasCV`) which links `foaf:Person` with `roh:CurriculumVitae`, Event (`roh:Activity`) and Indicator (`roh:Accreditation`).

Besides, an instance of a `foaf:Person` is associated to the following entities through object properties:

- `roh:AuthorMetric`, where a researcher may have associated metric values such as h-index or i10 index.
- `roh:AcademicSubject`, where a researcher may teach different subjects, which is a concept scheme.
- `vivo:AwardedDegree`, where a researcher `vivo:relates` with an `roh:AcademicDegree`
- `roh:Accreditation`, where a researcher `roh:hasAccreditation` of different types, e.g. `roh:ResearchAccreditation` or `roh:AcademicAccreditation`.
- `roh:Activity`, where a researcher `roh:participates` in diverse activities, e.g. `vivo:InvitedTalk` or `bibo:Conference`.
- `skos:Concept` is linked through `roh:knowledgeAreaOf` to a person, indicating the different knowledge areas (`roh:KnowledgeArea`) a researcher is specialized on.
- `roh:CurriculumVitae`, where a researcher `roh:hasCV` which includes a data type property like `roh:summary`. A researcher is also bound to author metrics `roh:AuthorMetric` through property `roh:hasMetric`. 
- `bibo:Document`, where a researcher through `bibo:authorList` is participating in a `bibo:Document` as one of its authors.
- `vcard:Individual`, where a researcher `roh:hasContactInfo` described through ontology vcard.
- `vivo:Position`, where a researcher `roh:hasPosition` usually in an organization linking it to any of the `vivo:Position` subclasess like `vivo:FacultyAdministrativePosition` or `vivo:FacultyPosition`.
- `vivo:PersonExpense`, where a researcher may contribute with several expenses for its research activities.
- `roh:ResearchObject`, where a researcher is the `roh:correspondingAuthor` of different subtypes of `roh:ResearchObject`, e.g. `obo-iao:JournalArticle`, `vivo:ConferencePaper` or `bibo:Proceedings`.
- `obo-bfo:BFO_0000023` (Role), where a `foaf:Agent` may `roh:hasRole` like `vivo:ResearcherRole` or `vivo:TeacherRole` either in a `vivo:Project` or a `foaf:Organization`.
- `roh:PersonContract`, where a researcher `roh:hasContract` described according to the attributes corresponding to parent class `vivo:Contract`.
- `bibo:Thesis`, where a researcher is `roh:supervisorOf` of a `bibo:Thesis`, concretely, any of its subtypes subclasess like `roh:MasterThesis` or `roh:PhDThesis`.

The following table fully describes the object and data properties defined within the foaf:Person entity in ROH.

![](.//media/2.roh-person-table.png) 

![](.//media/2.roh-person.png)

**Figura** **4**. Ontological Diagram for entity Person.



**<a name="headSection5"></a>5. Organization entity**
=================================================

An Organization in ROH (see Figura 5) is a `foaf:Organization` which carries out several `vivo:Project`s. It is a child of `foaf:Agent`. Some organization can emit `roh:Acreditation` (e.g. ANECA or CENAI in Spain), those belonging to subclass `roh:AccreditationIssuer`, or award degrees (`vivo:AwardedDegree`), those of subclass `vivo:University`. An Organization may receive several `roh:FundingAmount`s, corresponding to a `roh:Funding`, obtained through a `roh:FundingProgram` provided by a `vivo:FundingOrganization` through a `roh:FundingSource`. As a `foaf:Agent` an Organization may be involved in several `roh:Actitity`s, has several instances of attribute `vivo:freetextKeyword`, is associated through `roh:hasKnowledgeArea` with `roh:KnowledgeArea` and bound to a geographical scope through `gn:locatedIn` with `gn:Feature`, it may also have a time span through `vivo:dateTimeInterval` linking it with an instance of `vivo:DateTimeInterval`.

Based on FOAF [10], the `foaf:Organization` entity takes into account the data properties (attributes: `vivo:abbreviation`, `foaf:homepage`) and data properties (links) defined by the Organization Unit in CERIF. It also takes into account and supports the relationships of CERIF Equipment (via entity `vivo:Equipment` and object property `roh:hasReservable`), Event (`roh:Activity`), Expertise and Skill (via `vivo:freeTextKeyword` and `roh:hasKnowledgeArea`), Facility (`roh:Facility` and `roh:hasReservable`), Funding (`roh:Funding`), Organization Unit (kinship relationships between organizations can be established with `obo-ro:BFO_0000051` (has part) and `obo-ro:BFO_0000051` (part of), Prize Award (through `roh:Accreditation`), Result Patent, Result Product, Result Publication and Service - all of them through `roh:ResearchObject` which can be obtained through the `roh:produces` relationship from the Projects in which an organization participates by playing a declared role through `roh:hasRole`, Person (through `roh:hasPosition`) may hold different positions. Therefore, the CERIF data model for Organization is covered.

An exhaustive hierarchy of organizations is included, e.g. `roh:AccreditationIssuer`, `vivo:Company` or `vivo:University`, among many others.

Besides, an instance of a `foaf:Organization` is associated to the following entities through object properties:

- `roh:Accreditation`, where an organization of type `roh:AccrediationIssuer` issues (`roh:issues`) accreditations, e.g. `roh:ResearchAccreditation` or `roh:AcademicAccreditation`.
- `roh:Activity`, where an organization may play `vivo:OrganizerRole` through `roh:hasRole` in an activity or may through its participation role in a project participate (`roh:participates`) in an activity.
- `vivo:AwardedDegree`, where a `vivo:University` may `roh:awards` degrees which are related to both a concrete `vivo:AcademicDegree` and an instance of `foaf:Person`.
- `skos:Concept`, where an organization through `roh:hasKnowledgeArea` may be associated to several knowledge areas, defined as instance data of thesaurus created with SKOS ontology. A concept linked to an organization must necessary belong to `roh:KnowledgeArea` concept scheme.
- `vivo:Company`, where an organization might be linked to several spinoffs through object property `roh:hasSpinOff`.
- `vivo:DateTimeInterval`, where an organization may exist during a given time interval
- `foaf:Document`, where an organization may be associated to several homepages `foaf:homePage`
- `gn:Feature` through relationship `gn:locatedIn`, where an organization may be associated a geographical scope.
- `roh:FundingAmount` where an organization may receive several funding amounts part of a `roh:Funding` through `roh:grants` object property.
- `vcard:Organization`, where an organization `roh:hasContactInfo` described through ontology vcard.
- `roh:Reservable`, where an organization may `roh:hasReservable`, belonging to any of its subclasses, e.g. `roh:Equipment`, `roh:Facility` or `obo-ero:ERO_0000071` (Software).
- `roh:PatentApplication` or `bibo:Patent` where an organization is the owner `(roh:ownerOrganizationOf`) of different patent applications or granted patents. Notice that a `roh:PatentApplication` goes through different status (`roh:patentStatus`) and is associated with a granted patent (`bibo:Patent`), only when its status passes to be `roh:Accepted`, through object property (`roh:hasPatent`).
- `foaf:Organization`, where an organization may be linked through `vivo:hasSucessorOrganization` or `vivo:hasPredecessorOrganization` with another `foaf:Organization` or may be part of (`obo-ro:BFO_0000050`) or include `obo-ro:BFO_0000051` (has part) other several `foaf:Organization`s.
- `obo-ero:ERO_0000005` (Service), where an organization `roh:provides` several services, e.g. `obo-ero:ERO_0000392` (Storage Service)

Check the following table for more details on object and data properties for foaf:Organization.

![](.//media/2.roh-organization-table.png) 

![](.//media/3.roh-organization.png)

**Figura** **5**. Ontological diagram for entity Organization.



**<a name="headSection6"></a>6. Funding entity**
============================================

The `roh:Funding` entity (see Figura 6), new in ROH, represents the funding associated with a project (vivo:Project) whose funding is associated with a funding program (`roh:FundingProgram`) and comes from a (`roh:FundingSource`), which in turn is associated with a funding organization (`vivo:FundingOrganization`). A funding is divided into several amounts (`roh:FundingAmount`), associated with the different entities that participate in a project and the annuities in which they do so. Funding amounts for an organization in a projects are expressed through data properties `roh:monetaryAmount` and `roh:currency`.

A funding can be marked as public through property `roh:publicFunding`, qualified by properties `vivo:identifier`, `vivo:description` and `vivo:freeTextKeyword` and classified into any of the following subclasses: `roh:Grant`, `roh:Loan`, `roh:Outsourcing` or `roh:RefundableAdvance`.

The funding organization (`vivo:FundingOrganization`) (see Figura 6), imported from VIVO [1], inherits from `foaf:Organization`, promotes (`roh:promotes`) research through different funding programs (`roh:FundingProgram`) and through different funding sources (`roh:FundingSource`). A `roh:Funding` is associated with `roh:FundingAmount`s through object property `obo-ro:BFO_0000051` (has part). A `roh:FundingProgram` funds (`roh:funds`) a `roh:Funding`, funding programs are promoted by `roh:FundingOrganization`s. Notice that a `roh:Funding` is divided into several `roh:FundingAmount`s associated with different `foaf:Organizations` through the `roh:grants` relationship.

The Funding Program entity (`roh:FundingProgram`) (see Figura 6), new in ROH, defines the funding initiatives promoted (`roh:promotedBy`) by a Funding Organization (`roh:FundingOrganization`) which is, likewise, promoted by a `roh:FundingSource`. A funding is in operation during a time interval (`vivo:dateTimeInterval`) and is usually linked to a geographical scope (`geonames:Feature`) as determined by the `roh:FundingProgram`.

The following table illustrates the object and data properties associated to entities dealing with the funding concept in ROH.

![](.//media/4.roh-funding-table.png) 

![](.//media/4.roh-funding.png)


**Figura** **6**. Ontological diagram for Funding.



**<a name="headSection7"></a>7. Research Object entity**
====================================================

The research object entity (`roh:ResearchObject`) is a new entity defined in ROH (see Figura 7) that corresponds to a research result generated by a person (researcher), usually through work on a project. Notice that `roh:ResearchObject` is a defined class, which follows these restrictions: `(((roh:correspondingAuthor some foaf:Person) and (roh:hasKnowledgeArea some

(skos:Concept and (skos:inScheme some roh:KnowledgeArea)))) and (roh:correspondingAuthor only foaf:Person) and (roh:hasKnowledgeArea only

(skos:Concept and (skos:inScheme some roh:KnowledgeArea)))) or (roh:producedBy only roh:Project)`. This is, an instance in ROH belongs to class `roh:ResearchObject` if it meets these restrictions.

A research object has been modelled around entity `obo-iao:IAO_0000030` (Information Content Entity). Such entity is linked to at least one `foaf:Person` through object property `roh:correspondingAuthor`. In the case of its subclass `bibo:Document`, the contributors of a research object are accessible through object property `bibo:authorList`. Usually a `roh:ResearchObject` results from working on a `vivo:Project` (`roh:produces`). Under `obo-iao:IAO_0000030` (Information Content Entity) entity a complete taxonomy of research objects mostly imported from BIBO [4], covering all kinds of publications, patents, software and web pages, is defined. Some examples are: `bibo:Collection`, `bibo:Journal`, `bibo:Article`, `bibo:Book`, `bibo:Chapter`, `vivo:DataSet`, `bibo:Patent`, `bibo:Thesis` and `bibo:Webpage`. The primary author of a research object is accessible through the `roh:correspondingAuthor` property. A `roh:ResearchObject` may have several knowledge areas bound to it through `roh:hasKnowledgeArea`, where the linked concepts should be associated to concept scheme `roh:KnowledgeArea` or one of its subclasses. The [vertical module](https://github.com/HerculesCRUE/ROH/tree/main/roh/modules) `knowledge-area` contains relevant instance data scientific domains, research subjects and UNESCO codes.

The most important research result is represented by the concept publication and is defined mainly through the imported entity `bibo:Document`. Currently, the following sets of entities related to the publication concept are supported: `bibo:Collection` (Newspaper, Magazine) and `bibo:Document` (Article, ConferencePaper, EditorialArticle, Book, Proceedings, ConferencePaper, Chapter, Thesis). `bibo:Thesis` has been refined into `roh:BachelorsThesis`, `roh:MastersThesis` and `roh:PhDThesis`.

Two entities worth mentioning that belong to the hierarchy of classes associated to the `roh:ResearchProject` are: `bibo:Report` and `roh:Dossier`. A `bibo:Report` has been refined to include subclasses `roh:EthicalReport` (which includes `roh:EthicalAudit` and `roh:EthicalValidation`), `roh:EvaluationSummary`, `roh:Justification` and `roh:ResearchProposal`. This implies that a report may correspond to ethical validation and auditing needs of a project, correspond to the evaluation of the project, its proposal or the set of documents corresponding to its justification.

On the other hand `roh:Dossier` represents a collection of reports related to a `vivo:Project`, which may include all the types of reports above mentioned.

The below tables show the data and object properties, as well as subclasses, of entity `roh:ResearchObject`.

![](.//media/5.roh-research-object-table-part0.png) 
![](.//media/5.roh-research-object-table-part1.png) 
![](.//media/5.roh-research-object-table-part2.png) 
![](.//media/5.roh-research-object-table-part3.png) 

![](.//media/5.roh-research-object.png)

**Figura** **7**. Ontological diagram for ResearchObject.



**<a name="headSection8"></a>8. Activity entity**
=============================================

The entity research activity (`roh:Activity`), new in ROH and visualized in Figura 8, represents the activities in which People participate (`roh:participes`) and organized by Organizations (`foaf:Organization`) reflected through the `roh:hasRole` relationship that connects with the intermediary entity `vivo:OrganizerRole`. Each activity is usually linked to a project through the relationship (`roh:participes`) and causes a project expenditure linked through (`vivo:relates`). A detailed hierarchy of activity subtypes is defined below `roh:Activity`: `bibo:Conference`, `vivo:Course`, `vivo:Internship` or `roh:ThesisViva`.

Related to Activity, it is also important to describe `roh:Expense`, which denotes the expenses incurred either by a project (`vivo:Project`) or person (`foaf:Person`) and associated through relationship `roh:spends`. Every expense has a time instant of associated expense (`vivo:DateTimeValue`) and other properties that qualify it as (`roh:monetaryAmount`, `roh:currency`, `roh:title` or `vivo:description`. The following subclasses of `roh:Expense` have been defined: `roh:PatentExpense`, `roh:PesonExpense`, `roh:ProjectExpense` and `roh:ResearchObjectExpense`. Besides, each expense can have associated a different type of expense through `roh:hasExpenseClassification` (Congress/network, external recruitment, Investment/inventory, office, other costs, publication, representation or staff expenses).

The following table illustrates the class hierarchy, object and data properties defined by `roh:Activity`.

![](.//media/6.roh-activity-table.png) 

![A picture containing map, text Description automatically
generated](.//media/6.roh-activity.png)

**Figura** **8**. Ontological diagram for entity Activity.



**<a name="headSection9"></a>9. Other entities in ROH**
===================================================

For more details on other entities in ROH check the tables detailing class hierarchies, object and data properties for all entities defined in ROH at the following PDF file: [https://github.com/HerculesCRUE/ROH/blob/gh-pages/1-%20OntologyDocumentation.pdf](https://github.com/HerculesCRUE/ROH/blob/gh-pages/1-%20OntologyDocumentation.pdf).

**Bibliography**
================

\[1\] «Ontology Reference - VIVO 1.10.x Documentation - LYRASIS Wiki».
\[En línea\]. Disponible en:
https://wiki.lyrasis.org/display/VIVODOC110x/Ontology+Reference.
\[Accedido: 12-feb-2020\].

\[2\] «Current research information system -
Wikipedia». \[En línea\]. Disponible en:
https://en.wikipedia.org/wiki/Current_research_information_system.
\[Accedido: 14-feb-2020\].

\[3\] «CERIF 1.5 Reference». \[En línea\].
Disponible en:
https://www.eurocris.org/Uploads/Web%20pages/CERIF-1.5/cerif.html\#cfResProd.
\[Accedido: 13-feb-2020\].

\[4\] «euroCRIS \| Current Research
Information Systems». \[En línea\]. Disponible en:
https://www.eurocris.org/. \[Accedido: 14-feb-2020\].

\[5\] «SKOS Simple
Knowledge Organization System Namespace Document 30 July 2008 "Last
Call" Edition». \[En línea\]. Disponible en:
https://www.w3.org/TR/2008/WD-skos-reference-20080829/skos.html.
\[Accedido: 13-feb-2020\].

\[6\] «Public Procurement Ontology». \[En
línea\]. Disponible en:
http://contsem.unizar.es/def/sector-publico/pproc.html. \[Accedido:
13-feb-2020\].

\[7\] «CVN». \[En línea\]. Disponible en:
https://cvn.fecyt.es/editor/cvn.html?locale=spa\#ENTRADA. \[Accedido:
13-feb-2020\].

\[8\] «SWRC-FE (SWRC Funding Extension) \| MORElab
Ontologies». \[En línea\]. Disponible en:
https://morelab.deusto.es/ontologies/swrcfe. \[Accedido:
13-feb-2020\].

\[9\] «GeoNames Ontology - Geo Semantic Web». \[En
línea\]. Disponible en:
http://www.geonames.org/ontology/documentation.html. \[Accedido:
13-feb-2020\].

\[10\] «FOAF Vocabulary Specification». \[En línea\].
Disponible en: http://xmlns.com/foaf/spec/. \[Accedido:
12-feb-2020\].

\[11\] «Bibliographic Ontology Specification \| The
Bibliographic Ontology». \[En línea\]. Disponible en:
http://bibliontology.com/. \[Accedido: 13-feb-2020\].

\[12\] «OOPS! --
OntOlogy Pitfall Scanner!» \[En línea\]. Disponible en:
http://mayor2.dia.fi.upm.es/oeg-upm/index.php/en/technologies/292-oops/index.html.
\[Accedido: 13-feb-2020\].