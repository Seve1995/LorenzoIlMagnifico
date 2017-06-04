package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public class DoubleYourBonuses extends Effect{

	public DoubleYourBonuses(GameBoard gameBoard) {
		super(gameBoard);
	}

	@Override
	public boolean isLegal(Player player) {
		return true;
	}

	@Override
	public void executeEffect(Player player) {
		
		player.setSantaRita(true);
		
	}
	

}
