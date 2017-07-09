package it.polimi.ingsw.pc22.gamebox;

import java.io.Serializable;
import java.util.Random;

/**
 * This class represents the dice of the game box. Every dice has a
 * color (specified in the ColorsEnum enumeration) and a value.
 * The method rollingDice generates a random number between 1 and 6.
 */

public class Dice implements Serializable
{
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
		this.diceValue = random.nextInt(6) + 1;
	}
	
}
