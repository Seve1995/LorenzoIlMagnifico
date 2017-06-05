package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public class AddAssetMalus implements Effect{

	private Asset asset;

	public Asset getAsset()
	{
		return asset;
	}

	public void setAsset(Asset asset)
	{
		this.asset = asset;
	}

	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) {
		
		return true;
		
	}

	@Override
	public void executeEffect(Player player, GameBoard gameBoard) {
		
		if (isLegal(player,gameBoard))
			player.addAsset(asset);
		
	}

}
