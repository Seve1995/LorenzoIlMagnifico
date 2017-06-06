package it.polimi.ingsw.pc22.gamebox;

import java.util.List;

public class GameBoard
{
	
	Tower[] towers;
	Harvest harvest;
	Production production;
	Market market;
	CouncilPalace councilPalace;
	List<Dice> dices;
	
	public GameBoard() 
	{
		
	}

	public Tower[] getTowers() {
		return towers;
	}

	public void setTowers(Tower[] towers) {
		this.towers = towers;
	}

	public Harvest getHarvest() {
		return harvest;
	}

	public void setHarvest(Harvest harvest) {
		this.harvest = harvest;
	}

	public Production getProduction() {
		return production;
	}

	public void setProduction(Production production) {
		this.production = production;
	}

	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.market = market;
	}

	public CouncilPalace getCouncilPalace() {
		return councilPalace;
	}

	public void setCouncilPalace(CouncilPalace councilPalace) {
		this.councilPalace = councilPalace;
	}

	public List<Dice> getDices() {
		return dices;
	}

	public void setDices(List<Dice> dices) {
		this.dices = dices;
	}

	@Override
	public String toString()
	{
		String output = "GAMEBOARD\n";
		for(Tower t : towers)
			output+=t.toString() + "\n";
		output += harvest.toString();
		output += production.toString();
		output += market.toString();
		output += councilPalace.toString();
		return output;
	}

}
