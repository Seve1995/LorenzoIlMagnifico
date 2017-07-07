package it.polimi.ingsw.pc22.gamebox;


/**
 * Please refer to harvest-cell class.
 */


public class ProductionCell extends Cell
{
	private boolean isABlockedCell=false;
	private boolean isAPenaltyCell=false;
	
	public ProductionCell(int requiredDiceValue)
	{
		super(requiredDiceValue, null);
	}

    public ProductionCell() {

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
