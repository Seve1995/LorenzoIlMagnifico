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

	public void executeEffect(Player p){
		//TODO: implement this kind of method
	} 
	
}
