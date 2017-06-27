package it.polimi.ingsw.pc22.gamebox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Market implements Serializable {
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
		StringBuilder output = new StringBuilder("MARKET\n");
		for (int i = 0; i < marketCells.size(); i++)
		{
			MarketCell currCell = marketCells.get(i);

			if (!currCell.isABlockedCell() && currCell.getFamilyMember() == null)
				output.append("Zone " + i + " : " + marketCells.get(i).toString() + "\n");
		}
		return output.toString();
	}

	public String gainInfo() {
		String output = "There are 4 action spaces in the Market."
				+ "Each space may contain only 1 Family Member."
				+ "There can be any number of Family Members of the same color in the market area."
				+ "Every action space has a different effect.";
		return output;
	}
}
