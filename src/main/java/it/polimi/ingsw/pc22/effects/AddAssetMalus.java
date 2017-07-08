package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

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
