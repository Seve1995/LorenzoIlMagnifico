package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.player.Player;

public abstract class Effect 
{
	private Player player;
	
	public Effect(Player player) 
	{
		this.player = player;
	}
	
	
	public Player getPlayer() {
		return player;
	}


	public void setPlayer(Player player) {
		this.player = player;
	}

	public abstract boolean isLegal(Player player);
	public abstract void executeAction(Player player);
}
