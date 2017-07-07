package it.polimi.ingsw.pc22.gamebox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the market zone;
 * as for harvest and production it is composed by an array list
 * of cells.
 */

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

}
