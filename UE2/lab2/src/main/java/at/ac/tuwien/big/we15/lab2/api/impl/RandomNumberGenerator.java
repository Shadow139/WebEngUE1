package at.ac.tuwien.big.we15.lab2.api.impl;

import java.util.Random;


public class RandomNumberGenerator {
	private static Random rand = new Random();

	public static int getRandIntBetween(int lowerBound, int upperBound) {
		return rand.nextInt(upperBound - lowerBound) + lowerBound;
	}

	public static int getRandInt(int upperBound) {
		return rand.nextInt(upperBound);
	}
}
