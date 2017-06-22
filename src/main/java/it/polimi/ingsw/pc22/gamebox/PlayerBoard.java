package it.polimi.ingsw.pc22.gamebox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlayerBoard implements Serializable
{
	private List<TerritoryCard> territories = new ArrayList<>();
	private List<BuildingCard> buildings = new ArrayList<>();
	private List<CharacterCard> characters= new ArrayList<>();
	private List<VentureCard> ventures = new ArrayList<>();
	private List<LeaderCard> leaderCards = new ArrayList<>();
	private int typeOfBonusTile;
	private BonusTile bonusTile;
	
	public List<TerritoryCard> getTerritories() {
		return territories;
	}


	public void setTerritories(List<TerritoryCard> territories) {
		this.territories = territories;
	}


	public List<BuildingCard> getBuildings() {
		return buildings;
	}


	public void setBuildings(List<BuildingCard> buildings) {
		this.buildings = buildings;
	}


	public List<CharacterCard> getCharacters() {
		return characters;
	}


	public void setCharacters(List<CharacterCard> characters) {
		this.characters = characters;
	}


	public List<VentureCard> getVentures() {
		return ventures;
	}


	public void setVentures(List<VentureCard> ventures) {
		this.ventures = ventures;
	}


	public int getTypeOfBonusTile() {
		return typeOfBonusTile;
	}


	public void setTypeOfBonusTile(int typeOfBonusTile) {
		this.typeOfBonusTile = typeOfBonusTile;
	}


	public BonusTile getBonusTile() {
		return bonusTile;
	}


	public void setBonusTile(BonusTile bonusTile) {
		this.bonusTile = bonusTile;
	}

	public List<LeaderCard> getLeaderCards() {
		return leaderCards;
	}


	public void setLeaderCards(List<LeaderCard> leaderCards) {
		this.leaderCards = leaderCards;
	}
	
	
	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("(PLAYER BUILDINGS) ");
		for (BuildingCard b : buildings)
			output.append(b.getName() + "|");
		output.append("\nProduction Bonus: " 
			+ bonusTile.getProductionCoinsBonus().toString() + ", " 
			+ bonusTile.getProductionMilitaryPointsBonus().toString() + ", " 
			+ bonusTile.getProductionServantBonus().toString() + "\n");
		output.append("\n(PLAYER TERRITORIES) ");
		for (TerritoryCard t : territories)
			output.append(t.getName() + "|");
		output.append("\nHarvest Bonus: "
			+ bonusTile.getHarvestCoinsBonus().toString() + ", " 
			+ bonusTile.getHarvestMilitaryPointsBonus().toString() + ", " 
			+ bonusTile.getHarvestServantBonus().toString() + ", " 
			+ bonusTile.getHarvestStonesBonus().toString() + ", " 
			+ bonusTile.getHarvestWoodsBonus() + "\n");
		output.append("\n(PLAYER CHARACTERS) ");
		for (CharacterCard c : characters)
			output.append(c.getName() + "|");
		output.append("\n(PLAYER VENTURES) ");
		for (VentureCard v: ventures)
			output.append(v.getName() + "|");
		return output.toString();
	}
	
}
