package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public class AddNoFirstActionMalus extends Effect{

	public AddNoFirstActionMalus(GameBoard gameBoard) {
		super(gameBoard);
		
	}

	@Override
	public boolean isLegal(Player player) {
		return true;
	}

	@Override
	public void executeEffect(Player player) {
		
		player.setNoFirstAction(true);
		
	}

}
