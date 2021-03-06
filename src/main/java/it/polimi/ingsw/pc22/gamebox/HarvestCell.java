package it.polimi.ingsw.pc22.gamebox;

/**
 * This is the implementation of the single cell;
 * it can be blocked or not (2-3 players-game)
 * it can be a penalty cell (from the 2th to the 16th cell)
 * in games composed by 3-5 players
 *
 */

public class HarvestCell extends Cell
{
	private boolean isABlockedCell=false;
	private boolean isAPenaltyCell=false;

	public HarvestCell(int requiredDiceValue)
	{
		super(requiredDiceValue, null);
	}

	public HarvestCell() {
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
