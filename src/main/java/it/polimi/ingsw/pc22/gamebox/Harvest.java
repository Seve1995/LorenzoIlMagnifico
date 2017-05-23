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

	public void ExecuteEffect(Player player)
	{
		//TODO: implement the execution effect for this kind of cells
	}

}
