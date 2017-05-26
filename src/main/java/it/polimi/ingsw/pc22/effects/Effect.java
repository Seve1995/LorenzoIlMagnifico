package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.player.Player;

public interface Effect
{
	boolean isLegal(Player player);
	void executeEffect(Player player);
}
