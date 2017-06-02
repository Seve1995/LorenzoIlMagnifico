package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.player.Player;

public class AddServantsHandlerMalus implements Effect{

	@Override
	public boolean isLegal(Player player) {
		return true;
	}

	@Override
	public void executeEffect(Player player) {
		
		player.setServantMalus(true);
		
	}

}
