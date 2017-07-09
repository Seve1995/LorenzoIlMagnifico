package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

/**
 * This class represents the malus added by some excommunication cards,
 * that consists in receive 1 fewer asset every time the player
 * gain one asset of the same type of assetMalus 
 */	

public class AddAssetMalus implements Effect{

	private Asset assetMalus;

	public Asset getAsset()
	{
		return assetMalus;
	}

	public void setAsset(Asset asset)
	{
		this.assetMalus = asset;
	}

	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) {
		
		return true;
		
	}

	@Override
	public boolean executeEffects(Player player, GameBoard gameBoard) {
		
		switch (assetMalus.getType()) {
		
			case MILITARYPOINT:
				player.setMilitaryPointsMalus(true);
				break;
				
			case COIN:
				player.setCoinMalus(true);
				break;
				
			case SERVANT:
				player.setServantMalus(true);
				break;
				
			case STONE:
				player.setStoneMalus(true);
				break;
				
			case WOOD:
				player.setWoodMalus(true);
				break;
				
			default:
				break;
				
			}
		return true;
	}

}
