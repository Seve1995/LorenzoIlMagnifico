package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

/**
 * This effect, when activated, will make possible for
 * the player to not spend 3 coins when he/she places a family
 * member in a tower already occupied. 
 * This is a Leader Card's effect.
 */

public class DontPay3CoinsForOccupiedTower implements Effect {

	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) {
		return true;
	}

	@Override
	public boolean executeEffects(Player player, GameBoard gameBoard) 
	{
		player.setDontPayThreeCoinsInTowers(true);
		
		return true;
	}

}
