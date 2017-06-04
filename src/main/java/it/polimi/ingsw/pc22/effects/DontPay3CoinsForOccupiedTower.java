package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public class DontPay3CoinsForOccupiedTower extends Effect {

	public DontPay3CoinsForOccupiedTower(GameBoard gameBoard) {
		super(gameBoard);
		
	}

	@Override
	protected boolean isLegal(Player player) {
		return true;
	}

	@Override
	public void executeEffect(Player player) {
	
		player.setDontPayThreeCoinsInTowers(true);
		
	}

}
