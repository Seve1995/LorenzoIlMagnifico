package it.polimi.ingsw.pc22.gamebox;

public class Harvest
{
	private HarvestCell[] harvestCells = new HarvestCell[16];

	public Harvest(HarvestCell[] harvestCells)
	{
		this.harvestCells = harvestCells;
	}

	public HarvestCell[] getHarvestCell() {
		return harvestCells;
	}

	public void setHarvestCell(HarvestCell[] harvestCells) {
		this.harvestCells = harvestCells;
	}
	

	public int firstCellFree() {
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
	
	public String gainInfo() {
		String output = "Harvest activates the corresponding personal bonus and the" +
				"permanent effects of all Territories on your Personal Board, but only of those Territories" +
				"that have a value equal to or lower than your Harvest action value.";
		return output;
	}
	
	public String toString() {
		String output = "HARVEST\n" 
						+"FamilyMembers already in harvest area:\n";
		for (int i=0; i<firstCellFree(); i++)
			output += i + ") " + harvestCells[i].getFamilyMember().toString();
		return output;
	}

}
