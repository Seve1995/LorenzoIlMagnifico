package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

/**
 * Using this effect a player can set Territories in his/her player board,
 * with no care about military-points-constrain
 */

public class NoMilitaryPointsForTerritories implements Effect {

	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) {
		return true;
	}

	@Override
	public boolean executeEffects(Player player, GameBoard gameBoard) {
		
		player.setNoMilitaryPointsForTerritories(true);
		return true;
		
	}
	

}
