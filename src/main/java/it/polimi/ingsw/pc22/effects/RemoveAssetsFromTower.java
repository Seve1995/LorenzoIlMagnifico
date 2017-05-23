package it.polimi.ingsw.pc22.effects;
import it.polimi.ingsw.pc22.player.Player;

/*
 * EFFECT DESCRIPTION:
 * You don't take the bonuses when you take a Development Card from the third
 * and the fourth floor or  the towers (through a Family Member or as an 
 * effect of another card).
 */

public class RemoveAssetsFromTower extends Effect{

	@Override
	public void executeAction(Player player) {
		player.setRemoveTowerBonus(true);
	}

	@Override
	public boolean isLegal(Player player) {
		// Always legal, it doesn't have requirements
		return true;
	}
	
}
