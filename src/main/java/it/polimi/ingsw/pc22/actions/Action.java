package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.player.Player;

public abstract class Action 
{
	public abstract boolean isLegal(Player player);
	
	public abstract void executeAction(Player player);
}
