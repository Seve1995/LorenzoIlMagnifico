package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public class PickOneCouncilPrivilege extends Effect{

	private Asset choosenAsset;

	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) {
		
		return true;
	}

	@Override
	public void executeEffect(Player player, GameBoard gameBoard)
	{
		
		player.addAsset(choosenAsset);
		
	}
	
}
