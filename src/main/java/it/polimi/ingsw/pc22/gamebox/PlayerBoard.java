package it.polimi.ingsw.pc22.gamebox;

import java.util.List;

public class PlayerBoard {
	private List<TerritoryCard> territories;
	private List<BuildingCard> buildings;
	private List<CharacterCard> characters;
	private List<VentureCard> ventures;
	private List<LeaderCard> leaderCards;
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
	


	public void getBonus (BonusTile bonusTile){
		
	}


	public List<LeaderCard> getLeaderCards() {
		return leaderCards;
	}


	public void setLeaderCards(List<LeaderCard> leaderCards) {
		this.leaderCards = leaderCards;
	}
	
	
		
	
}
