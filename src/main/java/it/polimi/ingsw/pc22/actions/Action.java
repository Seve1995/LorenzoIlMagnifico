package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

/**
 * Abstract class that represent all the actions in the game
 * every action need up to one familiar to be executed, but there are
 * also some actions that don't need it, that's the reason why we implemented
 * in this abstract class the familiar and the familiarNeeded boolean
 */
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

	public abstract boolean isLegal(Player player, GameBoard gameBoard);

	public abstract boolean executeAction(Player player, GameBoard gameBoard);
}
