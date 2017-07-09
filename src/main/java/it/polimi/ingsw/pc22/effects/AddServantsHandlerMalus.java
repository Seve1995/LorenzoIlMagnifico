package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

/**
 * This class represents the malus added by an excommunication cards.
 * Since its activation, the player will have to spend 2 servants to
 * increase his/her action value by 1.
 */	

public class AddServantsHandlerMalus implements Effect{

	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) {
		return true;
	}

	@Override
	public boolean executeEffects(Player player, GameBoard gameBoard) 
	{
			player.setServantMalus(true);
			
			return true;
	}

}
