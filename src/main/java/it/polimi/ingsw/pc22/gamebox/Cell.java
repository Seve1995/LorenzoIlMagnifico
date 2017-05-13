package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.player.Player;

public abstract class Cell {
	private int requiredDiceValue;
	private FamilyMember familyMember;
	
	public abstract void executeEffect(Player player);
	
	public int getRequiredDiceValue() {
		return requiredDiceValue;
	}
	public void setRequiredDiceValue(int requiredDiceValue) {
		this.requiredDiceValue = requiredDiceValue;
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
