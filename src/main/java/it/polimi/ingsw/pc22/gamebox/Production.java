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
	
	public String gainInfo() {
		String output = "Production activates the corresponding personal bonus and the" +
				"permanent effects of all Buildings on your Personal Board, but only of those Buildings" +
				"that have a value equal to or lower than your Production action value.";
		return output;
	}

}
