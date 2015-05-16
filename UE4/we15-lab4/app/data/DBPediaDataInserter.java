package data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import models.Answer;
import models.Category;
import models.JeopardyDAO;
import models.Question;

import org.springframework.ui.Model;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

import at.ac.tuwien.big.we.dbpedia.api.DBPediaService;
import at.ac.tuwien.big.we.dbpedia.api.SelectQueryBuilder;
import at.ac.tuwien.big.we.dbpedia.vocabulary.DBPedia;
import at.ac.tuwien.big.we.dbpedia.vocabulary.DBPediaOWL;

public class DBPediaDataInserter {

	public static void insertData() {
		// Check if DBpedia is available 
		if(!DBPediaService.isAvailable())
			return;  
		
		// Resource Tim Burton is available at http://dbpedia.org/resource/Tim_Burton  
		// Load all statements as we need to get the name later 
		Resource director = DBPediaService.loadStatements(DBPedia.createResource("Tim_Burton"));     
		
		// Resource Johnny Depp is available at http://dbpedia.org/resource/Johnny_Depp  
		// Load all statements as we need to get the name later 
		Resource actor = DBPediaService.loadStatements(DBPedia.createResource("Johnny_Depp"));  
		
		// retrieve english and german names, might be used for question text  
		String englishDirectorName = DBPediaService.getResourceName(director, Locale.ENGLISH); 
		String germanDirectorName = DBPediaService.getResourceName(director, Locale.GERMAN);  
		String englishActorName = DBPediaService.getResourceName(actor, Locale.ENGLISH); 
		String germanActorName = DBPediaService.getResourceName(actor, Locale.GERMAN);  
		
		// build SPARQL-query 
		SelectQueryBuilder movieQuery = DBPediaService.createQueryBuilder()  
				.setLimit(5) // at most five statements  
				.addWhereClause(RDF.type, DBPediaOWL.Film)  
				.addPredicateExistsClause(FOAF.name)  
				.addWhereClause(DBPediaOWL.director, director)  
				.addFilterClause(RDFS.label, Locale.GERMAN)  
				.addFilterClause(RDFS.label, Locale.ENGLISH);  // retrieve data from dbpedia         
		Model timBurtonMovies = (Model) DBPediaService.loadStatements(movieQuery.toQueryString());  // get english and german movie names, e.g., for right choices 
		List<String> englishTimBurtonMovieNames =     DBPediaService.getResourceNames((com.hp.hpl.jena.rdf.model.Model) timBurtonMovies, Locale.ENGLISH); 
		List<String> germanTimBurtonMovieNames =     DBPediaService.getResourceNames((com.hp.hpl.jena.rdf.model.Model) timBurtonMovies, Locale.GERMAN);     // alter query to get movies without tim burton 
		
		movieQuery.removeWhereClause(DBPediaOWL.director, director); 
		movieQuery.addMinusClause(DBPediaOWL.director, director);  // retrieve data from dbpedia         
		
		Model noTimBurtonMovies = (Model) DBPediaService.loadStatements(movieQuery.toQueryString());  // get english and german movie names, e.g., for wrong choices 
		List<String> englishNoTimBurtonMovieNames =     DBPediaService.getResourceNames((com.hp.hpl.jena.rdf.model.Model) noTimBurtonMovies, Locale.ENGLISH); 
		List<String> germanNoTimBurtonMovieNames =     DBPediaService.getResourceNames((com.hp.hpl.jena.rdf.model.Model) noTimBurtonMovies, Locale.GERMAN); 		
		
		Category category = new Category();
		category.setNameDE("TIM BURTONS AlICE FMOCVAFLILEM FILME");
		category.setNameEN("MOVIES");
		for(int i = 0; i < 5; i++){
			Question question = new Question();
			question.setTextDE("Ist das ein Tim Burton Film?");
			question.setTextEN("Is it a Tim Burton Movie?");
			question.setValue(10);
			
			Answer rightChoice = new Answer();
			rightChoice.setTextDE(germanTimBurtonMovieNames.get(i));
			rightChoice.setTextEN(englishTimBurtonMovieNames.get(i));
			question.addRightAnswer(rightChoice);
			
			for(int j = 0; j < 3; j++){
				Answer wrongChoice = new Answer();
				wrongChoice.setTextDE(englishNoTimBurtonMovieNames.get(3*i + j));
				wrongChoice.setTextEN(germanNoTimBurtonMovieNames.get(3*i + j));
				question.addWrongAnswer(wrongChoice);
			}
			
			category.addQuestion(question);
		}
		
		JeopardyDAO.INSTANCE.persist(category);
	}
		

}
