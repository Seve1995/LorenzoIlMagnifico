package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

public abstract class Cell
{
	private int requiredDiceValue;
	private FamilyMember familyMember;

	private List<Effect> effects;
	
	public Cell(int requiredDiceValue, List<Effect> effects) {
		this.requiredDiceValue = requiredDiceValue;
		this.effects = effects;
		this.familyMember = null;
	}
	
	public abstract void executeEffect(Player player);

	public int getRequiredDiceValue() {
		return requiredDiceValue;
	}
	
	public void setRequiredDiceValue(int requiredDiceValue) { 
	//Is this necessary? Should requiredDiceValue be final?
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
	
	public boolean isEmpty() {
		if (this.familyMember==null)
			return true;
		else
			return false;
	} 
}
