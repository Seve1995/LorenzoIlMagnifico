package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.player.Player;

import java.io.Serializable;
import java.util.List;

/**
 * This class represents the basic cell of the game.
 * It has a certain required dice value that specifies the
 * minimum value for the activation of the effects that the 
 * cell contains.
 * In the attribute "familyMember" it stores the player's family
 * member that was placed in. 
 */

public class Cell implements Serializable
{
	private int requiredDiceValue;
	private FamilyMember familyMember;

	private List<Effect> effects;
	
	public Cell(int requiredDiceValue, List<Effect> effects) {
		this.requiredDiceValue = requiredDiceValue;
		this.effects = effects;
		this.familyMember = null;
	}

	public Cell() {}

	public boolean executeEffects(Player player, GameBoard gameBoard)
	{
		for(Effect e : this.getEffects())
		{
			if (!e.executeEffects(player, gameBoard))
				return false;
		}

		return true;
	}

	public int getRequiredDiceValue() {
		return requiredDiceValue;
	}
	
	public void setRequiredDiceValue(int requiredDiceValue) { 
		this.requiredDiceValue = requiredDiceValue;
	}

	public List<Effect> getEffects() {
		return effects;
	}

	public void setEffects(List<Effect> effects) {
		this.effects = effects;
	}

	public FamilyMember getFamilyMember() {
		return familyMember;
	}
	
	public void setFamilyMember(FamilyMember familyMember) {
		this.familyMember = familyMember;
	}
	
	public void removeFamilyMember(FamilyMember familyMember) {
		this.familyMember = null;
	}
	
	public boolean isEmpty()
	{
		if (this.familyMember==null)
			return true;
		else
			return false;
	} 
}
