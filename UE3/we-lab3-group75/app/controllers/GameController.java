package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import at.ac.tuwien.big.we15.lab2.api.*; 
import at.ac.tuwien.big.we15.lab2.api.impl.PlayJeopardyFactory;

public class GameController{
	
	public void startGame(String username, String avatarId){
		JeopardyFactory factory = new PlayJeopardyFactory("conf/data.de.json"); 
		JeopardyGame game = factory.createGame(user); 
		Player human = game.getHumanPlayer(); // get human player 
	
		int i = human.getProfit(); // get current profit of player 
		int is = human.getLatestProfitChange(); // get last profit change (for display) 
		game.chooseHumanQuestion(2); // choose next human question 
		game.answerHumanQuestion(2); // answer current human questions 
		game.isRoundStart(); // check if we are at the beginning of a new round 
		game.isAnswerPending(); // check if the current question needs to be answered 
		game.isGameOver();   // check if game is over 
		game.getWinner(); // winner of round or null if no winner exists yet 
	}
	
    public static Result submitJeopardy(){
		
    	
    	return TODO;
    }
}
