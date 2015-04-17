package at.ac.tuwien.big.we15.lab2.api;

public interface Player {

	public String getName();
	public void setName(String name);
	public int getWinnings();
	public void setWinnings(int winnings);
	public void increaseWinnings(int amount);
	public void decreaseWinnings(int amount);
}
