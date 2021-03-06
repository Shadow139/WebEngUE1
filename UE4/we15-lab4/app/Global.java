
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import at.ac.tuwien.big.we.dbpedia.api.DBPediaService;
import models.Category;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Play;
import play.db.jpa.JPA;
import play.libs.F.Function0;
import data.DBPediaDataInserter;
import data.JSONDataInserter;

public class Global extends GlobalSettings {
	
	@play.db.jpa.Transactional
	public static void insertJSonData() throws IOException {
		String file = Play.application().configuration().getString("questions.filePath");		
		Logger.info("Data from: " + file);
		InputStream is = Play.application().resourceAsStream(file);
		String banane = null;
		//banane.charAt(9);
		List<Category> categories = JSONDataInserter.insertData(is);
		DBPediaDataInserter.insertData();
		Logger.info(categories.size() + " categories from json file '" + file + "' inserted.");
	}
	
	@play.db.jpa.Transactional
    public void onStart(Application app) {
       try {
    	   JPA.withTransaction(new Function0<Boolean>() {

			@Override
			public Boolean apply() throws Throwable {
				insertJSonData();
				return true;
			}
			   
			});
       } catch (Throwable e) {
    	   e.printStackTrace();
       }
    }

    public void onStop(Application app) {
        Logger.info("Application shutdown...");
    }
    
    

}