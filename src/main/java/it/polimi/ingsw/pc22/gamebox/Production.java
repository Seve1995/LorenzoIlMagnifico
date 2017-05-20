package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.player.Player;

public class Production {
	private int productionMaxPlaces;
	private ProductionCell productionCell[];
	
	public int getProductionMaxPlaces() {
		return productionMaxPlaces;
	}

	public void setProductionMaxPlaces(int productionMaxPlaces) {
		this.productionMaxPlaces = productionMaxPlaces;
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
