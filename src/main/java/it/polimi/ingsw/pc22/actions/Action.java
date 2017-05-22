package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.player.Player;

public abstract class Action 
{
	private FamilyMember familyMember;
	
	public Action(FamilyMember familyMember) {
		this.familyMember = familyMember;
	}
		
	public FamilyMember getFamilyMember() {
		return familyMember;
	}

	public void setFamilyMember(FamilyMember familyMember) {
		this.familyMember = familyMember;
	}



	public abstract boolean isLegal(Player player);
	public abstract void executeAction(Player player);
}
