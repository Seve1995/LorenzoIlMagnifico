package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.player.Player;

public class Harvest {
	private int harvestMaxPlaces;
	private HarvestCell harvestCell[]; 
	
	public int getharvestMaxPlaces() {
		return harvestMaxPlaces;
	}

	public void setharvestMaxPlaces(int harvestMaxPlaces) {
		this.harvestMaxPlaces = harvestMaxPlaces;
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
