package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public abstract class Action 
{
	protected FamilyMember familyMember;

	private boolean familiarNeeded = true;

	public boolean isFamiliarNeeded() {
		return familiarNeeded;
	}

	public void setFamiliarNeeded(boolean familiarNeeded) {
		this.familiarNeeded = familiarNeeded;
	}

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

	protected abstract boolean isLegal(Player player, GameBoard gameBoard);

	public abstract boolean executeAction(Player player, GameBoard gameBoard);


}
