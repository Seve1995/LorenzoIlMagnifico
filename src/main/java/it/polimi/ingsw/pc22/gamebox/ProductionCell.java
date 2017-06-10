package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.player.Player;

public class ProductionCell extends Cell
{
	private boolean isABlockedCell=false;
	private boolean isAPenaltyCell=false;
	
	public ProductionCell(int requiredDiceValue)
	{
		super(requiredDiceValue, null);
	}
	
	@Override
	public void executeEffects(Player player) {
		// TODO Auto-generated method stub
	}

	public boolean isABlockedCell() {
		return isABlockedCell;
	}

	public void setABlockedCell(boolean ABlockedCell) {
		isABlockedCell = ABlockedCell;
	}

	public boolean isAPenaltyCell() {
		return isAPenaltyCell;
	}

	public void setAPenaltyCell(boolean APenaltyCell) {
		isAPenaltyCell = APenaltyCell;
	}
}
