package at.ac.tuwien.big.we15.lab2.api.impl;

import java.io.Serializable;
import java.util.List;

import at.ac.tuwien.big.we15.lab2.api.Category;
import at.ac.tuwien.big.we15.lab2.api.Game;
import at.ac.tuwien.big.we15.lab2.api.Player;

public class SimpleGame implements Game, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2588919783375566848L;
	private Player player1;
	private Player player2;

	private List<Category> categoryList;
	private int questionsAsked;
	
	public SimpleGame(Player p1, Player p2){
		this.player1 = p1;
		this.player2 = p2;
		this.questionsAsked = 0;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public int getQuestionsAsked() {
		return questionsAsked;
	}

	public void setQuestionsAsked(int questionsAsked) {
		this.questionsAsked = questionsAsked;
	}
	
	public void increaseQuestionsAskedCount(){
		this.questionsAsked++;
	}
	
	
	
}
