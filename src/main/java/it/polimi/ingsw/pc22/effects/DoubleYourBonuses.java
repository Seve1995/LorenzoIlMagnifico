package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

/**
 * This effect, when activated, will make possible for
 * the player to receive the resources twice eache time he/she
 * receive wood, stone, coins, or servants as an 
 * immediate effect from Development Card.
 * This is a Leader Card's effect.
 */

public class DoubleYourBonuses implements Effect{

	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) {
		return true;
	}

	@Override
	public boolean executeEffects(Player player, GameBoard gameBoard) 
	{
		player.setSantaRita(true);
		
		return true;
	}
	

}
