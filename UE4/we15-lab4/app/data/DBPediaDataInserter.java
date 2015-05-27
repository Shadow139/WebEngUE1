package data;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import play.Logger;
import models.Answer;
import models.Category;
import models.JeopardyDAO;
import models.Question;

import com.hp.hpl.jena.rdf.model.Model;
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
		
		// Resource Ubisoft is available at http://dbpedia.org/resource/Ubisoft  
		// Load all statements as we need to get the name later 
		Resource publisher = DBPediaService.loadStatements(DBPedia.createResource("Ubisoft"));     
		
		// build SPARQL-query 
		SelectQueryBuilder gameQuery = DBPediaService.createQueryBuilder()  
				.setLimit(200) // at most five statements  
				.addWhereClause(RDF.type, DBPediaOWL.VideoGame)  
				.addPredicateExistsClause(FOAF.name)  
				.addWhereClause(DBPediaOWL.publisher, publisher)  
				.addFilterClause(RDFS.label, Locale.GERMAN)  
				.addFilterClause(RDFS.label, Locale.ENGLISH);           
		gameQuery.setLimit(200);
		
		// retrieve data from dbpedia
		Model ubisoftGames = DBPediaService.loadStatements(gameQuery.toQueryString());  
		
		// get english and german game names, e.g., for right choices 
		List<String> englishUbisoftGameNames =     DBPediaService.getResourceNames((com.hp.hpl.jena.rdf.model.Model) ubisoftGames, Locale.ENGLISH); 
		List<String> germanUbisoftGameNames =     DBPediaService.getResourceNames((com.hp.hpl.jena.rdf.model.Model) ubisoftGames, Locale.GERMAN);     
		
		// alter query to get games without Ubisoft 
		gameQuery.removeWhereClause(DBPediaOWL.publisher, publisher); 
		gameQuery.addMinusClause(DBPediaOWL.publisher, publisher);        
		gameQuery.setLimit(200);
		
		// retrieve data from dbpedia   
		Model noUbisoftGames = DBPediaService.loadStatements(gameQuery.toQueryString());  
		// get english and german game names, e.g., for wrong choices 
		List<String> englishNoUbisoftGameNames =     DBPediaService.getResourceNames((com.hp.hpl.jena.rdf.model.Model) noUbisoftGames, Locale.ENGLISH); 
		List<String> germanNoUbisoftGameNames =     DBPediaService.getResourceNames((com.hp.hpl.jena.rdf.model.Model) noUbisoftGames, Locale.GERMAN); 	
		
		
		gameQuery.addWhereClause(DBPediaOWL.publisher, publisher);
		gameQuery.removeMinusClause(DBPediaOWL.publisher, publisher); 
		gameQuery.setLimit(200);

		// retrieve data from dbpedia
		ubisoftGames = DBPediaService.loadStatements(gameQuery.toQueryString());  
		
		// get english and german game names, e.g., for right choices 
		englishUbisoftGameNames =     DBPediaService.getResourceNames((com.hp.hpl.jena.rdf.model.Model) ubisoftGames, Locale.ENGLISH); 
		germanUbisoftGameNames =     DBPediaService.getResourceNames((com.hp.hpl.jena.rdf.model.Model) ubisoftGames, Locale.GERMAN);     
		
		
		Logger.info("size of englishNoUbisoftGameNames: " + englishNoUbisoftGameNames.size());
		Logger.info("size of germanNoUbisoftGameNames: " + germanNoUbisoftGameNames.size());

		Category category = new Category();
		category.setNameDE("Ubisoft Spiele");
		category.setNameEN("Ubisoft Games");
		Random randomno = new Random();
		for(int i = 1; i < 8; i++){
			Question question = new Question();
			question.setTextDE("Ist das ein Ubisoft Spiel?");
			question.setTextEN("Is it an Ubisoft Game?");
			question.setValue(10*i);
			
			int temp = randomno.nextInt(3)+2;
			Logger.info("gamesize: "+germanUbisoftGameNames.size()+"");
			Logger.info("gamesizewrong: "+germanNoUbisoftGameNames.size()+"");
			for(int r = 1; r < temp; r++){
				int temp2 = randomno.nextInt(1+germanUbisoftGameNames.size()-2);
				Answer rightChoice = new Answer();
				rightChoice.setTextDE(germanUbisoftGameNames.get(temp2));
				rightChoice.setTextEN(englishUbisoftGameNames.get(temp2));
				question.addRightAnswer(rightChoice);
			}
			temp = randomno.nextInt(3)+4;
			
			for(int j = 1; j < temp; j++){ // Dieser Teil is noch Buggy wir müssen anschauen wie genau die answers geladen werden
				Answer wrongChoice = new Answer();
				int temp2 = randomno.nextInt(1+englishNoUbisoftGameNames.size()-2);
				wrongChoice.setTextDE(englishNoUbisoftGameNames.get(temp2));
				wrongChoice.setTextEN(germanNoUbisoftGameNames.get(temp2));
				question.addWrongAnswer(wrongChoice);
			}
			
			category.addQuestion(question);
		}
		
		JeopardyDAO.INSTANCE.persist(category);
	}
		

}
