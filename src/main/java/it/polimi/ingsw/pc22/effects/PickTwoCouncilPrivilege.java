package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.AssetType;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public class PickTwoCouncilPrivilege implements Effect{

	private Asset choosenAsset1;
	private Asset choosenAsset2;
	
	

	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) 
	{
		if (choosenAsset1.getType().equals(choosenAsset2.getType()))
		{
			return false;
		}
		return true;
	}

	@Override
	public boolean executeEffects(Player player, GameBoard gameBoard)
	{
		this.choosenAsset1.setType(AssetType.COIN);
		this.choosenAsset1.setValue(0);
		if (isLegal(player, gameBoard))
		{
			player.addAsset(choosenAsset1);
			player.addAsset(choosenAsset2);
			return true;
		}
		return false;
		
	}

	@Override
	public String toString() {
		return "Pick Two Council Privilege";
	}
}
