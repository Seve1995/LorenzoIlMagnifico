package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.player.Player;

public class MarketCell extends Cell {
	private boolean isABlockedCell=false;
	
	public MarketCell(int requiredDiceValue) {
		super(requiredDiceValue);
	}
	
	public void setAsBlockedCell() {
		this.isABlockedCell=true;
	}
	
	@Override
	public void executeEffect(Player player) {
		// TODO Auto-generated method stub	
	}
	

}
