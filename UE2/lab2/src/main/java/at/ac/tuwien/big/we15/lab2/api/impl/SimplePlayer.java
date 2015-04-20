package at.ac.tuwien.big.we15.lab2.api.impl;

import java.io.Serializable;

import at.ac.tuwien.big.we15.lab2.api.Player;
import at.ac.tuwien.big.we15.lab2.api.Avatar;


public class SimplePlayer implements Player,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3749160645010618523L;
	private String name;
	private Avatar avatar;
	private int winnings;

	
	
	public SimplePlayer() {
		this.avatar = avatar.getRandomAvatar();
		this.name = avatar.getName();
		this.setWinnings(0);
	}

	public SimplePlayer(String id) {
		this.avatar = avatar.getAvatar(id);
		this.name = avatar.getName();
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
	public void increaseWinnings(int amount) {
		this.winnings += amount;
	}

	public void decreaseWinnings(int amount) {
		this.winnings -= amount;
	}

	public Avatar getAvatar() {
		return avatar;
	}

	public void setAvatar(Avatar avatar) {
		this.avatar = avatar;
	}
	
	

}
