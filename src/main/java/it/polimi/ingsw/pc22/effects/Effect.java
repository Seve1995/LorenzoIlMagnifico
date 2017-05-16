package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.player.Player;

public abstract class Effect 
{
	public abstract boolean isLegal(Player player);
	public abstract void executeAction(Player player);
}
