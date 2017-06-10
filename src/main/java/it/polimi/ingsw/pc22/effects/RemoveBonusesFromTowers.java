package it.polimi.ingsw.pc22.effects;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

/*
 * EFFECT DESCRIPTION:
 * You don't take the bonuses when you take a Development Card from the third
 * and the fourth floor or  the towers (through a Family Member or as an 
 * effect of another card).
 */

public class RemoveBonusesFromTowers implements Effect
{

	@Override
	public boolean executeEffects(Player player, GameBoard gameBoard)
	{
		player.setRemoveTowerBonus(true);
		return true;
	}

	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) 
	{
		return true;
	}

	@Override
	public String toString() {
		return "RemoveBonusesFromTowers []";
	}
	
	
}
