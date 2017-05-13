package it.polimi.ingsw.pc22.gamebox;

import java.util.Random;

public class Dice {
	private int number;
	private ColorsEnum color;
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public ColorsEnum getColor() {
		return color;
	}
	
	public void setColor(ColorsEnum color) {
		this.color = color;
	}
	
	public void rollingDice() {
		Random random = new Random();
		this.number = random.nextInt(5) + 1;
	}
	
}
