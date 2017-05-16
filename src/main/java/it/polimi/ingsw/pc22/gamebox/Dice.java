package it.polimi.ingsw.pc22.gamebox;

import java.util.Random;

public class Dice {
	private int diceValue;
	private ColorsEnum color;
	
	public Dice(ColorsEnum color) {
		this.color = color;
	}
	
	public int getNumber() {
		return diceValue;
	}
	
	public ColorsEnum getColor() {
		return color;
	}
	
	public void setColor(ColorsEnum color) {
		this.color = color;
	}
	
	public void rollingDice() {
		Random random = new Random();
		this.diceValue = random.nextInt(5) + 1;
	}
	
}
