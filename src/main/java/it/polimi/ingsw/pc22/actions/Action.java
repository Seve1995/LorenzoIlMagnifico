package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public abstract class Action 
{
	public abstract boolean isLegal(Player player, GameBoard gameBoard);
	public abstract void executeAction(Player player, GameBoard gameBoard);
}
