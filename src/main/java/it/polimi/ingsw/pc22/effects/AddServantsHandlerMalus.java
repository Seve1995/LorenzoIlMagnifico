package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public class AddServantsHandlerMalus implements Effect{

	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) {
		return true;
	}

	@Override
	public boolean executeEffects(Player player, GameBoard gameBoard) {
		
		if (isLegal(player,gameBoard))
		{
			player.setServantMalus(true);
			return true;
		}
		
		return false;
		
	}

}
