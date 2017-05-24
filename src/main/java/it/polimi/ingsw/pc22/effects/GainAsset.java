package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.player.Player;

public class GainAsset implements Effect
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
	public boolean isLegal(Player player) {
		return true;
	}

	@Override
	public void executeAction(Player player) {
		
	}
	
	
}
