package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.player.Player;

public class Production
{
	private ProductionCell[] productionCells;

	public Production(ProductionCell[] productionCell)
	{
		this.productionCells = productionCell;
	}

	public ProductionCell[] getProductionCell() {
		return productionCells;
	}

	public void setProductionCell(ProductionCell[] productionCell) {
		this.productionCells = productionCell;
	}
	
	public int firstCellFree() {
		int i=0;
		while ( i < this.productionCells.length)
		{
			if (!(productionCells[i].isEmpty()))
			{
				i++;
			}
		}
		return i;
	}
	
	public String toString() {
		String output = "PRODUCTION\n";
		if (firstCellFree()!=0)
			{
			 output += "FamilyMembers already in production area:\n";
			for (int i=0; i<firstCellFree(); i++)
				output += i + ") " + productionCells[i].getFamilyMember().toString();
			}
		return output;
	}
	
	public String gainInfo() {
		String output = "Production activates the corresponding personal bonus and the" +
				"permanent effects of all Buildings on your Personal Board, but only of those Buildings" +
				"that have a value equal to or lower than your Production action value.";
		return output;
	}
	
}
