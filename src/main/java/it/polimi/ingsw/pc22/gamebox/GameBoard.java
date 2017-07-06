package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.effects.Effect;

import java.io.Serializable;
import java.util.List;

public class GameBoard implements Serializable
{
	private String gameMatchName;

	private Tower[] towers;
	private Harvest harvest;
	private Production production;
	private Market market;
	private CouncilPalace councilPalace;
	private List<Dice> dices;
	private List<ExcommunicationCard> excommunicationCards;

	public GameBoard() 
	{
		
	}

	public Tower[] getTowers()
	{
		return towers;
	}

	public Tower getTowerByType(CardTypeEnum type)
	{
		for (Tower tower : towers)
		{
			if (tower.getTowerType().equals(type))
				return tower;
		}

		return null;
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

	public List<ExcommunicationCard> getExcommunicationCards() {
		return excommunicationCards;
	}

	public void setExcommunicationCards(List<ExcommunicationCard> excommunicationCards) {
		this.excommunicationCards = excommunicationCards;
	}

	public String getGameMatchName()
	{
		return gameMatchName;
	}

	public void setGameMatchName(String gameMatchName)
	{
		this.gameMatchName = gameMatchName;
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
