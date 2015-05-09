package controllers;

import java.io.File;

import org.apache.commons.logging.Log;



import play.api.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import at.ac.tuwien.big.we15.lab2.api.*; 
import at.ac.tuwien.big.we15.lab2.api.impl.*;

public class GameController{
	
	
	
	public JeopardyGame startGame(String username, String avatarId){
		JeopardyFactory factory = new PlayJeopardyFactory("data.en.json"); 
		User user = new SimpleUser();
		user.setName(username);
		user.setAvatar(Avatar.getAvatar(avatarId));
		//play.api.Logger.debug("Attempting risky calculation.");
		JeopardyGame game = factory.createGame("user"); 
		//JeopardyGame game = new SimpleJeopardyGame(factory);

		return game;
		
	}
	
	public void getCleanPath() {
	    ClassLoader classLoader = GameController.class.getClassLoader();
	    File classpathRoot = new File(classLoader.getResource("").getPath());

	    String a = classpathRoot.getPath();
	    
	    System.out.println(a);
	}
	
}
