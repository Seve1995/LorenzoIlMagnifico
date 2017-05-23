package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.player.Player;

public class GainAsset extends Effect
{
	@Override
	public String toString()
	{
		return this.getAsset().getValue() + " " + this.getAsset().getType();
	}

	@Override
	public boolean isLegal(Player player) {
		return true;
	}

	@Override
	public void executeAction(Player player) {
		
	}
	
	
}
