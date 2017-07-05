package it.polimi.ingsw.pc22.gamebox;

import java.io.Serializable;

public class Production implements Serializable
{
	private ProductionCell[] productionCells = new ProductionCell[16];

	public Production(ProductionCell[] productionCell)
	{
		this.productionCells = productionCell;
	}

	public Production() {

	}

	public ProductionCell[] getProductionCell() {
		return productionCells;
	}

	public void setProductionCell(ProductionCell[] productionCell) {
		this.productionCells = productionCell;
	}
	
	public int firstCellFree()
	{
		int i=0;
		while ( i < this.productionCells.length)
		{
			if ((productionCells[i].isEmpty()))
			{
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public String toString() {
		StringBuilder output = new StringBuilder("PRODUCTION\n"
						+ "FamilyMembers already in production area:\n");
		for (int i=0; i<firstCellFree(); i++)
			output.append(i + ") " + productionCells[i].getFamilyMember().toString());
		if (firstCellFree()>0 && firstCellFree()%4==0) output.append("\n");
		return output.toString();
	}
	
	public String gainInfo() {
		String output = "Production activates the corresponding personal bonus and the" +
				"permanent effects of all Buildings on your Personal Board, but only of those Buildings" +
				"that have a value equal to or lower than your Production action value.";
		return output;
	}
	
	
}
