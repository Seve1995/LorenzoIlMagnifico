package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.player.Player;

public class Harvest
{
	private HarvestCell harvestCell[];

	public Harvest(HarvestCell[] harvestCell)
	{
		this.harvestCell = harvestCell;
	}

	public HarvestCell[] getHarvestCell() {
		return harvestCell;
	}

	public void setHarvestCell(HarvestCell[] harvestCell) {
		this.harvestCell = harvestCell;
	}
	
	public String gainInfo() {
		String output = "Harvest activates the corresponding personal bonus and the" +
				"permanent effects of all Territories on your Personal Board, but only of those Territories" +
				"that have a value equal to or lower than your Harvest action value.";
		return output;
	}
	
	public int firstCellFree() {
		int i=0;
		while ( i < this.harvestCell.length)
		{
			if (!(harvestCell[i].isEmpty()))
			{
				i++;
			}
		}
		return i;
	}

}
