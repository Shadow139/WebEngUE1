package models;

import at.ac.tuwien.big.we15.lab2.api.JeopardyGame;

public class Game{
	JeopardyGame game;
	public Game(JeopardyGame jgame){
		game = jgame;
	}
	public JeopardyGame getGame() {
		return game;
	}
	public void setGame(JeopardyGame game) {
		this.game = game;
	}
	
}
