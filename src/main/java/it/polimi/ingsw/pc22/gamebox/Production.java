package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.player.Player;

public class Production
{
	private ProductionCell productionCell[];

	public Production(ProductionCell[] productionCell)
	{
		this.productionCell = productionCell;
	}

	public ProductionCell[] getProductionCell() {
		return productionCell;
	}

	public void setProductionCell(ProductionCell[] productionCell) {
		this.productionCell = productionCell;
	}

	public String gainInfo() {
		String output = "Production activates the corresponding personal bonus and the" +
				"permanent effects of all Buildings on your Personal Board, but only of those Buildings" +
				"that have a value equal to or lower than your Production action value.";
		return output;
	}

	
	public int firstCellFree() {
		int i=0;
		while ( i < this.productionCell.length)
		{
			if (!(productionCell[i].isEmpty()))
			{
				i++;
			}
		}
		return i;
	}

}
