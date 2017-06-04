package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public abstract class Effect
{
	protected GameBoard gameBoard;

	public Effect (GameBoard gameBoard)
	{
		this.gameBoard = gameBoard;
	}
	
	
	public GameBoard getGameBoard() {
		return gameBoard;
	}

	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}
	
	protected abstract boolean isLegal(Player player); 
	public abstract void executeEffect(Player player);
}
