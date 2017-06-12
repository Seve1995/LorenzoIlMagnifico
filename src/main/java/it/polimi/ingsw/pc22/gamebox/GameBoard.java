package it.polimi.ingsw.pc22.gamebox;

import java.util.List;

public class GameBoard
{
	
	private Tower[] towers;
	private Harvest harvest;
	private Production production;
	private Market market;
	private CouncilPalace councilPalace;
	private List<Dice> dices;
	
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
		StringBuilder output = new StringBuilder("GAMEBOARD\n");
		for(Tower t : towers)
			output.append(t.toString() + "\n");
		output.append(harvest.toString());
		output.append(production.toString());
		output.append(market.toString());
		output.append(councilPalace.toString());
		return output.toString();
	}

}
