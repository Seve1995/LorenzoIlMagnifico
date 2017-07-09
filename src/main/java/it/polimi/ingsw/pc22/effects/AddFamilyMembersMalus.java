package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

/**
 * This class represents the malus added by some excommunication cards,
 * that consists in receiving a certain diceValueMalus reduction of the 
 * family members' value each time the player place them 
 */	

public class AddFamilyMembersMalus implements Effect{

	private int diceValueMalus;
	
	public int getDiceValueMalus() {
		return diceValueMalus;
	}

	public void setDiceValueMalus(int diceValueMalus) {
		this.diceValueMalus = diceValueMalus;
	}

	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) 
	{
		return true;
	}

	@Override
	public boolean executeEffects(Player player, GameBoard gameBoard) 
	{
		for(FamilyMember f : player.getFamilyMembers())
			f.setValueModifier(diceValueMalus);
		
		return true;
	}

}
