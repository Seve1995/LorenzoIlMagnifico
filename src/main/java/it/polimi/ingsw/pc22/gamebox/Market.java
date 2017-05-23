package it.polimi.ingsw.pc22.gamebox;

import java.util.Arrays;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.effects.GainAsset;
import it.polimi.ingsw.pc22.player.Player;

public class Market {
	
	private MarketCell[] marketCells;
	
	public Market(MarketCell[] marketCells) {
		this.marketCells = marketCells;
	}
	public MarketCell[] getMarketCell() {
		return marketCells;
	}

	public void setMarketCell(MarketCell[] marketCell) {
		this.marketCells = marketCell;
	}

	public void ExecuteEffect (Player player) {
	}

	@Override
	public String toString() {
		String output = "";
		for (int i=0; i<marketCells.length; i++) 
		{
			if (!marketCells[i].isABlockedCell())
				output += "On cell " + (i+1) + " " + marketCells[i].toString().toLowerCase() + "\n";
		}
		return output;
	}
	
	/*
  	public static void main(String[] args) {
		Asset asset = new Asset(3, AssetType.COIN);
		Asset asset2 = new Asset(3, AssetType.FAITHPOINT);
		Effect[] effect = new Effect[2];
		effect[0] = new GainAsset(null, asset);
		effect[1] = new GainAsset(null, asset2);
		Effect[] effect2 = new Effect[1];
		effect2[0] = new GainAsset(null, asset);
		MarketCell[] marketCells = new MarketCell[2];
		marketCells[0] = new MarketCell(3, effect);
		marketCells[1] = new MarketCell(5, effect2);
		Market market = new Market(marketCells);
		System.out.print(market.toString());
  	}
  	*/
}
