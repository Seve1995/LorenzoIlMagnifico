package it.polimi.ingsw.pc22.gamebox;

import java.util.List;
import it.polimi.ingsw.pc22.player.Player;

public class Market {
	
	private List<MarketCell> marketCells;
	
	public Market(List<MarketCell> marketCells) {
		this.marketCells = marketCells;
	}
	public List<MarketCell> getMarketCells() {
		return marketCells;
	}

	public void setMarketCell(List<MarketCell> marketCells) {
		this.marketCells = marketCells;
	}

	public void ExecuteEffect (Player player) {
	}

	@Override
	public String toString() {
		String output = "";
		for (int i=0; i<marketCells.size(); i++) 
		{
			if (!marketCells.get(i).isABlockedCell())
				output += "On cell " + (i+1) + " " + marketCells.get(i).toString().toLowerCase() + "\n";
		}
		return output;
	}
	
	/*
  	public static void main(String[] args) {
		Asset asset = new Asset(3, AssetType.COIN);
		Asset asset2 = new Asset(3, AssetType.FAITHPOINT);
		List<Effect> effects = new ArrayList<Effect>();
		effects.add(new GainAsset(asset));
		effects.add(new GainAsset(asset2));
		List<Effect> effects2 = new ArrayList<Effect>();
		effects2.add(new GainAsset(asset));
		List<MarketCell> marketCells = new ArrayList<MarketCell>();
		marketCells.add(new MarketCell(3, effects));
		marketCells.add(new MarketCell(5, effects2));
		marketCells.get(1).setABlockedCell(true);
		Market market = new Market(marketCells);
		System.out.print(market.toString());
  	}
  	*/
}
