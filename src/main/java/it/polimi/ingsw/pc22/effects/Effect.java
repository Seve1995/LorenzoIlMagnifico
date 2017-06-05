package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public abstract class Effect
{
	protected abstract boolean isLegal(Player player, GameBoard gameBoard); 
	public abstract void executeEffect(Player player, GameBoard gameBoard);
}
