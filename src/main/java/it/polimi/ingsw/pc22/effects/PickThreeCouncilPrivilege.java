package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public class PickThreeCouncilPrivilege implements Effect{

	private Asset choosenAsset1;
	private Asset choosenAsset2;
	private Asset choosenAsset3;
	
	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) 
	{
		if (!(choosenAsset1.getType().equals(choosenAsset2.getType())) && !(choosenAsset1.getType().equals(choosenAsset3.getType())) 
				&& !(choosenAsset1.getType().equals(choosenAsset2.getType())))
		{
			return true;
		}
		
		return false;
	}

	@Override
	public void executeEffect(Player player, GameBoard gameBoard) 
	{
		if (isLegal(player, gameBoard))
		{
			player.addAsset(choosenAsset1);
			player.addAsset(choosenAsset2);
			player.addAsset(choosenAsset3);

		}
	}
	
	@Override
	public String toString() {
		return "Pick Three Council Privilege";
	}
}
