package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.player.Player;

public class HarvestCell extends Cell {
	private boolean isABlockedCell=false;
	private boolean isAPenaltyCell=false;

	public HarvestCell(int requiredDiceValue) {
		super(requiredDiceValue);
	}
	@Override
	public void executeEffect(Player player) {
		// TODO Auto-generated method stub	
	}
	
	public void setAsBlockedCell() {
		this.isABlockedCell=true;
	}

	public void setAsPenaltyCell() {
		this.isAPenaltyCell=true;
	}
}
