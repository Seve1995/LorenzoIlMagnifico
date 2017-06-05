package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public interface Effect
{
	boolean isLegal(Player player, GameBoard gameBoard); 
	void executeEffect(Player player, GameBoard gameBoard);
}
