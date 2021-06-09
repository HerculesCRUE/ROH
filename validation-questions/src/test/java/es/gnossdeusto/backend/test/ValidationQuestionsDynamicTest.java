package es.gnossdeusto.backend.test;

import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.ThrowingConsumer;
import org.mindswap.pellet.jena.PelletReasonerFactory;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ValidityReport;
import com.hp.hpl.jena.reasoner.ValidityReport.Report;
import com.hp.hpl.jena.util.PrintUtil;

import es.gnossdeusto.backend.QueryExecutor;
import es.gnossdeusto.backend.validation.InvalidOntologyException;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.DynamicTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

 
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class ValidationQuestionsDynamicTest {
    
	private static Logger log = Logger.getLogger(ValidationQuestionsDynamicTest.class.toString());

	
	public static void printStatements(Model m, Resource s, Property p, Resource o) {
		for (StmtIterator i = m.listStatements(s, p, o); i.hasNext();) {
			Statement stmt = i.nextStatement();
			log.info(" - " + PrintUtil.print(stmt));
			// System.out.println(" - " + PrintUtil.print(stmt));
		}
	}

	public static void printIterator(Iterator<Report> i, String header) throws InvalidOntologyException {
		// System.out.println(header);
		log.info(header);
		for (int c = 0; c < header.length(); c++)
			System.out.print("=");
		System.out.println();
		log.info("\n");

		if (i.hasNext()) {
			while (i.hasNext()) {
				Report report = i.next();
				//System.out.println(report.toString());
				log.info(report.toString());
			}
			throw new InvalidOntologyException("Invalid ontology, see logs files...");
		} else
			//System.out.println("<EMPTY>");
			log.info("<EMPTY>");

		// System.out.println();
		log.info("\n");
	}
	
	private Model getModel(String[] modules) throws InvalidOntologyException {
		try {
			Model emptyModel = ModelFactory.createDefaultModel();
			
			if (Boolean.valueOf(System.getProperty("reasoning", "true"))) {

				Reasoner reasoner = (Reasoner) PelletReasonerFactory.theInstance().create();
				InfModel model = ModelFactory.createInfModel(reasoner, emptyModel);
				for (String module : modules) {
					model.read(module);
				}
				ValidityReport report = model.validate();
				printIterator(report.getReports(), "Validation Results");
				
				return model;
			} else {
			
				Model model = ModelFactory.createDefaultModel();
				for (String module : modules) {
					model.read(module);
				}
				
				return model;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
    @TestFactory
    Stream<DynamicTest> validationQuestions() throws IOException{
		
        try {
        	List<String> inputList = new ArrayList<String>();
        	List<JSONObject> outputList = new ArrayList<JSONObject>();
			JSONObject report =new JSONObject();


			Model model = getModel(System.getProperty("model").split(","));
			File queryFolder = new File(System.getProperty("queryFolder"));
			List<String> pathList = new ArrayList<String>();
			for (File inputFile : queryFolder.listFiles()) {
				if (inputFile.getName().endsWith(".sparql")) {
					String query = null;
					try {
						query = FileUtils.readFileToString(inputFile, (Charset) null);
						File outputFile = new File(inputFile.getAbsolutePath().replace(".sparql", ".result"));
						try {
							String queryResult = FileUtils.readFileToString(outputFile, (Charset) null);
							inputList.add(query);
							//outputList.add(queryResult);
							outputList.add(new JSONObject(queryResult));
							pathList.add(outputFile.getAbsolutePath().replace(".result",".json"));
						} catch (IOException e1) {
							System.out.println(String.format("Can't read result file %s", outputFile.getAbsolutePath()));
							report.put(inputFile.getAbsolutePath(), "Skipped");
						}
					} catch (IOException e) {
						System.out.println(String.format("Can't read query file %s", inputFile.getAbsolutePath()));
					}				
				}
			}
			
			Function<String, String> displayNameGenerator 
			  = (input) -> "Executing: " + input;

			
			Iterator<String> inputGenerator = inputList.iterator();
			ThrowingConsumer<String> testExecutor = (input) -> {
				int id = inputList.indexOf(input);
				JSONObject result = null;
				try {
					result = QueryExecutor.execute(input, model);
					FileWriter file = new FileWriter(pathList.get(id));
            		file.write(result.toString());
					file.close();
					JSONAssert.assertEquals(outputList.get(id), result, JSONCompareMode.NON_EXTENSIBLE);
					report.put(pathList.get(id), "Pass");
				} catch (AssertionError ae) {
					ae.printStackTrace();
					System.out.println("ERROR\n");
					System.out.println(result);
					System.out.println(pathList.get(id));
					report.put(pathList.get(id), "Incorrect");
					throw ae;
				}
				catch(IOException ae){
					ae.printStackTrace();
					System.out.println("ERROR\n");
					System.out.println(pathList.get(id));
					report.put(pathList.get(id), "Incorrect");
					throw ae;

				}
			};
			FileWriter file = new FileWriter(System.getProperty("queryFolder").concat("report.json"));
			System.out.println(report);
            file.write(report.toString());
			System.out.println("----------------------------------------------------------------------");
			file.close();
			return DynamicTest.stream(
					  inputGenerator, displayNameGenerator, testExecutor);

			
		} catch (InvalidOntologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; 

    }
}
