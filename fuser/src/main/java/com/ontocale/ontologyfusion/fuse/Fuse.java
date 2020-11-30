package com.ontocale.ontologyfusion;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.RDFList;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.ResourceUtils;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
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
    private static final Log log = LogFactory.getLog(Fuse.class);
    private static HashMap<String, String> prefixes1 = new HashMap<String, String>();
    private static HashMap<String, String> prefixes2 = new HashMap<String, String>();
    private static HashMap<String, String> prefixes2reverse = new HashMap<String, String>();
    private static final List<String> entryPoints = Arrays.asList(
            "vivo:AcademicDegree", "roh:Accreditation", "rohes:Account", 
            "roh:Activity", "foaf:Agent", "skos:Concept", "roh:CurriculumVitae",
            "roh:Expense", "gn:Feature", "roh:Infrastructure", "roh:Metric", 
            "vivo:Project", "roh:ResearchObject", "bfo:Role", "ero:Service", 
            "rohes:Tax", "bfo:TemporalRegion");
    
    static {
        // ib-hercules prefixes
        prefixes1.put("asio", "http://purl.org/hercules/asio/core#");
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
        Model ontology2 = loadOntology(args[2]);
        Model fused = fuse(args[0], ontology1, ontology2);
        fused.write(new FileOutputStream(new File(args[3]) + ".ttl"), "TTL");
        XSSFWorkbook spreadsheet = createSpreadsheet(fused);
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
            String useInsteadURI = (row.size() > 1) ? uri(row.get(1), prefixes2, null) : "";
            String superURI = (row.size() > 2) ? uri(row.get(3), prefixes2, null) : "";
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
        fused = changeNamespace(fused, ASIO, ROH);
        return fused;
    }
    
    private static Model changeNamespace(Model model, String fromNS, String toNS) {
        ResIterator resIt = model.listSubjects();
        while(resIt.hasNext()) {
            Resource res = resIt.next();
            if(!res.isAnon() && fromNS.equals(res.getNameSpace())) {
                ResourceUtils.renameResource(res, toNS + res.getLocalName());
            }
        }
        NodeIterator nodeIt = model.listObjects();
        while(nodeIt.hasNext()) {
            RDFNode node = nodeIt.next();
            if(node.isURIResource()) {
                Resource res = node.asResource();
                if(!res.isAnon() && fromNS.equals(res.getNameSpace())) {
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
            throw new RuntimeException("No type found for entity " + fromURI);
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
            throw new RuntimeException("No type found for entity " + fromURI);
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
    
    private static Model addStatuses(Model model) {
        List<String> statuses = Arrays.asList("Accepted", "Cancelled", 
                "Closed", "Funded", "Rejected", "Signed", "Terminated", "Unfunded", "Open", "Submitted", "Proposal Submitted");
        for(String status : statuses) {
            Resource statusRes = model.createResource(ROH + status.replaceAll(" ", ""));
            model.add(statusRes, RDF.type, OWL.Class);
            model.add(statusRes, RDFS.label, status, "en");
            model.add(statusRes, RDFS.subClassOf, model.getResource(ROH + "Status"));
        }
        List<String> statusProps = Arrays.asList("status", "patentStatus", "projectStatus");
        List<Resource> restrictionsToUpdate = new ArrayList<Resource>();
        for(String statusProp : statusProps) {
            ResIterator restrictionit = model.listResourcesWithProperty(
                    OWL.onProperty, model.getProperty(ROH + statusProp));
            while(restrictionit.hasNext()) {
                restrictionsToUpdate.add(restrictionit.next()); 
            }
            for(Resource restriction : restrictionsToUpdate) {
                model.removeAll(restriction, OWL.allValuesFrom, null);
                model.add(restriction, OWL.allValuesFrom, model.getResource(ROH + "Status"));
            }
            // eliminate property's original range
            Model preserveNonRange = ModelFactory.createDefaultModel();
            preserveNonRange.add(model.listStatements(model.getResource(statusProp), RDFS.label, (RDFNode) null));
            preserveNonRange.add(model.listStatements(model.getResource(statusProp), RDFS.domain, (RDFNode) null));
            preserveNonRange.add(model.listStatements(model.getResource(statusProp), RDFS.comment, (RDFNode) null));
            model.remove(getDescription(model, statusProp));
            model.add(preserveNonRange);
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
    
    private static XSSFWorkbook createSpreadsheet(Model ontology) {
        XSSFWorkbook wb = new XSSFWorkbook();
        Map<String, CellStyle> styles = makePrefixStyles(wb);
        XSSFSheet sheet = wb.createSheet("ontology");
        PropertyTemplate pt = new PropertyTemplate();
        RowCreator rowCreator = new RowCreator(sheet);
        StmtIterator sit = ontology.listStatements(null, RDF.type, OWL.Class);
        while(sit.hasNext()) {
            Statement stmt = sit.next();
            if(stmt.getSubject().isURIResource()) {
                Resource clazz = stmt.getSubject();
                XSSFRow row = rowCreator.createRow();
                XSSFCell cell0 = row.createCell(0);
                String prefix = prefixes2reverse.get(clazz.getNameSpace());
                if(prefix == null) {
                    log.warn("No prefix found for namespace " + clazz.getNameSpace());
                    cell0.setCellValue(clazz.getURI());
                } else {
                    cell0.setCellValue(prefix + ":" + clazz.getLocalName());
                    CellStyle style = styles.get(prefix);
                    if(style == null) {
                        log.warn("No cell style found for prefix " + prefix);
                    } else {
                        cell0.setCellStyle(style);
                    }
                }
                List<String> properties = getProperties(clazz.getURI(), ontology);
                for(String prop : properties) {
                    XSSFRow propRow = rowCreator.createRow();
                    XSSFCell propCell = propRow.createCell(1);
                    propCell.setCellValue(prop);                    
                }
            }
        }
        sheet.setColumnWidth(0, 7500);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(4, 4000);
        sheet.setColumnWidth(5, 4000);
        sheet.setColumnWidth(6, 4000);
        return wb;
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
    
    private static List<String> getProperties(String classURI, Model m) {
        List<String> properties = new ArrayList<String>();
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, m);
        OntResource clazz = ontModel.getOntResource(classURI);
        if(!clazz.canAs(OntClass.class)) {
            log.warn(classURI + " cannot as OntClass. Skipping.");
            return properties;
        }
        OntClass ontClazz = clazz.as(OntClass.class);
        // domains
        ExtendedIterator<ObjectProperty> ontProps = ontModel.listObjectProperties();
        while(ontProps.hasNext()) {
            OntProperty ontProp = ontProps.next();
            checkPropertyDomain(ontProp, ontClazz, properties);
        }
        ExtendedIterator<DatatypeProperty> dataProps = ontModel.listDatatypeProperties();
        while(dataProps.hasNext()) {
            OntProperty dataProp = dataProps.next();
            checkPropertyDomain(dataProp, ontClazz, properties);
        }
        // restrictions 
        ExtendedIterator<? extends OntClass> superIt = ontClazz.listSuperClasses();
        while(superIt.hasNext()) {
            OntClass superClazz = superIt.next();
            if(superClazz.isRestriction()) {
                Restriction rest = superClazz.asRestriction();
                String fmt = formatRestriction(rest);
                if(fmt != null) {
                    properties.add(fmt);
                }
            } else if(superClazz.isIntersectionClass()) {
                IntersectionClass superIntersection = superClazz.asIntersectionClass();
                Iterator<? extends OntClass> operandIt = superIntersection.listOperands();
                // TODO refactor this
                while(operandIt.hasNext()) {
                    OntClass operand = operandIt.next();
                    if(operand.isRestriction()) {
                        Restriction rest = operand.asRestriction();
                        String fmt = formatRestriction(rest);
                        if(fmt != null) {
                            properties.add(fmt);
                        }
                    }
                }
            }
        }
        return properties;
    }
    
    private static void checkPropertyDomain(OntProperty ontProp, OntClass ontClazz, List<String> properties) {
        log.info("Checking domains for " + ontProp.getURI());
        ExtendedIterator<? extends OntResource> domains = ontProp.listDomain();
        while(domains.hasNext()) {
            OntResource domain = domains.next();
            if(domain.getURI() != null) {
                log.info("Named domain class " + domain.getURI());
            }
            if(domain.equals(ontClazz)) {
                log.info("Adding simple domain");
                String fmt = formatPropertyAndRange(ontProp, ontProp.getRange());
                if(fmt != null) {
                    properties.add(fmt);
                }
            } else if(domain.canAs(OntClass.class)) {
                log.info("Checking complex domain");
                OntClass domainClazz = domain.as(OntClass.class);
                if(domainClazz.isUnionClass()) {
                    UnionClass domainUnion = domainClazz.asUnionClass();
                    Iterator<? extends OntClass> operandIt = domainUnion.listOperands();
                    while(operandIt.hasNext()) {
                        if(ontClazz.equals(operandIt.next())) {
                            String fmt = formatPropertyAndRange(ontProp, ontProp.getRange());
                            if(fmt != null) {
                                properties.add(fmt);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private static String formatRestriction(Restriction rest) {
        if(rest.isAllValuesFromRestriction()) {
            try {
                AllValuesFromRestriction restA = rest.asAllValuesFromRestriction();
                return "allValuesFrom: " + formatPropertyAndRange(restA.getOnProperty(), restA.getAllValuesFrom());
            } catch (Exception e) {
                log.error(e, e);
                log.error(rest);
            }
        } else if(rest.isSomeValuesFromRestriction()) {
            try {
                SomeValuesFromRestriction restE = rest.asSomeValuesFromRestriction();
                return "someValuesFrom: " + formatPropertyAndRange(restE.getOnProperty(), restE.getSomeValuesFrom());
            } catch (Exception e) {
                log.error(e, e);
                log.error(rest);
            }
        } 
        return null;      
    }
    
    private static String formatPropertyAndRange(OntProperty ontProp, Resource range) {
        if(range == null) {
            log.warn(ontProp.getURI() + " has a null range");
            return null;
        }
        StringBuilder out = new StringBuilder()
                .append(getLabel(ontProp)).append(": range: ");        
        if(range.hasProperty(RDF.type, RDFS.Datatype) && range.hasProperty(OWL.oneOf)) {
            RDFNode listValue = range.getProperty(OWL.oneOf).getObject();
            if(listValue.canAs(RDFList.class)) {
                List<RDFNode> nodes = listValue.as(RDFList.class).asJavaList();                    
                out.append("{");
                boolean firstPrinted = true;
                for(RDFNode node : nodes) {
                    if(firstPrinted == false) {
                        out.append(", ");
                    }
                    firstPrinted = false;
                    if(node.isLiteral()){
                        out.append("\"").append(node.asLiteral().getLexicalForm()).append("\"");
                    }
                }
                out.append("}");                
            }
        } else {
            OntClass rangeClass = range.as(OntClass.class);
            if(!rangeClass.isAnon()) {
                String[] pln = prefixAndLocalName(rangeClass);
                if(!StringUtils.isEmpty(pln[0])) {
                    out.append(pln[0]).append(":").append(pln[1]);
                } else {
                    out.append(pln[1]);
                }
            } else if(rangeClass.isUnionClass()) {
                UnionClass rangeUnion = rangeClass.asUnionClass();
                ExtendedIterator<? extends OntClass> operandIt = rangeUnion.listOperands();
                boolean firstPrinted = true;
                while(operandIt.hasNext()) {
                    OntResource operand = operandIt.next();
                    if(!operand.isAnon()) {
                        if(!firstPrinted) {
                            out.append(" or ");                            
                        }
                        firstPrinted = false;
                        String[] pln = prefixAndLocalName(operand);
                        if(!StringUtils.isEmpty(pln[0])) {
                            out.append(pln[0]).append(":").append(pln[1]);
                        } else {
                            out.append(pln[1]);
                        }
                    }
                }
            } else if(rangeClass.isDataRange()) {
                DataRange dataRange = rangeClass.asDataRange();
                ExtendedIterator<? extends Literal> operandIt = dataRange.listOneOf();
                out.append("{");
                boolean firstPrinted = true;
                while(operandIt.hasNext()) {
                    if(firstPrinted == false) {
                        out.append(", ");
                    }
                    firstPrinted = false;
                    Literal lit = operandIt.next();
                    out.append("\"").append(lit.getLexicalForm()).append("\"");
                }
                out.append("}");
            }
        }
        return out.toString();
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
        colors.put("bfo", IndexedColors.GREEN.index);
        colors.put("ero", IndexedColors.GREEN.index);
        colors.put("iao", IndexedColors.GREEN.index);
        colors.put("ro", IndexedColors.GREEN.index);
        colors.put("owl", IndexedColors.TAN.index);
        colors.put("rdf", IndexedColors.TAN.index);
        colors.put("rdfs", IndexedColors.TAN.index);
        colors.put("roh", IndexedColors.DARK_YELLOW.index);
        colors.put("rohes", IndexedColors.ORANGE.index);
        colors.put("rohum", IndexedColors.DARK_RED.index);
        colors.put("skos", IndexedColors.PLUM.index);
        colors.put("uneskos", IndexedColors.DARK_YELLOW.index);
        colors.put("vcard", IndexedColors.LIGHT_YELLOW.index);
        colors.put("vivo", IndexedColors.SEA_GREEN.index);  
        HashMap<String, CellStyle> styles = new HashMap<String, CellStyle>();
        for(String prefix : colors.keySet()) {
            CellStyle style = wb.createCellStyle();
            style.setFillForegroundColor(colors.get(prefix));
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            styles.put(prefix, style);
            CellStyle bold = wb.createCellStyle();
            bold.setFillForegroundColor(colors.get(prefix));
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
    
}

