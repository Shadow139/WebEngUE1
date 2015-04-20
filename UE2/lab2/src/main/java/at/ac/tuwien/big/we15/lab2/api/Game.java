package at.ac.tuwien.big.we15.lab2.api;

import java.util.List;

public interface Game {

	public Player getPlayer1();
	public void setPlayer1(Player player1);
	public Player getPlayer2();
	public void setPlayer2(Player player2);
	public Player getCurrentPlayer();
	public void setCurrentPlayer(Player currentPlayer);
	public List<Category> getCategoryList();
	public void setCategoryList(List<Category> categoryList);
	public int getQuestionsAsked();
	public void setQuestionsAsked(int questionsAsked);
	public void increaseQuestionsAskedCount();
	public Player getWinner();
	public Player getLooser();
}
