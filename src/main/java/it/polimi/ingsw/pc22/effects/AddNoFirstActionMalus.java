package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

/**
 * This class represents the malus added by an excommunication cards,
 * that consists in skipping the first turn of the player.
 */	

public class AddNoFirstActionMalus implements Effect{

	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) {
		return true;
	}

	@Override
	public boolean executeEffects(Player player, GameBoard gameBoard) 
	{
		player.setNoFirstAction(true);
		
		return true;
}

}
