package it.polimi.ingsw.pc22.gamebox;

import java.util.Arrays;
import java.util.List;

public class GameBoard 
{
	
	Tower[] tower;
	Harvest harvest;
	Production production;
	Market market;
	CouncilPalace councilPalace;
	Dice[] dice;
	
	public GameBoard() 
	{
		
	}

	
	

	public Tower[] getTower() {
		return tower;
	}

	public void setTower(Tower[] tower) {
		this.tower = tower;
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

	public Dice[] getDice() {
		return dice;
	}

	public void setDice(Dice[] dice) {
		this.dice = dice;
	}

}
