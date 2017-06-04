package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public abstract class Action 
{
	protected FamilyMember familyMember;
	protected GameBoard gameBoard;
	
	
	public Action(){}

	public Action(FamilyMember familyMember) {
		this.familyMember = familyMember;
	}
		
	public FamilyMember getFamilyMember() {
		return familyMember;
	}

	public void setFamilyMember(FamilyMember familyMember) {
		this.familyMember = familyMember;
	}

	protected abstract boolean isLegal(Player player);

	public abstract boolean executeAction(Player player);
}
