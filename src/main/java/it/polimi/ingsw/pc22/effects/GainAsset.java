package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.player.Player;

public class GainAsset extends Effect{
	private Asset asset;

	public GainAsset(Asset asset) 
	{
		this.asset = asset;
	}

	@Override
	public String toString() {
		return asset.getValue() + " " + asset.getType();
	}

	@Override
	public boolean isLegal(Player player) {
		return true;
	}

	@Override
	public void executeAction(Player player) {
		
	}
	
	
}
