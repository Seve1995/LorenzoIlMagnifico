package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

/**
 * This effect will increase the victory points
 * of the player by the value specified in asset
 */	

public class AddEndGameVictoryPoints implements Effect{

	private Asset asset;

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) 
	{
		return true;
	}

	@Override
	public boolean executeEffects(Player player, GameBoard gameBoard) {
	
		player.setEndGameVictoryPoints(player.getEndGameVictoryPoints()+asset.getValue());

		return true;
		
	}
	
}