package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

/**
 * The activation of this effect will add to a certain player
 * the asset specified in the private attribute "Asset"
 */

public class AddAsset implements Effect
{
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
	public String toString()
	{
		return this.getAsset().toString();
	}

	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) {
		return true;
	}

	@Override
	public boolean executeEffects(Player player, GameBoard gameBoard) 
	{
		player.addAsset(asset);
		
		return true;
	}
	
}	