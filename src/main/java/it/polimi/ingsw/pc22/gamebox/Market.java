package it.polimi.ingsw.pc22.gamebox;

import java.util.ArrayList;
import java.util.List;

public class Market {
	
	private List<MarketCell> marketCells = new ArrayList<>();
	
	public Market(List<MarketCell> marketCells) {
		this.marketCells = marketCells;
	}
	public List<MarketCell> getMarketCells() {
		return marketCells;
	}

	public void setMarketCell(List<MarketCell> marketCells) {
		this.marketCells = marketCells;
	}

	public String toString() {
		String output = "MARKET\n";
		for (int i=0; i<marketCells.size(); i++) 
		{
			if (!marketCells.get(i).isABlockedCell())
				output += "Zone " + i + " : " + marketCells.get(i).toString().toLowerCase() + "\n";
		}		
		return output;
	}
	
	public String gainInfo() {
		String output = "There are 4 action spaces in the Market."
				+ "Each space may contain only 1 Family Member."
				+ "There can be any number of Family Members of the same color in the market area." 
				+ "Every action space has a different effect.";
		return output;
	}
		
	/*
  	public static void main(String[] args) {
		Asset asset = new Asset(3, AssetType.COIN);
		Asset asset2 = new Asset(3, AssetType.FAITHPOINT);
		List<Effect> effects = new ArrayList<Effect>();
		AddAsset add1= new AddAsset();
		add1.setAsset(asset);
		effects.add(add1);
		AddAsset add2= new AddAsset();
		add2.setAsset(asset);
		effects.add(add2);
		List<Effect> effects2 = new ArrayList<Effect>();
		effects2.add(add1);
		List<MarketCell> marketCells = new ArrayList<MarketCell>();
		marketCells.add(new MarketCell(3, effects));
		marketCells.add(new MarketCell(5, effects2));
		marketCells.get(1).setABlockedCell(true);
		Market market = new Market(marketCells);
		System.out.print(market.toString());
  	}
  	*/
}
