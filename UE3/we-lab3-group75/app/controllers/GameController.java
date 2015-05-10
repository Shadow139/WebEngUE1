package controllers;

import java.io.File;

import org.apache.commons.logging.Log;



import play.api.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import at.ac.tuwien.big.we15.lab2.api.*; 
import at.ac.tuwien.big.we15.lab2.api.impl.*;

public class GameController{
	JeopardyFactory factory;
	User user;

	
	
	public JeopardyGame startGame(String username, String avatarId){
		String language = System.getProperty("user.language");
		factory = new PlayJeopardyFactory("data.de.json");
		if (language.contains("english")||language.contains("en")||language.contains("English")||language.contains("En")||language.contains("EN")) {
			factory = new PlayJeopardyFactory("data.en.json"); 
		}
		user = new SimpleUser();
		user.setName(username);
		user.setAvatar(Avatar.getAvatar(avatarId));
		JeopardyGame game = factory.createGame(user); 

		return game;
		
	}
	
	public JeopardyGame createGame(){
		JeopardyGame game = factory.createGame(user); 

		return game;
	}
	
	public void getCleanPath() {
	    ClassLoader classLoader = GameController.class.getClassLoader();
	    File classpathRoot = new File(classLoader.getResource("").getPath());

	    String a = classpathRoot.getPath();
	    
	    System.out.println(a);
	}
	
}
