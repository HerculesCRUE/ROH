package org.sigmaaie.ontologyfusion;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.AllValuesFromRestriction;
import org.apache.jena.ontology.DataRange;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.IntersectionClass;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.ontology.Restriction;
import org.apache.jena.ontology.SomeValuesFromRestriction;
import org.apache.jena.ontology.UnionClass;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryParseException;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFList;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Seq;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.ResourceUtils;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.XSD;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PropertyTemplate;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Fuse {

    private static final String ASIO = "http://purl.org/hercules/asio/core#";
    private static final String ROH = "http://purl.org/roh#";
    private static final String EXCEL_CONTENT_TYPE = 
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final boolean INCLUDE_STUBS = true;
    private static final Log log = LogFactory.getLog(Fuse.class);
    private static HashMap<String, String> prefixes1 = new HashMap<String, String>();
    private static HashMap<String, String> prefixes2 = new HashMap<String, String>();
    private static HashMap<String, String> prefixes2reverse = new HashMap<String, String>();
    private static Random random = new Random(System.currentTimeMillis());
    private static final List<String> entryPoints = Arrays.asList(
            "vivo:AcademicDegree", "roh:Accreditation", "rohes:Account", 
            "roh:Activity", "foaf:Agent", "skos:Concept", "roh:CurriculumVitae",
            "roh:Expense", "gn:Feature", "roh:Infrastructure", "roh:Metric", 
            "vivo:Project", "roh:ResearchObject", "bfo:Role", "ero:Service", 
            "rohes:Tax", "bfo:TemporalRegion");
    
    static {
        // ib-hercules prefixes
        prefixes1.put("asio", "http://purl.org/hercules/asio/core#");
        prefixes1.put("asioModules", "http://purl.org/asio/modules#");
        prefixes1.put("event", "http://purl.org/NET/c4dm/event.owl#");
        prefixes1.put("dc", "http://purl.org/dc/elements/1.1/");
        prefixes1.put("ro", "http://purl.org/wf4ever/ro#");
        prefixes1.put("dct", "http://purl.org/dc/terms/");
        prefixes1.put("deo", "http://purl.org/spar/deo/");
        prefixes1.put("org", "http://www.w3.org/ns/org#");
        prefixes1.put("owl", "http://www.w3.org/2002/07/owl#");
        prefixes1.put("pso", "http://purl.org/spar/pso/");
        prefixes1.put("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        prefixes1.put("xml", "http://www.w3.org/XML/1998/namespace");
        prefixes1.put("xsd", "http://www.w3.org/2001/XMLSchema#");
        prefixes1.put("adms", "http://www.w3.org/ns/adms#");
        prefixes1.put("asio", "http://purl.org/hercules/asio/core#");
        prefixes1.put("bibo", "http://purl.org/ontology/bibo/");
        prefixes1.put("dcat", "http://www.w3.org/ns/dcat#");
        prefixes1.put("doco", "http://purl.org/spar/doco/");
        prefixes1.put("foaf", "http://xmlns.com/foaf/0.1/");
        prefixes1.put("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
        prefixes1.put("skos", "http://www.w3.org/2004/02/skos/core#");
        prefixes1.put("time", "http://www.w3.org/2006/time#");
        prefixes1.put("vivo", "http://vivoweb.org/ontology/core#");
        prefixes1.put("cerif", "http://purl.org/cerif/");
        prefixes1.put("fabio", "http://purl.org/spar/fabio/");
        prefixes1.put("frapo", "http://purl.org/cerif/frapo/");
        prefixes1.put("modules", "http://purl.org/hercules/asio/modules#");
        prefixes1.put("semcerif", "http://eurocris.org/ontology/semcerif#");
        prefixes1.put("timezone", "http://www.w3.org/2006/timezone#");
        prefixes1.put("humantechreadiness", "http://sweetontology.net/humanTechReadiness#");
        // GnossDeusOnto prefixes
        prefixes2.put("roh", "http://purl.org/roh#");
        prefixes2.put("rohes", "http://purl.org/rohes#");
        prefixes2.put("rohpt", "http://purl.org/rohpt#");
        prefixes2.put("rohuk", "http://purl.org/rohuk#");
        prefixes2.put("event", "http://purl.org/NET/c4dm/event.owl#");
        prefixes2.put("ns", "http://www.w3.org/2003/06/sw-vocab-status/ns#");
        prefixes2.put("ro", "http://purl.org/roh/mirror/obo/ro#");
        prefixes2.put("bfo", "http://purl.org/roh/mirror/obo/bfo#");
        prefixes2.put("iao", "http://purl.org/roh/mirror/obo/iao#");
        prefixes2.put("obo", "http://purl.obolibrary.org/obo/");
        prefixes2.put("owl", "http://www.w3.org/2002/07/owl#");
        prefixes2.put("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        prefixes2.put("xml", "http://www.w3.org/XML/1998/namespace");
        prefixes2.put("xsd", "http://www.w3.org/2001/XMLSchema#");
        prefixes2.put("bibo", "http://purl.org/roh/mirror/bibo#");
        prefixes2.put("foaf", "http://purl.org/roh/mirror/foaf#");
        prefixes2.put("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
        prefixes2.put("skos", "http://www.w3.org/2004/02/skos/core#");
        prefixes2.put("vivo", "http://purl.org/roh/mirror/vivo#");
        prefixes2.put("skos1", "http://purl.org/roh/mirror/skos#");
        prefixes2.put("terms", "http://purl.org/dc/terms/");
        prefixes2.put("vitro", "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#");
        prefixes2.put("schema", "https://schema.org/");
        prefixes2.put("uneskos", "http://purl.org/umu/uneskos#");
        prefixes2.put("skos-thes", "http://purl.org/iso25964/skos-thes#");
        prefixes2.put("vcard", "http://purl.org/roh/mirror/vcard#");
        prefixes2.put("ero", "http://purl.org/roh/mirror/obo/ero#");
        prefixes2.put("geonames", "http://purl.org/roh/mirror/geonames#");
        for(String prefix : prefixes2.keySet()) {
            prefixes2reverse.put(prefixes2.get(prefix), prefix);
        }
    }
    
    public static void main(String[] args) throws IOException {
        if(args.length != 4) {
            log.info("Usage: [jar] mapping.tsv ontology1 ontology2 fused.ttl");
            return;
        }
        Model ontology1 = loadOntology(args[1]);
        Model ontology2 = ("omit".equals(args[2])) 
                ? ModelFactory.createDefaultModel() : loadOntology(args[2]);
        Model fused = null;
        boolean writeFused = true;
        if("docsonly".equals(args[0])) {
            fused = loadOntology(args[3]);
            writeFused = false;
        } else if("university-structure".equals(args[0])) {
            fused = processUniversityStructure(ontology1);
        } else if("geopolitical".equals(args[0])) {
            fused = processGeopolitical(ontology1);
        } else {
            fused = fuse(args[0], ontology1, ontology2);
        }
        if(writeFused) {
            fused.write(new FileOutputStream(new File(args[3]) + ".ttl"), "TTL");
        }
        Model sampleData = createSampleData(fused);
        sampleData.write(new FileOutputStream(new File(args[3]) + "-sampleData.ttl"), "TTL");
        OntModel ontology = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, fused);
        XSSFWorkbook spreadsheet = createSpreadsheet(ontology);
        OutputStream out = new BufferedOutputStream(new FileOutputStream(new File(args[3] + ".xlsx")));
        try {
          spreadsheet.write(out);
        } finally {
          if(out != null) {
              out.flush();
              out.close();
          }
        }   
    }
    
    private static Model fuse(String mappingFilePath, Model ontology1, 
            Model ontology2) throws IOException {
        Model fused = ModelFactory.createDefaultModel();
        fused.add(ontology1);
        fused.add(ontology2);
        fused = convertRoleIndividuals(fused);
        InputStreamReader in = new InputStreamReader(new FileInputStream(
                new File(mappingFilePath)), StandardCharsets.UTF_8);
    
        Iterable<CSVRecord> records = CSVFormat.TDF.parse(in);
        Iterator<CSVRecord> rows = records.iterator();
        if(rows.hasNext()) {
            rows.next(); // skip over the header 
        }
        int rowNum = 1;
        while(rows.hasNext()) {
            rowNum++;
            CSVRecord row = rows.next();
            if(row.size() == 0) {
                continue;
            }
            // first column = fromURI
            // second column = equivalentURI
            // third column = use instead URI
            // third column = superEntityURI
            // fourth column = notes; ignore
            String fromURI = uri(row.get(0), prefixes1, prefixes2);              
            String equivURI = (row.size() > 1) ? uri(row.get(1), prefixes2, null) : "";
            String useInsteadURI = (row.size() > 2) ? uri(row.get(2), prefixes2, null) : "";
            String superURI = (row.size() > 3) ? uri(row.get(3), prefixes2, null) : "";
            if(StringUtils.isEmpty(fromURI)) {
                continue;
            }
            if(!StringUtils.isEmpty(equivURI)) {
                if("x".equalsIgnoreCase(equivURI)) {
                    log.info("Deleting " + fromURI);
                    delete(fused, fromURI);
                } else {
                    log.info("Renaming " + fromURI + " to " + equivURI);
                    rename(fused, fromURI, equivURI);   
                }
            } else if (!StringUtils.isEmpty(useInsteadURI)) {
                log.info("Deleting description for " + fromURI + " and using " + useInsteadURI + " instead");
                useInstead(fused, fromURI, useInsteadURI);
            } else if (!StringUtils.isEmpty(superURI)) {
                log.info("Placing " + fromURI + " under " + superURI);
                addSuper(fused, fromURI, superURI);
            } else {
                log.info("Skipping row " + rowNum + " (" + fromURI + "). Nothing to do.");
            }
        }
        fused = addStatuses(fused);
        fused = addMisc(fused);
        fused = changeNamespace(fused, ASIO, ROH);
        fused = changeNamespace(fused, prefixes1.get("asioModules"), prefixes2.get("rohes"), "ES_");
        fused = changeNamespace(fused, prefixes1.get("asioModules"), prefixes2.get("rohpt"), "PT_");
        fused = changeNamespace(fused, prefixes1.get("asioModules"), prefixes2.get("rohuk"), "GB_");
        fused = updateOntologyResource(fused);
        fused = vacuumBrokenRestrictions(fused);
        fused = ensureLabels(fused, ROH);
        for(String prefix : prefixes1.keySet()) {
           String from = prefixes1.get(prefix);
           String to = prefixes2.get(prefix);
           if(to != null && !(to.equals(from))) {
               log.info("Changing namespace from " + from + " to " + to);
               fused = changeNamespace(fused, from, to);
           }
        }
        return fused;
    }
    
    /**
     * Remove broken owl:Restriction constructs from a model
     * @param model 
     * @return supplied model with bad triples removed
     */
    private static Model vacuumBrokenRestrictions(Model model) {
        Model removals = ModelFactory.createDefaultModel();
        StmtIterator restIt = model.listStatements(null, RDF.type, OWL.Restriction);
        while(restIt.hasNext()) {
            Statement restStmt = restIt.next();
            int statementsAboutRestriction = 0;
            StmtIterator restStmtIt = model.listStatements(restStmt.getSubject(), null, (RDFNode) null);
            while(restStmtIt.hasNext()) {
                restStmtIt.next();
                statementsAboutRestriction++;
            }
            // should be at least 3: the type declaration, onProperty and all/some/min/max/etc.
            if(statementsAboutRestriction < 3) {
                removals.add(model.listStatements(restStmt.getSubject(), null, (RDFNode) null));
                removals.add(model.listStatements(null, null, restStmt.getSubject()));
            }
        }
        model.remove(removals);
        log.info("Removed " + removals.size() + " bad restriction triples:");
        StringWriter sw = new StringWriter();
        removals.write(sw, "N3");
        log.info(sw.toString());
        return model;
    }
    
    /**
     * For any subjects in the specified namespace, add an English label
     * equal to the subject's local name if no other label exists
     * @param model
     * @param namespace
     * @return model with any missing labels added
     */
    private static Model ensureLabels(Model model, String namespace) {
        Model additions = ModelFactory.createDefaultModel();
        StmtIterator sit = model.listStatements();
        while(sit.hasNext()) {
            Statement stmt = sit.next();
            Resource subj = stmt.getSubject();
            if(!subj.isAnon() && subj.getURI().startsWith(namespace)) {
                if(!model.contains(subj, RDFS.label, (RDFNode) null)) {
                    additions.add(subj, RDFS.label, subj.getLocalName(), "en");
                }
            }
        }
        model.add(additions);
        return model;
    }
    
    private static Model updateOntologyResource(Model fused) {
        // previous namespace change will result in a owl:Ontology resource with
        // an extra hash on the end.  Rewrite this.  
        // TODO: update / replace metadata with appropriate new values
        ResourceUtils.renameResource(fused.getResource(ROH), ROH.substring(0, ROH.length() - 1));
        return fused;
    }
    
    private static Model changeNamespace(Model model, String fromNS, 
            String toNS) {
        return changeNamespace(model, fromNS, toNS, null);  
    }
    private static Model changeNamespace(Model model, String fromNS, 
            String toNS, String localNameStartsWith) {
        ResIterator resIt = model.listSubjects();
        while(resIt.hasNext()) {
            Resource res = resIt.next();
            if(!res.isAnon() && fromNS.equals(res.getNameSpace()) 
                    && (localNameStartsWith == null 
                    || res.getLocalName().startsWith(localNameStartsWith))) {
                ResourceUtils.renameResource(res, toNS + res.getLocalName());
            }
        }
        NodeIterator nodeIt = model.listObjects();
        while(nodeIt.hasNext()) {
            RDFNode node = nodeIt.next();
            if(node.isURIResource()) {
                Resource res = node.asResource();
                if(!res.isAnon() && fromNS.equals(res.getNameSpace())
                        && (localNameStartsWith == null 
                        || res.getLocalName().startsWith(localNameStartsWith))) {
                    ResourceUtils.renameResource(res, toNS + res.getLocalName());
                }
            }            
        }
        return model;
    }
    
    private static void rename(Model model, String fromURI, String toURI) {
        Resource fromRes = model.getResource(fromURI);
        Resource toRes = model.getResource(toURI);
        if(model.contains(fromRes, RDF.type, OWL.Class)) {
            model.add(toRes, RDF.type, OWL.Class);
        } else if(model.contains(fromRes, RDF.type, OWL.ObjectProperty)) {
            model.add(toRes, RDF.type, OWL.ObjectProperty);
        } else if(model.contains(fromRes, RDF.type, OWL.DatatypeProperty)) {
            model.add(toRes, RDF.type, OWL.DatatypeProperty);
        } else {
            log.warn("No type found for entity " + fromURI);
        }
        ResourceUtils.renameResource(fromRes, toURI);
    }
    
    private static void addSuper(Model model, String fromURI, String superURI) {
        Resource fromRes = model.getResource(fromURI);
        Resource superRes = model.getResource(superURI);
        if(model.contains(fromRes, RDF.type, OWL.Class)) {
            model.add(fromRes, RDFS.subClassOf, superRes);
            model.add(superRes, RDF.type, OWL.Class);
        } else if(model.contains(fromRes, RDF.type, OWL.ObjectProperty)) {
            model.add(fromRes, RDFS.subPropertyOf, superRes);
            model.add(superRes, RDF.type, OWL.ObjectProperty);
        } else if(model.contains(fromRes, RDF.type, OWL.DatatypeProperty)) {
            model.add(fromRes, RDFS.subPropertyOf, superRes);
            model.add(superRes, RDF.type, OWL.DatatypeProperty);
        } else {
            log.warn("No type found for entity " + fromURI + ". Skipping.");
        }
    }
    
    private static Model loadOntology(String path) throws IOException {
        Model m = ModelFactory.createDefaultModel();
        if(path.toLowerCase().endsWith(".rdf") || path.toLowerCase().endsWith(".owl")) {
            m.read(new FileInputStream(new File(path)), null, "RDF/XML");
        } else {
            m.read(new FileInputStream(new File(path)), null, "N3");
        }
        return m;
    }
    
    private static Model getDescription(Model model, String uri) {
        String describe = "DESCRIBE <" + uri + ">";
        QueryExecution qe = QueryExecutionFactory.create(describe, model);
        try {
            return qe.execDescribe();
       } finally {
            if(qe != null) {
                qe.close();
            }
        }
     }
    
    private static void useInstead(Model model, String fromUri, String insteadUri) {
        model.remove(getDescription(model, fromUri));
        rename(model, fromUri, insteadUri);
    }
    
    private static void delete(Model model, String uri) {
       model.remove(getDescription(model, uri));
       model.removeAll(model.getResource(uri), null, null);
       model.removeAll(null, model.getProperty(uri), null);
       model.removeAll(null, null, model.getResource(uri));
    }
    
    private static Model addMisc(Model model) {
        // authorCitation
//        Resource citationCount = model.getResource(ROH + "citationCount");
//        model.add(citationCount, RDF.type, OWL.ObjectProperty);
//        model.add(citationCount, RDFS.domain, model.getResource(prefixes2.get("foaf") + "Person"));
//        model.add(citationCount, RDFS.range, model.getResource(ROH + "QualifiedValue"));
        
        model.add(model.getResource(prefixes2.get("bibo") + "Article"), 
                RDFS.subClassOf, model.getResource(prefixes2.get("bibo") + "Document"));
        Resource authorListRest = model.createResource();
        model.add(authorListRest, RDF.type, OWL.Restriction);
        model.add(authorListRest, OWL.onProperty,
                model.getResource(prefixes2.get("bibo") + "authorList"));
        model.add(authorListRest, OWL.allValuesFrom, RDF.Seq);
        model.add(model.getResource(prefixes2.get("roh") + "ResearchObject"), 
                RDFS.subClassOf, authorListRest);
        Resource dateTimeRest = model.createResource();
        model.add(dateTimeRest, RDF.type, OWL.Restriction);
        model.add(dateTimeRest, OWL.onProperty, model.getResource(prefixes2.get("vivo") + "dateTime"));
        model.add(dateTimeRest, OWL.someValuesFrom, XSD.dateTime);
        model.add(model.getResource(prefixes2.get("vivo") + "DateTimeValue"), 
                RDFS.subClassOf, dateTimeRest);
        // move restriction on dateIssued from Journal to JournalArticle
        Resource restToMove = findRestriction(prefixes2.get("bibo") + "Journal", prefixes2.get("vivo") + "dateIssued", model);
        if(restToMove != null) {
            model.add(model.getResource(prefixes2.get("iao") + "IAO_0000013"), RDFS.subClassOf, restToMove);
            model.remove(model.getResource(prefixes2.get("bibo") + "Journal"), RDFS.subClassOf, restToMove);
        }
        // fix restriction(s) on bibo:Document from vivo:dateTime to vivo:dateTimeValue
        Resource restToFix = findRestriction(prefixes2.get("bibo") + "Document", prefixes2.get("vivo") + "dateTime", model);
        if(restToFix != null) {         
            model.remove(restToFix, OWL.onProperty, model.getResource(prefixes2.get("vivo") + "dateTime"));
            model.remove(restToFix, OWL.allValuesFrom, model.getResource(prefixes2.get("xsd") + "dateTime"));
            model.remove(restToFix, model.getProperty(OWL.getURI() + "maxQualifiedCardinality"), model.createTypedLiteral("1", XSDDatatype.XSDnonNegativeInteger));
            model.remove(restToFix, model.getProperty(OWL.getURI() + "onDataRange"), model.getResource(prefixes2.get("vivo") + "dateTime"));
            model.add(restToFix, OWL.onProperty, model.getResource(prefixes2.get("vivo") + "dateTimeValue"));
            model.add(restToFix, OWL.allValuesFrom, model.getResource(prefixes2.get("vivo") + "DateTimeValue"));
        }
        restToFix = findRestriction(prefixes2.get("bibo") + "Document", prefixes2.get("vivo") + "dateTime", model);
        if(restToFix != null) {
            model.remove(restToFix, OWL.onProperty, model.getResource(prefixes2.get("vivo") + "dateTime"));
            model.remove(restToFix, OWL.allValuesFrom, model.getResource(prefixes2.get("xsd") + "dateTime"));
            model.remove(restToFix, model.getProperty(OWL.getURI() + "maxQualifiedCardinality"), model.createTypedLiteral("1", XSDDatatype.XSDnonNegativeInteger));
            model.remove(restToFix, model.getProperty(OWL.getURI() + "onDataRange"), model.getResource(prefixes2.get("vivo") + "dateTime"));
            model.add(restToFix, OWL.onProperty, model.getResource(prefixes2.get("vivo") + "dateTimeValue"));
            model.add(restToFix, model.getProperty(OWL.getURI() + "maxQualifiedCardinality"), model.createTypedLiteral("1", XSDDatatype.XSDnonNegativeInteger));
            model.add(restToFix, model.getProperty(OWL.getURI() + "onClass"), model.getResource(prefixes2.get("vivo") + "DateTimeValue"));
        }
        // make hasKnowledgeArea visible on bibo:Patent
        Resource patentRest = model.createResource();
        model.add(patentRest, RDF.type, OWL.Restriction);
        model.add(patentRest, OWL.onProperty, model.getResource(prefixes2.get("roh") + "hasKnowledgeArea"));
        model.add(patentRest, OWL.allValuesFrom, model.getResource(prefixes2.get("roh") + "KnowledgeArea"));
        model.add(model.getResource(prefixes2.get("bibo") + "Patent"), RDFS.subClassOf, patentRest);
        return model;
    }
    
    private static Resource findRestriction(String classURI, String onPropertyURI, Model model) {
        StmtIterator sit = model.listStatements(model.getResource(classURI
                ), RDFS.subClassOf, (RDFNode) null);
        Resource restToMove = null;
        while(sit.hasNext()) {
            Statement stmt = sit.next();
            if(stmt.getObject().isResource() 
                    && model.contains(stmt.getObject().asResource(), 
                            OWL.onProperty, model.getResource(onPropertyURI))) {
                restToMove = stmt.getObject().asResource();
            }        
        }
        return restToMove;
    }
    
    private static Model addStatuses(Model model) {
        List<String> statuses = Arrays.asList("Accepted", "Cancelled", 
                "Closed", "Funded", "Rejected", "Signed", "Terminated", 
                "Unfunded", "Open", "Submitted", "Proposal Submitted", "Final", "Provisional");
        for(String status : statuses) {
            Resource statusRes = model.createResource(ROH + status.replaceAll(" ", ""));
            model.add(statusRes, RDF.type, OWL.Class);
            model.add(statusRes, RDFS.label, status, "en");
            model.add(statusRes, RDFS.subClassOf, model.getResource(ROH + "Status"));
        }
        List<String> statusProps = Arrays.asList("status", "patentStatus", "projectStatus", "evaluationStatus");
        List<Resource> restrictionsToUpdate = new ArrayList<Resource>();
        for(String statusProp : statusProps) {
            ResIterator restrictionit = model.listResourcesWithProperty(
                    OWL.onProperty, model.getProperty(ROH + statusProp));
            while(restrictionit.hasNext()) {
                restrictionsToUpdate.add(restrictionit.next()); 
            }
            for(Resource restriction : restrictionsToUpdate) {
                log.info("updating restriction");
                if(restriction.hasProperty(OWL.allValuesFrom)) {
                    log.info("updating allValuesFrom");
                    model.removeAll(restriction, OWL.allValuesFrom, null);
                    model.add(restriction, OWL.allValuesFrom, model.getResource(ROH + "Status"));
                } else if (restriction.hasProperty(OWL.someValuesFrom)) {
                    model.removeAll(restriction, OWL.someValuesFrom, null);
                    model.add(restriction, OWL.someValuesFrom, model.getResource(ROH + "Status"));
                }
            }
            // eliminate property's original range
            Model preserveNonRange = ModelFactory.createDefaultModel();
            preserveNonRange.add(model.getResource(ROH + statusProp), RDF.type, OWL.ObjectProperty);
            preserveNonRange.add(model.listStatements(model.getResource(ROH + statusProp), RDFS.label, (RDFNode) null));
            preserveNonRange.add(model.listStatements(model.getResource(ROH + statusProp), RDFS.domain, (RDFNode) null));
            preserveNonRange.add(model.listStatements(model.getResource(ROH + statusProp), RDFS.comment, (RDFNode) null));
            log.info("Editing " + statusProp);
            log.info(model.size());
            Model remove = getDescription(model, ROH + statusProp);
            log.info(remove.size());
            model.remove(remove);
            log.info(model.size());
            model.add(preserveNonRange);
            model.add(model.getResource(ROH + statusProp), RDFS.range, model.getResource(ROH + "Status"));
        }
        return model;
    }
    
    private static Model convertRoleIndividuals(Model model) {
        List<Resource> individualsToUpdate = new ArrayList<Resource>();
        ResIterator indIt = model.listResourcesWithProperty(
                RDF.type, model.getResource(ASIO + "Role"));
        while(indIt.hasNext()) {
            individualsToUpdate.add(indIt.next());
        }
        for(Resource ind : individualsToUpdate) {
            log.info("Converting " + ind.getURI() + " from individual to class");
            model.removeAll(ind, RDF.type, null);
            model.add(ind, RDF.type, OWL.Class);
        }
        return model;
    }
    
    private static String uri(String qnameOrUri, Map<String, String> prefixesMain, 
            Map<String, String> prefixesSec) {
        if(qnameOrUri.contains(",")) {
            qnameOrUri = qnameOrUri.split(",")[0].trim();
            log.info("Using first value " + qnameOrUri);
        }
        if(StringUtils.isEmpty(qnameOrUri) || !qnameOrUri.contains(":") 
                || qnameOrUri.startsWith("http")) {
            return qnameOrUri;
        } else {
            String[] qname = qnameOrUri.split(":");
            String ns = prefixesMain.get(qname[0]);
            if(ns == null && prefixesSec != null) {
                ns = prefixesSec.get(qname[0]);
            }
            if(ns == null) {
                throw new RuntimeException("Unknown prefix: " + qname[0]);
            }
            return ns + qname[1];
        }
    }
    
    private static Model getGeoNamesData() {
        Model geo = ModelFactory.createDefaultModel();
        List<String> geoIDs = Arrays.asList("2510769", "2264397", "2635167", "2593109", "3125609", "2513413");
        for(String geoID : geoIDs) {
            geo.add(geo.getResource("https://sws.geonames.org/" + geoID + "/"), 
                    RDF.type, geo.getResource(prefixes2.get("geonames") + "Feature"));
            geo.add(geo.getResource("https://sws.geonames.org/" + geoID + "/"), 
                    RDFS.label, geoID);
        }
        return geo;
    }
    
    private static Model removeTemporaryGeoLabels(Model model) {
        List<String> geoIDs = Arrays.asList("2510769", "2264397", "2635167", "2593109", "3125609", "2513413");
        for(String geoID : geoIDs) {
            model.removeAll(model.getResource("https://sws.geonames.org/" + geoID + "/"), 
                    RDFS.label, (RDFNode) null);
        }
        return model;
    }
    
    private static Model createSampleData(Model model) {
        OntModel ontology = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, model);
        String namespace = "http://example.org/individual/";
        Model sampleData = ModelFactory.createDefaultModel();
        sampleData.add(getGeoNamesData());
        StmtIterator sit = ontology.listStatements(null, RDF.type, OWL.Class);
        while(sit.hasNext()) {
            Statement stmt = sit.next();
            if(!stmt.getSubject().isAnon()) {
                OntClass clazz = ontology.getOntClass(stmt.getSubject().getURI());
                if(clazz.getURI().contains("Feature")) {
                    continue;
                }
                List<String[]> objProps = getAllObjectProperties(clazz, ontology);
                List<String[]> dataProps = getAllDatatypeProperties(clazz, ontology);
                // figure out how many individuals we need to represent the different possible values
                // If necessary, we can stash this work in a map so as not to repeat it later
                int individualsRequired = 1;
                for(String[] objProp : objProps) {                                       
                    Property property = sampleData.getProperty(objProp[0]);
                    OntClass range = ontology.getOntClass(getRangeURI(objProp));
                    if(range == null) {
                        log.warn("Null range class found for " + property.getURI());
                        continue;
                    }       
                    List<OntClass> subClasses = getAllNamedSubClasses(range, ontology);
                    if((subClasses.size() > individualsRequired) && individualsRequired < 5) {
                        individualsRequired = subClasses.size();
                    }
                }
                for(String[] dataProp : dataProps) {
                    if(!StringUtils.isEmpty(dataProp[5])) {                        
                        // enumerated values
                        String[] values = getEnumValues(dataProp);
                        if(values.length > individualsRequired) {
                            individualsRequired = values.length;    
                        }
                    }
                }
                List<Resource> sampleInds = new ArrayList<Resource>();
                for(int i = 0; i < individualsRequired; i++) {
                    Resource newInd = sampleData.getResource(namespace + 
                            "sample-" + clazz.getLocalName() + "-" + autoIncrement());
                    sampleInds.add(newInd);
                    sampleData.add(newInd, RDF.type, clazz);
                    sampleData.add(newInd, RDFS.label, newInd.getLocalName());
                }
                log.info("Created " + sampleInds.size() + " " + clazz.getURI());
                for(String[] objProp : objProps) {                                       
                    Property property = sampleData.getProperty(objProp[0]);
                    OntClass range = ontology.getOntClass(getRangeURI(objProp));
                    if(range == null) {
                        log.warn("Null range class found for " + property.getURI());
                        continue;
                    }       
                    List<OntClass> subClasses = getAllNamedSubClasses(range, ontology);                    
                    int subClassIndex = 0;
                    for(Resource clone : sampleInds) {
                        OntClass objectType = subClasses.get(subClassIndex);
                        if(subClassIndex == subClasses.size() - 1) {
                            subClassIndex = 0;
                        } else {
                            subClassIndex++;
                        }
                        log.debug("Adding " + property.getURI() + " range " 
                                + objectType.getURI() + " to individual " + clone.getURI());
                        Property inverse = getInverse(property, ontology);
                        Resource inverseInd = null;
                        if(inverse != null) {
                            StmtIterator invIt = sampleData.listStatements(null, inverse, clone);
                            while(invIt.hasNext()) {
                                Statement invStmt = invIt.next();
                                inverseInd = invStmt.getSubject();
                            }
                        }
                        if(inverseInd != null) {
                            sampleData.add(clone, property, inverseInd);
                        } else {
                            Resource objectInd = getRelatedIndividual(
                                    0, objectType, ontology, sampleData, namespace);
                            if(objectInd != null) {
                                sampleData.add(clone, property, objectInd);
                            }
                        }
                    }
                }
                
                for(String[] dataProp : dataProps) {
                    Property property = sampleData.getProperty(dataProp[0]);
                    if(!StringUtils.isEmpty(dataProp[5])) {                        
                        // enumerated values
                        String[] values = getEnumValues(dataProp);                       
                        int valueIndex = 0;                                                
                        for(Resource clone : sampleInds) {
                            String value = values[valueIndex];
                            if(valueIndex == values.length - 1) {
                                valueIndex = 0;
                            } else {
                                valueIndex++;
                            }
                            sampleData.add(clone, property, value);
                        }
                    } else {
                        String rangeDatatypeURI = getRangeURI(dataProp);
                        for(Resource res : sampleInds) {
                            createDatatypeValue(res, property, rangeDatatypeURI, sampleData);
                        } 
                    }
                 }
            }
        }
        sampleData = fleshOutStubs(sampleData, ontology, namespace);
        sampleData = createAuthorLists(sampleData);
        sampleData = addDateTimePrecision(sampleData);
        sampleData = fixRelates(sampleData);
        sampleData = fixHasRole(sampleData);
        sampleData = replaceTemporaryClass(prefixes2.get("roh") + "JournalMetric", prefixes2.get("roh") + "PublicationMetric", sampleData);
        sampleData = removeTemporaryGeoLabels(sampleData);
        return sampleData;
    }
    
    private static Model replaceTemporaryClass(String fromClassURI, String toClassURI, Model model) {
        Model additions = ModelFactory.createDefaultModel();
        Model removals = ModelFactory.createDefaultModel();
        StmtIterator sit = model.listStatements(null, RDF.type, model.getResource(fromClassURI));
        while(sit.hasNext()) {
            Statement stmt = sit.next();
            removals.add(stmt);
            additions.add(stmt.getSubject(), RDF.type, model.getResource(toClassURI));
        }
        model.remove(removals);
        model.add(additions);
        return model;
    }
    
    private static Model fixHasRole(Model model) {
        Model additions = ModelFactory.createDefaultModel();
        Model removals = ModelFactory.createDefaultModel();
        StmtIterator sit = model.listStatements(null, model.getProperty(prefixes2.get("roh") + "roleOf"), (RDFNode) null);
        while(sit.hasNext()) {
            Statement stmt = sit.next();
            if(!stmt.getObject().isResource()) {
                continue;
            }
            Resource bearer = stmt.getObject().asResource();
            additions.add(bearer, model.getProperty("hasRole"), stmt.getSubject());
            additions.add(bearer, model.getProperty(prefixes2.get("ro") + "RO_0000053"), stmt.getSubject());
            additions.add(stmt.getSubject(), model.getProperty(prefixes2.get("ro") + "RO_0000052"), bearer);
            removals.add(model.listStatements(null, model.getProperty("hasRole"), stmt.getSubject()));
            removals.add(model.listStatements(null, model.getProperty(prefixes2.get("ro") + "RO_0000053"), stmt.getSubject()));
            removals.add(model.listStatements(stmt.getSubject(), model.getProperty(prefixes2.get("ro") + "RO_0000052"), (RDFNode) null));
        }
        model.remove(removals);
        model.add(additions);
        return model;
    }
    
    private static Model fixRelates(Model model) {
        Model additions = ModelFactory.createDefaultModel();
        Model removals = ModelFactory.createDefaultModel();
        List<String> fakePredicates = Arrays.asList(prefixes2.get("vivo") + "relates1", prefixes2.get("vivo") + "relates2", prefixes2.get("vivo") + "relates3");
        for(String fakePredicate : fakePredicates) {
            StmtIterator sit = model.listStatements(null, model.getProperty(fakePredicate), (RDFNode) null);
            while(sit.hasNext()) {
                Statement stmt = sit.next();
                removals.add(stmt);
                additions.add(stmt.getSubject(), model.getProperty(prefixes2.get("vivo") + "relates"), stmt.getObject());
            }
        }
        model.remove(removals);
        model.add(additions);
        return model;
    }
    
    private static String[] getEnumValues(String[] dataProp) {
        return dataProp[5].replaceAll("\\{", "")
                .replaceAll("\\}", "").replaceAll("\\\"",  "")
                .replaceAll(" ", "").split(","); 
    }
    
    private static void createDatatypeValue(Resource res, Property property, 
            String rangeDatatypeURI, Model sampleData) {
        if(rangeDatatypeURI == null || RDFS.Literal.getURI().equals(rangeDatatypeURI)) {
            log.warn("Null rangeDatatypeURI for" + property.getURI());
            sampleData.add(res, property, "plain literal " + random.nextInt(9999));
        } else if(rangeDatatypeURI.equals(XSD.integer.getURI()) || rangeDatatypeURI.equals(XSD.xint.getURI())) {
            sampleData.add(res, property, sampleData.createTypedLiteral(random.nextInt(9999)));
        } else if(rangeDatatypeURI.equals(XSD.xdouble.getURI())) {
            sampleData.add(res, property, sampleData.createTypedLiteral(random.nextDouble() * 200, XSDDatatype.XSDdouble));
        } else if(rangeDatatypeURI.equals(XSD.xfloat.getURI())) {
            sampleData.add(res, property, sampleData.createTypedLiteral(random.nextFloat() * 200, XSDDatatype.XSDfloat));
        } else if(rangeDatatypeURI.equals(XSD.decimal.getURI())) {
            sampleData.add(res, property, sampleData.createTypedLiteral(random.nextInt(9999999) + "." + random.nextInt(10) + "0", XSDDatatype.XSDdecimal));
        } else if(rangeDatatypeURI.equals(XSD.xstring.getURI())) {
            sampleData.add(res, property, sampleData.createTypedLiteral("a string " + random.nextInt(99999)));
        } else if(rangeDatatypeURI.equals(XSD.date.getURI())) {
            sampleData.add(res, property, sampleData.createTypedLiteral(1990 + random.nextInt(30) + "-01-01", XSDDatatype.XSDdate));
        } else if(rangeDatatypeURI.equals(XSD.dateTime.getURI())) {
            sampleData.add(res, property, sampleData.createTypedLiteral(1990 + random.nextInt(30) + "-01-01T00:00:00Z", XSDDatatype.XSDdateTime));
        } else {
            log.warn("Unsupported datatype URI " + rangeDatatypeURI);
        }
    }
    
    private static Model fleshOutStubs(Model model, OntModel ontology, 
            String namespace) {
        List<Resource> stubs = new ArrayList<Resource>();
        StmtIterator sit = model.listStatements(null, RDFS.label, "stub");
        while(sit.hasNext()) {
            Statement stmt = sit.next();
            stubs.add(stmt.getSubject());
        }
        int count = 0;
        for(Resource stub : stubs) {
            count++;
            log.info("Processing stub " + count + " out of " + stubs.size());
            OntClass clazz = null;
            StmtIterator typeIt = stub.listProperties(RDF.type);
            while(typeIt.hasNext()) {
                Statement typeStmt = typeIt.next();
                clazz = ontology.getOntClass(typeStmt.getObject().asResource().getURI());
            }
            if(isDependentClass(clazz, ontology)) {
                createRelatedIndividual(2, clazz, ontology, model, namespace, stub);
            } else {
                if(countResources(clazz.getURI(), model, !INCLUDE_STUBS) > 3) {
                    Resource reused = getRandomResource(clazz.getURI(), model, false);
                    List<Statement> additions = new ArrayList<Statement>();
                    StmtIterator incomingIt = model.listStatements(null, null, stub);
                    while(incomingIt.hasNext()) {
                        Statement stmt = incomingIt.next();
                        additions.add(ResourceFactory.createStatement(stmt.getSubject(), stmt.getPredicate(), reused));
                    }
                    model.add(additions);
                    model.removeAll(stub, null, (RDFNode) null);
                    model.removeAll(null, null, stub);
                    // try to reuse
                } else {
                    createRelatedIndividual(2, clazz, ontology, model, namespace, stub);
                }                 
            }
        }
        return model;
    }    
    
    private static Model addDateTimePrecision(Model sampleData) {
        Model additions = ModelFactory.createDefaultModel();
        StmtIterator sit = sampleData.listStatements(null, RDF.type, 
                sampleData.getResource(prefixes2.get("vivo") + "DateTimeValue"));
        while(sit.hasNext()) {
            Statement stmt = sit.next();
            additions.add(stmt.getSubject(), additions.getProperty(
                    prefixes2.get("vivo") + "dateTimePrecision"), 
                    additions.getResource(prefixes2.get("vivo") + "yearPrecision")); 
        }
        sampleData.add(additions);
        return sampleData;
    }
    
    private static Resource getRelatedIndividual(int depth, OntClass clazz, OntModel ontology, Model sampleData, String namespace) {
        // If clazz is not a subclass of Role, Relationship, or DateTimeValue,
        // try to reuse an existing individual if enough have already been made.      
        log.debug("Trying to reuse a " + clazz.getURI());
        int resCount = countResources(clazz.getURI(), sampleData, INCLUDE_STUBS);
        boolean isDependent = isDependentClass(clazz, ontology);
        log.debug("resCount is " + resCount + " and isDependent is " + isDependent);
        if(!isDependent && ( resCount > 3) ) {
            Resource randomRes = getRandomResource(clazz.getURI(), sampleData, INCLUDE_STUBS);
            sampleData.add(randomRes, RDFS.comment, "reused");         
            log.debug("reused one");
            return randomRes;
        } else if (depth > 0) {
            return createRelatedIndividual(depth - 1, clazz, ontology, sampleData, namespace);
        } else {
            Resource newInd = sampleData.getResource(namespace + "sample-" + clazz.getLocalName() + "-" + autoIncrement());
            sampleData.add(newInd, RDF.type, clazz);
            sampleData.add(newInd, RDFS.label, "stub");
            return newInd;
        }
    }
    
    private static boolean isDependentClass(OntClass clazz, OntModel ontology) {
        return isSubClassOf(clazz, prefixes2.get("vivo") + "Relationship", ontology) 
               /* || isSubClassOf(clazz, prefixes2.get("vivo") + "DateTimeInterval", ontology) */
               /* || isSubClassOf(clazz, prefixes2.get("vivo") + "DateTimeValue", ontology) */
                || isSubClassOf(clazz, prefixes2.get("bfo") + "BFO_0000023", ontology)
                || isSubClassOf(clazz, prefixes2.get("roh") + "Metric", ontology);
    }
    
    private static Resource createRelatedIndividual(int depth, OntClass clazz, OntModel ontology, 
            Model sampleData, String namespace) {
        return createRelatedIndividual(depth, clazz, ontology, sampleData, namespace, null);
    }
    
    private static Property getInverse(Property property, Model ontology) {
        Property inverse = null;
        StmtIterator sit = ontology.listStatements(ontology.getResource(property.getURI()), OWL.inverseOf, (RDFNode) null);
        while(sit.hasNext()) {
            Statement stmt = sit.next();
            if(stmt.getObject().isURIResource()) {
                inverse = ontology.getProperty(stmt.getObject().asResource().getURI());
            }
        }
        if(inverse == null) {
            sit = ontology.listStatements(null, OWL.inverseOf, ontology.getResource(property.getURI()));
            while(sit.hasNext()) {
                Statement stmt = sit.next();
                if(stmt.getSubject().isURIResource()) {
                    inverse = ontology.getProperty(stmt.getSubject().asResource().getURI());
                }
            }
        }
        return inverse;
    }
    
    private static Resource createRelatedIndividual(int depth, OntClass clazz, OntModel ontology, 
            Model sampleData, String namespace, Resource stub) {
        Resource newInd = null;
        if(stub != null) {
            newInd = stub;
            sampleData.remove(sampleData.listStatements(stub, RDFS.label, (RDFNode) null));
        } else {
            newInd = sampleData.getResource(namespace + "sample-" + clazz.getLocalName() + "-" + autoIncrement());    
        }        
        sampleData.add(newInd, RDF.type, clazz);
        sampleData.add(newInd, RDFS.label, newInd.getLocalName());
        List<String[]> objProps = getAllObjectProperties(clazz, ontology);
        for(String[] objProp : objProps) {                                       
            Property property = sampleData.getProperty(objProp[0]);
            Property inverse = getInverse(property, ontology);
            Resource inverseInd = null;
            if(inverse != null) {
                StmtIterator sit = sampleData.listStatements(null, inverse, newInd);
                while(sit.hasNext()) {
                    Statement stmt = sit.next();
                    inverseInd = stmt.getSubject();
                }
            }
            if(inverseInd != null) {
                sampleData.add(newInd, property, inverseInd);
            } else {
                OntClass range = ontology.getOntClass(getRangeURI(objProp));
                if( (prefixes2.get("bfo") + "BFO_0000054").equals(property.getURI()) ) {
                    int rand = random.nextInt(1);
                    if(rand == 0) {
                        range = ontology.getOntClass(prefixes2.get("vivo") + "Project");
                    }
                }
                if(range == null) {
                    log.warn("Null range class found for " + property.getURI());
                    continue;
                }
                OntClass objectType = getRandomSubClass(range, ontology);
                log.debug("Adding " + property.getURI() + " range " + objectType.getURI() + " to individual " + newInd.getURI());
                Resource objectInd = getRelatedIndividual(depth, objectType, ontology, sampleData, namespace);
                if(objectInd != null) {
                    sampleData.add(newInd, property, objectInd);
                }
            }
        }
        List<String[]> dataProps = getAllDatatypeProperties(clazz, ontology);
        for(String[] dataProp : dataProps) {
            Property property = sampleData.getProperty(dataProp[0]);
            if(!StringUtils.isEmpty(dataProp[5])) {                        
                // enumerated values
                String[] values = getEnumValues(dataProp);
                sampleData.add(newInd, property, values[random.nextInt(values.length)]);
            } else {
                String rangeDatatypeURI = getRangeURI(dataProp);
                createDatatypeValue(newInd, property, rangeDatatypeURI, sampleData);
            }
        }
        return newInd;
    }
    
    private static Model createAuthorLists(Model model) {
        Property authorList = model.getProperty(prefixes2.get("bibo") + "authorList");
        String foafPersonURI = prefixes2.get("foaf") + "Person";
        int personCount = countResources(foafPersonURI, model, INCLUDE_STUBS);
        log.info(personCount + " " + foafPersonURI + " objects have been created");
        List<Resource> resourcesWithAuthorLists = new ArrayList<Resource>();
        Model toRemove = ModelFactory.createDefaultModel();
        StmtIterator sit = model.listStatements(null, authorList, (RDFNode) null);
        while(sit.hasNext()) {
            Statement stmt = sit.next();
            resourcesWithAuthorLists.add(stmt.getSubject());
            // remove existing Seq; we'll just make a new one
            if(stmt.getObject().isResource()) {
                toRemove.add(stmt);
                toRemove.add(model.listStatements(stmt.getObject().asResource(), null, (RDFNode) null));
            }
        }
        model.remove(toRemove);
        for(Resource doc : resourcesWithAuthorLists) {
            Seq authorSeq = model.createSeq();
            int numAuthors = random.nextInt(Math.min(10, personCount)) + 1;
            for(int i = 0; i < numAuthors; i++) {
                authorSeq.add(getRandomResource(foafPersonURI, model, INCLUDE_STUBS));    
            }
            model.add(doc, authorList, authorSeq);
        }
        return model;
    }
    
    private static boolean isSubClassOf(OntClass subclass, String superClassURI, OntModel ontology) {
        List<Resource> parents = getAllNamedSuperClasses(subclass, ontology);
        for(Resource parent : parents) {
            if(parent.getURI().equals(superClassURI)) {
                return true;
            }
        }
        return false;
    }
    
    private static List<Resource> getAllNamedSuperClasses(OntClass clazz, Model model) {
        List<Resource> superClasses = new ArrayList<Resource>();
        String query = "SELECT ?y WHERE { <" + clazz.getURI() + "> <" + RDFS.subClassOf.getURI() + ">* ?y }";
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        try {
            ResultSet rs = qe.execSelect();
            while(rs.hasNext()) {
                QuerySolution qsoln = rs.next();
                if(qsoln.get("y").isURIResource()) {
                    superClasses.add(qsoln.get("y").asResource());
                }
            }
        } catch (QueryParseException e) {
            log.error("Unable to parse " + query);
            throw e;
        } finally {
            if(qe != null) {
                qe.close();
            }
        }
        superClasses.add(clazz);
        return superClasses;
    }
    
    private static List<OntClass> getAllNamedSubClasses(OntClass clazz, OntModel model) {
        List<OntClass> subClasses = new ArrayList<OntClass>();
        String query = "SELECT ?y WHERE { ?y <" + RDFS.subClassOf.getURI() + ">* <" + clazz.getURI() + "> }";
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        try {
            ResultSet rs = qe.execSelect();
            while(rs.hasNext()) {
                QuerySolution qsoln = rs.next();
                if(qsoln.get("y").isURIResource()) {
                    subClasses.add(model.getOntClass(qsoln.get("y").asResource().getURI()));
                }
            }
        } catch (QueryParseException e) {
            log.error("Unable to parse " + query);
            throw e;
        } finally {
            if(qe != null) {
                qe.close();
            }
        }
        subClasses.add(clazz);
        return subClasses;
    }
    
    private static OntClass getRandomSubClass(OntClass clazz, OntModel model) {
        List<OntClass> allSubs = getAllNamedSubClasses(clazz, model);
        return allSubs.get(random.nextInt(allSubs.size()));
    }
    
    private static List<String[]> getAllObjectProperties(OntClass clazz, Model model) {
        List<String[]> allProps = new ArrayList<String[]>();
        allProps.addAll(getObjectProperties(clazz.getURI(), model));
        log.debug("Found " + allProps.size() + " object properties for " + clazz.getURI());
        for(Resource superClass : getAllNamedSuperClasses(clazz, model)) {     
            if(!superClass.isAnon()) {
                mergeProperties(getObjectProperties(superClass.getURI(), model), allProps);
            }
            log.debug("Found " + allProps.size() + " including parent " + superClass.getURI());
        }
        return allProps;
    }
    
    private static List<String[]> getAllDatatypeProperties(OntClass clazz, Model model) {
        List<String[]> allProps = new ArrayList<String[]>();
        allProps.addAll(getDatatypeProperties(clazz.getURI(), model));
        for(Resource superClass : getAllNamedSuperClasses(clazz, model)) {
            if(!superClass.isAnon()) {
                mergeProperties(getDatatypeProperties(superClass.getURI(), model), allProps);
            }
        }
        return allProps;
    }
    
    private static void mergeProperties(List<String[]> newProps, List<String[]> existingProps) {
        for(String[] newProp : newProps) {
            boolean duplicate = false;
            for(String[] existingProp : existingProps) {
                if(existingProp[0].equals(newProp[0])) {
                    duplicate = true;
                    break;
                }
            }
            if(!duplicate) {
                existingProps.add(newProp);
            }
        }
    }
    
    private static int nextInt = 0;
    
    private static int autoIncrement() {
        nextInt++;
        return nextInt;
    }
    
    private static int countResources(String classURI, Model model, boolean includeStubs) {
        int count = 0;
        if(classURI == null || model == null) {
            return count;
        }
        StmtIterator sit = model.listStatements(null, RDF.type, model.getResource(classURI));
        while(sit.hasNext()) {
            Statement stmt = sit.next();
            if(model.contains(stmt.getSubject(), RDFS.label, (RDFNode) null)) {
                if(includeStubs || !model.contains(stmt.getSubject(), RDFS.label, "stub")) {
                    count++;
                }
            }
        }
        return count;
    }
    
    private static Resource getRandomResource(String classURI, Model model, boolean includeStubs) {
        int indexDesired = random.nextInt(countResources(classURI, model, includeStubs));
        StmtIterator sit = model.listStatements(null, RDF.type, model.getResource(classURI));
        int i = 0;
        Resource randomResource = null;
        while(sit.hasNext()) {
            Statement stmt = sit.next();
            if(!model.contains(stmt.getSubject(), RDFS.label, (RDFNode) null)) {
                continue;    
            }
            if(!includeStubs && model.contains(stmt.getSubject(), RDFS.label, "stub")) {
                continue;
            }
            randomResource = stmt.getSubject();
            if(i == indexDesired || !sit.hasNext()) {
                break;
            }
            i++;
        }
        return randomResource;
    }
    
//    private static List<Resource> extendIndividualList(List<Resource> inds, int toAdd, Model model) {
//        Model cloneStmts = ModelFactory.createDefaultModel();
//        for(int i = 0; i < toAdd; i++) {
//            Resource ind = inds.get(random.nextInt(inds.size()));
//            String baseURI = ind.getURI().substring(0, ind.getURI().lastIndexOf("-"));
//            String cloneURI = baseURI + "-" + autoIncrement();
//            Resource clone = cloneStmts.getResource(cloneURI);
//            inds.add(clone);
//            StmtIterator stmtIt = model.listStatements(ind, null, (RDFNode) null);
//            while(stmtIt.hasNext()) {
//                Statement stmt = stmtIt.next();
//                if(RDFS.label.equals(stmt.getPredicate())) {
//                    cloneStmts.add(clone, RDFS.label, clone.getLocalName());
//                } else if(stmt.getObject().isLiteral()) {
//                    cloneStmts.add(clone, stmt.getPredicate(), stmt.getObject());
//                } else if(stmt.getObject().equals(ind)) {
//                    cloneStmts.add(clone, stmt.getPredicate(), clone);
//                } else if(RDF.type.equals(stmt.getPredicate())) {
//                    cloneStmts.add(clone, RDF.type, stmt.getObject());
//                }  else if(stmt.getObject().isURIResource()){
//                    String objURI = stmt.getObject().asResource().getURI();
//                    int endOfBaseURI = objURI.lastIndexOf("-");
//                    String objBaseURI = null;
//                    if(endOfBaseURI < 0) {
//                        log.warn("No hyphen found in objURI " + objURI);
//                        objBaseURI = objURI;
//                    } else {
//                        objBaseURI = objURI.substring(0, endOfBaseURI);
//                    }                    
//                    Resource newObj = cloneStmts.getResource(objBaseURI + "-" + autoIncrement());
//                    cloneStmts.add(clone, stmt.getPredicate(), newObj);
//                    StmtIterator objSit = model.listStatements(model.getResource(objURI), null, (RDFNode) null);
//                    while(objSit.hasNext()) {
//                        Statement objStmt = objSit.next();
//                        cloneStmts.add(newObj, objStmt.getPredicate(), objStmt.getObject());
//                    }
//                }
//            }
//            
//        }
//        model.add(cloneStmts);
//        return inds;
//    }
    
    private static String getRangeURI(String[] prop) {
        String rangePrefix = prop[3];
        if(StringUtils.isEmpty(rangePrefix)) {
           return prop[4];
        } else {
            String rangeClassNamespace = prefixes2.get(rangePrefix);
            if(StringUtils.isEmpty(rangeClassNamespace)) {
                throw new RuntimeException("No namespace found for prefix " + rangePrefix);
            } else {
                return rangeClassNamespace + prop[4];
            }
        }
    }
    
    private static XSSFWorkbook createSpreadsheet(OntModel ontology) {
        XSSFWorkbook wb = new XSSFWorkbook();
        Map<String, CellStyle> styles = makePrefixStyles(wb);
        XSSFSheet sheet = wb.createSheet("ontology");
        PropertyTemplate pt = new PropertyTemplate();
        RowCreator rowCreator = new RowCreator(sheet);
        int classLevel = 0;
        int classMax = 6;
        for(Resource clazz : getRootClasses(ontology)) {
                addClassToSpreadsheet(clazz, sheet, rowCreator, classLevel, 
                        classMax, styles, ontology);      
        }
        sheet.setColumnWidth(0, 2000);
        sheet.setColumnWidth(1, 2000);
        sheet.setColumnWidth(2, 2000);
        sheet.setColumnWidth(3, 2000);
        sheet.setColumnWidth(4, 2000);
        sheet.setColumnWidth(5, 2000);
        sheet.setColumnWidth(6, 2000);
        sheet.setColumnWidth(7, 8000);
        sheet.setColumnWidth(8, 8000);
        sheet.setColumnWidth(9, 2000);
        sheet.setColumnWidth(10, 8000);
        sheet.setColumnWidth(11, 8000);
        sheet.setColumnWidth(12, 8000);
        return wb;
    }
    
    private static List<Resource> getRootClasses(Model ontology) {
        List<Resource> rootClasses = new ArrayList<Resource>();
        StmtIterator sit = ontology.listStatements(null, RDF.type, OWL.Class);
        while(sit.hasNext()) {
            Statement stmt = sit.next();            
            if(stmt.getSubject().isAnon()) {
                continue;
            }
            boolean hasNamedParents = false;
            StmtIterator parentIt = ontology.listStatements(stmt.getSubject(), RDFS.subClassOf, (Resource) null);
            while(parentIt.hasNext()) {
                Statement parentStmt = parentIt.next();
                if(parentStmt.getObject().isURIResource()) {
                    hasNamedParents = true;
                    break;
                }
            }
            if(!hasNamedParents) {
                rootClasses.add(stmt.getSubject());
            }
        }
        Collections.sort(rootClasses, new LocalNameSorter());
        return rootClasses;
    }
    
    private static class LocalNameSorter implements Comparator<Resource> {

        @Override
        public int compare(Resource arg0, Resource arg1) {
            if(arg0.getLocalName() == null && arg1.getLocalName() == null) {
                return 0;
            } else if(arg0.getLocalName() == null) {
                return -1;
            } else if(arg1.getLocalName() == null) {
                return 1;
            } else {
                return arg0.getLocalName().compareTo(arg1.getLocalName());
            }
        }      
        
    }
    
    private static List<Resource> getSubClasses(Resource superClass, OntModel ontology) {
        List<Resource> subClasses = new ArrayList<Resource>();
        try {
            OntClass ontSuperClass = ontology.getOntClass(superClass.getURI());
            Iterator<OntClass> subs = ontSuperClass.listSubClasses();
            while(subs.hasNext()) {
                OntClass sub = subs.next();
                if(!sub.isAnon()) {
                    subClasses.add(sub);
                }
            }
        } catch (Exception e) {
            log.error(e, e);
        }
        Collections.sort(subClasses, new LocalNameSorter());
        return subClasses;
    }
    
    private static void addClassToSpreadsheet(Resource clazz, XSSFSheet sheet, 
            RowCreator rowCreator, int classLevel, int classMax, 
            Map<String, CellStyle> styles, OntModel ontology) {
        log.info("Adding " + clazz.getURI() + " at level " + classLevel);
        XSSFRow row = rowCreator.createRow();
        XSSFCell cell0 = row.createCell(classLevel + 0);
        XSSFCell cell1 = row.createCell(classLevel + 1);                
        String prefix = prefixes2reverse.get(clazz.getNameSpace());                
        CellStyle style = styles.get(prefix);
        if(style == null) {
            log.warn("No cell style found for prefix " + prefix);
            style = styles.get("default");
        }
        cell0.setCellStyle(style);
        cell1.setCellStyle(style);
        for(int i = classLevel + 1; i < classMax; i++) {
            XSSFCell fillerCell = row.createCell(i);
            fillerCell.setCellStyle(style);
        }
        if(classLevel  + 1 < classMax - 1) {
        sheet.addMergedRegion(new CellRangeAddress(
                rowCreator.rowIndex, rowCreator.rowIndex, classLevel + 1, classMax - 1));
        }
        if(prefix == null) {
            log.warn("No prefix found for namespace " + clazz.getNameSpace());
            cell1.setCellValue(clazz.getURI());
        } else {
            cell0.setCellValue(prefix);
            cell1.setCellValue(clazz.getLocalName());                    
        }
        Iterator<String[]> objProperties = getObjectProperties(clazz.getURI(), ontology).iterator();
        Iterator<String[]> datatypeProperties = getDatatypeProperties(clazz.getURI(), ontology).iterator();
        boolean newRow = false;
        while(objProperties.hasNext() || datatypeProperties.hasNext()) {
            boolean objPropPrinted = false;
            if(objProperties.hasNext()) {
                objPropPrinted = true;
                if(newRow) {
                    for(int i = 0; i < 6; i++) {
                        XSSFCell fillerCell = row.createCell(i);
                        fillerCell.setCellStyle(style); // class style
                    }
                    sheet.addMergedRegion(new CellRangeAddress(
                            rowCreator.rowIndex, rowCreator.rowIndex, 0, 5));
                }
                String[] prop = objProperties.next();
                String propPrefix = prop[1];
                XSSFCell prefixCell = row.createCell(6);
                prefixCell.setCellValue(prop[1]);
                XSSFCell localNameCell = row.createCell(7);
                localNameCell.setCellValue(prop[2]);
                CellStyle propCellStyle = "range".equals(prop[6]) 
                        ? styles.get(propPrefix + "bold") : styles.get(propPrefix);
                if(propCellStyle == null) {
                    propCellStyle = "range".equals(prop[6]) 
                            ? styles.get("defaultbold") : styles.get("default");
                }
                prefixCell.setCellStyle(propCellStyle);
                localNameCell.setCellStyle(propCellStyle);
                String rangePrefix = prop[3];
                if(!StringUtils.isEmpty(rangePrefix)) {
                    XSSFCell rangeCell = row.createCell(8);
                    rangeCell.setCellValue(rangePrefix + ":" + prop[4]);
                    CellStyle rangeCellStyle = styles.get(rangePrefix);
                    if(rangeCellStyle == null) {
                        styles.get("default");
                    }                            
                    rangeCell.setCellStyle(rangeCellStyle);
                } else {
                    XSSFCell rangeCell = row.createCell(8);
                    rangeCell.setCellValue(prop[4]);
                    CellStyle rangeCellStyle = styles.get(propPrefix);
                    if(rangeCellStyle == null) {
                        styles.get("default");
                    }
                    rangeCell.setCellStyle(rangeCellStyle); 
                }
            }
            if(datatypeProperties.hasNext()) {
                if(newRow  && !objPropPrinted) {
                    for(int i = 0; i < 9; i++) {
                        XSSFCell fillerCell = row.createCell(i);
                        fillerCell.setCellStyle(style); // class style
                    }
                    sheet.addMergedRegion(new CellRangeAddress(
                            rowCreator.rowIndex, rowCreator.rowIndex, 0, 8));
                }
                String[] prop = datatypeProperties.next();
                String propPrefix = prop[1];
                XSSFCell prefixCell = row.createCell(9);
                prefixCell.setCellValue(prop[1]);
                XSSFCell localNameCell = row.createCell(10);
                localNameCell.setCellValue(prop[2]);
                CellStyle propCellStyle = "range".equals(prop[6]) 
                        ? styles.get(propPrefix + "bold") : styles.get(propPrefix);
                if(propCellStyle == null) {
                    propCellStyle = "range".equals(prop[6]) 
                            ? styles.get("defaultbold") : styles.get("default");
                }
                prefixCell.setCellStyle(propCellStyle);
                localNameCell.setCellStyle(propCellStyle);
                String rangePrefix = prop[3];
                CellStyle rangeCellStyle = null;
                if(!StringUtils.isEmpty(rangePrefix)) {
                    XSSFCell rangeCell = row.createCell(11);
                    rangeCell.setCellValue(rangePrefix + ":" + prop[4]);
                    rangeCellStyle = styles.get(rangePrefix);
                    if(rangeCellStyle == null) {
                        rangeCellStyle = styles.get("default");    
                    }
                    rangeCell.setCellStyle(rangeCellStyle);
                } else {
                    XSSFCell rangeCell = row.createCell(11);
                    rangeCell.setCellValue(prop[4]);
                    rangeCellStyle = styles.get(propPrefix);
                    if(rangeCellStyle == null) {
                        
                    }
                    rangeCell.setCellStyle(rangeCellStyle);                             
                }
                if(!StringUtils.isEmpty(prop[5])) {
                    XSSFCell valuesCell = row.createCell(12);
                    valuesCell.setCellValue(prop[5]);
                    if(rangeCellStyle == null) {
                        rangeCellStyle = styles.get("default");
                    }
                    valuesCell.setCellStyle(rangeCellStyle);
                }
            }
            if(objProperties.hasNext() || datatypeProperties.hasNext()) {
                row = rowCreator.createRow();
                newRow = true;
            }
        }
        for(Resource subClass : getSubClasses(clazz, ontology)) {
            addClassToSpreadsheet(subClass, sheet, rowCreator, Math.min(
                    classLevel + 1, classMax - 2), classMax, styles, ontology);  
        }
    }
    
    private static String[] prefixAndLocalName(Resource res) {
        String[] pln = new String[2];
        String prefix = prefixes2reverse.get(res.getNameSpace());
        if(prefix == null) {
            log.warn("No prefix found for namespace " + res.getNameSpace());
            pln[0] = "";
            pln[1] = res.getURI();
        } else {
            pln[0] = prefix;
            pln[1] = res.getLocalName();
        }
        return pln;
    }
    
    private static List<String[]> getObjectProperties(String classURI, Model m) {
        return getProperties(true, classURI, m);
    }
    
    private static List<String[]> getDatatypeProperties(String classURI, Model m) {
        return getProperties(false, classURI, m);
    }
    
    private static class PropertySorter implements Comparator<String[]> {

        @Override
        public int compare(String[] arg0, String[] arg1) {
            return arg0[2].compareTo(arg1[2]);
        }
        
    }
    
    private static List<String[]> getProperties(boolean objectProperties, 
            String classURI, Model m) {
        Map<String, String[]> propertyMap = new HashMap<String, String[]>();
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, m);
        OntResource clazz = ontModel.getOntResource(classURI);
        if(!clazz.canAs(OntClass.class)) {
            log.warn(classURI + " cannot as OntClass. Skipping.");
            return Collections.emptyList();
        }
        OntClass ontClazz = clazz.as(OntClass.class);
        // domains
        if(objectProperties) {
            ExtendedIterator<ObjectProperty> ontProps = ontModel.listObjectProperties();
            while(ontProps.hasNext()) {
                OntProperty ontProp = ontProps.next();
                checkPropertyDomain(ontProp, ontClazz, propertyMap);
            }
        } else {
            ExtendedIterator<DatatypeProperty> dataProps = ontModel.listDatatypeProperties();
            while(dataProps.hasNext()) {
                OntProperty dataProp = dataProps.next();
                checkPropertyDomain(dataProp, ontClazz, propertyMap);
            }
        }
        // restrictions 
        ExtendedIterator<? extends OntClass> superIt = ontClazz.listSuperClasses();
        while(superIt.hasNext()) {
            OntClass superClazz = superIt.next();
            if(superClazz.isRestriction()) {
                Restriction rest = superClazz.asRestriction();
                if(rest.getOnProperty() == null) {
                    log.warn("restriction with null onProperty");
                    continue;
                }                    
                if( (objectProperties && rest.getOnProperty().isObjectProperty()) 
                        || (!objectProperties && rest.getOnProperty().isDatatypeProperty()) ) {
                    String[] fmt = formatRestriction(rest);
                    if(fmt != null) {
                        log.debug("Adding restriction to map " + fmt[0]);
                        String[] existing = propertyMap.get(fmt[0]); 
                        if(existing == null || !("someValuesFrom".equals(fmt[6]))) {
                            if(existing != null && !StringUtils.isEmpty(existing[5])) {
                                existing[3] = fmt[3];
                                existing[4] = fmt[4];
                                propertyMap.put(fmt[0], existing);
                            } else {
                                propertyMap.put(fmt[0], fmt);
                            }
                        }
                    }
                }
            } else if(superClazz.isIntersectionClass()) {
                IntersectionClass superIntersection = superClazz.asIntersectionClass();
                Iterator<? extends OntClass> operandIt = superIntersection.listOperands();
                // TODO refactor this
                while(operandIt.hasNext()) {
                    OntClass operand = operandIt.next();
                    if(operand.isRestriction()) {
                        Restriction rest = operand.asRestriction();
                        if(rest.getOnProperty() == null) {
                            log.warn("restriction with null onProperty");
                            continue;
                        }
                        if( (objectProperties && rest.getOnProperty().isObjectProperty()) 
                                || (!objectProperties && rest.getOnProperty().isDatatypeProperty()) ) {
                            String fmt[] = formatRestriction(rest);
                            String[] existing = propertyMap.get(fmt[0]); 
                            if(existing == null || !("someValuesFrom".equals(fmt[6]))) {
                                log.debug("Adding restriction to map " + fmt[0]);
                                if(existing != null && !StringUtils.isEmpty(existing[5])) {
                                    log.debug("updating existing");
                                    existing[3] = fmt[3];
                                    existing[4] = fmt[4];
                                    propertyMap.put(fmt[0], existing);
                                } else {
                                    log.debug("overwriting/adding");
                                    propertyMap.put(fmt[0], fmt);
                                }
                            }
                        }                        
                    }
                }
            }
        }
        List<String[]> properties = new ArrayList<String[]>();
        properties.addAll(propertyMap.values());
        Collections.sort(properties, new PropertySorter());
        return properties;
    }
    
    private static void checkPropertyDomain(OntProperty ontProp, OntClass ontClazz, Map<String, String[]> propertyMap) {
        log.debug("Checking domains for " + ontProp.getURI());
        ExtendedIterator<? extends OntResource> domains = ontProp.listDomain();
        while(domains.hasNext()) {
            OntResource domain = domains.next();
            if(domain.getURI() != null) {
                log.debug("Named domain class " + domain.getURI());
            }
            if(domain.equals(ontClazz)) {
                log.debug("Adding simple domain");
                String[] fmt = formatPropertyAndRange(ontProp, ontProp.getRange());
                if(fmt != null) {
                    fmt[6] = "range";
                    propertyMap.put(fmt[0], fmt);
                }
            } else if(domain.canAs(OntClass.class)) {
                log.debug("Checking complex domain");
                OntClass domainClazz = domain.as(OntClass.class);
                if(domainClazz.isUnionClass()) {
                    UnionClass domainUnion = domainClazz.asUnionClass();
                    Iterator<? extends OntClass> operandIt = domainUnion.listOperands();
                    while(operandIt.hasNext()) {
                        if(ontClazz.equals(operandIt.next())) {
                            String[] fmt = formatPropertyAndRange(ontProp, ontProp.getRange());
                            if(fmt != null) {
                                fmt[6] = "range";
                                propertyMap.put(fmt[0], fmt);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private static String[] formatRestriction(Restriction rest) {
        if(rest.isAllValuesFromRestriction()) {
            try {
                log.debug("Formatting restriction");
                AllValuesFromRestriction restA = rest.asAllValuesFromRestriction();
                String[] fmt = formatPropertyAndRange(restA.getOnProperty(), restA.getAllValuesFrom());
                fmt[6] = "allValuesFrom";
                return fmt;
            } catch (Exception e) {
                log.error(e, e);
                log.error(rest);
            }
        } else if(rest.isSomeValuesFromRestriction()) {
            try {
                log.debug("Formatting restriction");
                SomeValuesFromRestriction restE = rest.asSomeValuesFromRestriction();
                String[] fmt = formatPropertyAndRange(restE.getOnProperty(), restE.getSomeValuesFrom());
                fmt[6] = "someValuesFrom";
                return fmt;
            } catch (Exception e) {
                log.error(e, e);
                log.error(rest);
            }
        } 
        return null;      
    }
    
    private static String[] formatPropertyAndRange(OntProperty ontProp, Resource range) {
        // 0 full URI
        // 1 prefix
        // 2 local name
        // 3 range prefix
        // 4 range local name
        // 5 range values
        // 6 "range" if by explicit range
        if(range == null) {
            log.warn(ontProp.getURI() + " has a null range");
            return null;
        }
        String[] out = new String[7];
        String[] propPln = prefixAndLocalName(ontProp);
        out[0] = ontProp.getURI();
        out[1] = propPln[0];
        out[2] = propPln[1];
        String[] rangePln = prefixAndLocalName(range);
        out[3] = rangePln[0];
        out[4] = rangePln[1];
        if(range.hasProperty(RDF.type, RDFS.Datatype) && range.hasProperty(OWL.oneOf)) {
            RDFNode listValue = range.getProperty(OWL.oneOf).getObject();
            if(listValue.canAs(RDFList.class)) {
                StringBuilder values = new StringBuilder();
                List<RDFNode> nodes = listValue.as(RDFList.class).asJavaList();                    
                values.append("{");
                boolean firstPrinted = true;
                for(RDFNode node : nodes) {
                    if(firstPrinted == false) {
                        values.append(", ");
                    }
                    firstPrinted = false;
                    if(node.isLiteral()){
                        values.append("\"").append(node.asLiteral().getLexicalForm()).append("\"");
                    }
                }
                values.append("}");
                out[5] = values.toString();
            }
        } else if (range.canAs(OntClass.class)) {
            OntClass rangeClass = range.as(OntClass.class);
            if(!rangeClass.isAnon()) {
                String[] pln = prefixAndLocalName(rangeClass);
                if(!StringUtils.isEmpty(pln[0])) {
                    out[3] = pln[0];
                    out[4] = pln[1];
                } else {
                    out[4] = pln[1];
                }
            } else if(rangeClass.isUnionClass()) {
                UnionClass rangeUnion = rangeClass.asUnionClass();
                ExtendedIterator<? extends OntClass> operandIt = rangeUnion.listOperands();
                StringBuilder values = new StringBuilder();
                boolean firstPrinted = true;
                while(operandIt.hasNext()) {
                    OntResource operand = operandIt.next();
                    if(!operand.isAnon()) {
                        if(!firstPrinted) {
                            values.append(" or ");                            
                        }
                        firstPrinted = false;
                        String[] pln = prefixAndLocalName(operand);
                        if(!StringUtils.isEmpty(pln[0])) {
                            values.append(pln[0]).append(":").append(pln[1]);
                        } else {
                            values.append(pln[1]);
                        }
                    }
                }
                out[4] = values.toString();
            } else if(rangeClass.isDataRange()) {
                DataRange dataRange = rangeClass.asDataRange();
                ExtendedIterator<? extends Literal> operandIt = dataRange.listOneOf();
                StringBuilder values = new StringBuilder();
                values.append("{");
                boolean firstPrinted = true;
                while(operandIt.hasNext()) {
                    if(firstPrinted == false) {
                        values.append(", ");
                    }
                    firstPrinted = false;
                    Literal lit = operandIt.next();
                    values.append("\"").append(lit.getLexicalForm()).append("\"");
                }
                values.append("}");
                out[5] = values.toString();
            }
        } else if(!range.isAnon()) {
            String[] pln = prefixAndLocalName(range);
            if(!StringUtils.isEmpty(pln[0])) {
                out[3] = pln[0];
                out[4] = pln[1];
            } else {
                out[4] = pln[1];
            }
        }
        return out;
    }
    
    private static String getLabel(Resource resource) {
        StmtIterator sit = resource.listProperties(RDFS.label);
        String label = null; 
        while(sit.hasNext()) {
            Statement stmt = sit.next();
            if(stmt.getObject().isLiteral()) {
                Literal lit = stmt.getObject().asLiteral();
                label = lit.getLexicalForm();
                String lang = lit.getLanguage();
                if(lang != null) {
                    if(lang.startsWith("en") || lang.startsWith("EN")) {
                        return label;
                    }
                }
            }
        }
        return label;
    }
    
    private static Map<String, CellStyle> makePrefixStyles(XSSFWorkbook wb) {
        Map<String, Short> colors = new HashMap<String, Short>();
        colors.put("bibo", IndexedColors.PINK.index);
        colors.put("cito", IndexedColors.LAVENDER.index);
        colors.put("foaf", IndexedColors.AQUA.index);
        colors.put("gn", IndexedColors.BROWN.index);
        colors.put("bfo", IndexedColors.LIME.index);
        colors.put("ero", IndexedColors.LIME.index);
        colors.put("iao", IndexedColors.LIME.index);
        colors.put("ro", IndexedColors.LIME.index);
        colors.put("owl", IndexedColors.TAN.index);
        colors.put("rdf", IndexedColors.TAN.index);
        colors.put("rdfs", IndexedColors.TAN.index);
        colors.put("xsd", IndexedColors.TAN.index);
        colors.put("roh", IndexedColors.LEMON_CHIFFON.index);
        colors.put("rohes", IndexedColors.ORANGE.index);
        colors.put("rohpt", IndexedColors.ORANGE.index);
        colors.put("rohuk", IndexedColors.LIGHT_ORANGE.index);
        colors.put("rohum", IndexedColors.DARK_RED.index);
        colors.put("skos", IndexedColors.PLUM.index);
        colors.put("uneskos", IndexedColors.DARK_YELLOW.index);
        colors.put("vcard", IndexedColors.LIGHT_YELLOW.index);
        colors.put("vivo", IndexedColors.SEA_GREEN.index);
        colors.put("geonames", IndexedColors.CORAL.index);
        colors.put("default", IndexedColors.WHITE.index);        
        HashMap<String, CellStyle> styles = new HashMap<String, CellStyle>();
        for(String prefix : colors.keySet()) {
            CellStyle style = wb.createCellStyle();
            style.setFillForegroundColor(colors.get(prefix));
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setWrapText(true);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);
            styles.put(prefix, style);
            CellStyle bold = wb.createCellStyle();
            bold.setFillForegroundColor(colors.get(prefix));
            bold.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            bold.setWrapText(true);
            bold.setBorderBottom(BorderStyle.THIN);
            bold.setBorderLeft(BorderStyle.THIN);
            bold.setBorderRight(BorderStyle.THIN);
            bold.setBorderTop(BorderStyle.THIN);
            XSSFFont boldfont = wb.createFont();
            boldfont.setBold(true);
            bold.setFont(boldfont);
            styles.put(prefix + "bold", bold);
        }
        return styles;
    }
    
    private static class RowCreator {

        private int rowIndex = -1;
        private XSSFSheet sheet;

        public RowCreator(XSSFSheet sheet) {
            this.sheet = sheet;
        }

        public XSSFRow createRow() {
            rowIndex = rowIndex + 1;
            return sheet.createRow(rowIndex);
        }

        public int getRowIndex() {
            return this.rowIndex;
        }

    }
    
    protected static Model processUniversityStructure(Model model) {
        boolean DELETE = true;
        boolean ADD = !DELETE;
        String dir = "/university-structure/";
        model = construct(dir + "additions/university.rq", model, ADD);
        model = construct(dir + "additions/division.rq", model, ADD);
        model = construct(dir + "additions/locatedIn.rq", model, ADD);
        model = construct(dir + "removals/university.rq", model, DELETE);
        model = construct(dir + "removals/division.rq", model, DELETE);
        model = construct(dir + "removals/locatedIn.rq", model, DELETE);
        return model;
    }
    
    protected static Model processGeopolitical(Model model) {
        boolean DELETE = true;
        boolean ADD = !DELETE;
        String dir = "/geopolitical/";
        model = construct(dir + "additions/country.rq", model, ADD);
        model = construct(dir + "additions/country-eu.rq", model, ADD);
        model = construct(dir + "additions/level1.rq", model, ADD);
        model = construct(dir + "additions/level1-eu.rq", model, ADD);
        model = construct(dir + "additions/level2.rq", model, ADD);
        model = construct(dir + "removals/country.rq", model, DELETE);
        model = construct(dir + "removals/level1.rq", model, DELETE);
        model = construct(dir + "removals/level2.rq", model, DELETE);
        return model;
    }
    
    private static Model construct(String queryFileName, Model model, boolean delete) {
        String queryStr = loadQuery(queryFileName);
        QueryExecution qe = QueryExecutionFactory.create(queryStr, model);
        try {
            Model results = qe.execConstruct();
            if(delete) {
                model.remove(results);
            } else {
                model.add(results);
            }
        } finally {
            if(qe != null) {
                qe.close();
            }
        }
        return model;
    }
    
    protected static String loadQuery(String resourcePath) {
        InputStream inputStream = Fuse.class.getResourceAsStream(
                resourcePath);
        StringBuffer fileContents = new StringBuffer();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String ln;
            while ( (ln = reader.readLine()) != null) {
                fileContents.append(ln).append('\n');
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to load " + resourcePath, e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return fileContents.toString();
    }
    
}

