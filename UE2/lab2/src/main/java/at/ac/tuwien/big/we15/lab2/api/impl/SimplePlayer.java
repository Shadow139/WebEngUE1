package at.ac.tuwien.big.we15.lab2.api.impl;

import java.io.Serializable;

import at.ac.tuwien.big.we15.lab2.api.Player;

public class SimplePlayer implements Player,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3749160645010618523L;
	private String name;
	private int winnings;

	
	
	public SimplePlayer(String name) {
		super();
		this.name = name;
		this.setWinnings(0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWinnings() {
		return winnings;
	}

	public void setWinnings(int winnings) {
		this.winnings = winnings;
	}
	

}
