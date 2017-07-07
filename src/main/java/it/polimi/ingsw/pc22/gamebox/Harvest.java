package it.polimi.ingsw.pc22.gamebox;

import java.io.Serializable;

/**
 * This is the implementation of the Harvest Place.
 * It is composed by an array of 16 cells (the greatest number of familiars
 * that can be placed here, playing a game of 4-5 players)
 */

public class Harvest implements Serializable
{
	private HarvestCell[] harvestCells = new HarvestCell[16];

	public Harvest(HarvestCell[] harvestCells)
	{
		this.harvestCells = harvestCells;
	}

	public Harvest() {
	}

	public HarvestCell[] getHarvestCell() {
		return harvestCells;
	}

	public void setHarvestCell(HarvestCell[] harvestCells) {
		this.harvestCells = harvestCells;
	}
	

	public int firstCellFree()
	{
		int i=0;

		while ( i < this.harvestCells.length)
		{
			if ((harvestCells[i].isEmpty()))
			{
				return i;
			}
			i++;
		}
		return -1;
	}

	public String toString() {
		StringBuilder output = new StringBuilder("HARVEST\n" 
						+"FamiliarMembers already in harvest area:\n");
		for (int i=0; i<firstCellFree(); i++)
			output.append(i + ") " + harvestCells[i].getFamilyMember().toString());
		if (firstCellFree()>0 && firstCellFree()%4==0) output.append("\n");
		return output.toString();
	}

}
