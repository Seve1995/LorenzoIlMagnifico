package it.polimi.ingsw.pc22.gamebox;

import java.util.Arrays;

public class PlayerBoard {
	private TerritoryCard[] territories;
	private BuildingCard[] buildings;
	private CharacterCard[] characters;
	private VentureCard[] ventures;
	private int typeOfBonusTile;
	private BonusTile bonusTile;
	
	
	public TerritoryCard[] getTerritories() {
		return territories;
	}


	public void setTerritories(TerritoryCard[] territories) {
		this.territories = territories;
	}


	public BuildingCard[] getBuildings() {
		return buildings;
	}


	public void setBuildings(BuildingCard[] buildings) {
		this.buildings = buildings;
	}


	public CharacterCard[] getCharacters() {
		return characters;
	}


	public void setCharacters(CharacterCard[] characters) {
		this.characters = characters;
	}


	public VentureCard[] getVentures() {
		return ventures;
	}


	public void setVentures(VentureCard[] ventures) {
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


	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bonusTile == null) ? 0 : bonusTile.hashCode());
		result = prime * result + Arrays.hashCode(buildings);
		result = prime * result + Arrays.hashCode(characters);
		result = prime * result + Arrays.hashCode(territories);
		result = prime * result + typeOfBonusTile;
		result = prime * result + Arrays.hashCode(ventures);
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerBoard other = (PlayerBoard) obj;
		if (bonusTile == null) {
			if (other.bonusTile != null)
				return false;
		} else if (!bonusTile.equals(other.bonusTile))
			return false;
		if (!Arrays.equals(buildings, other.buildings))
			return false;
		if (!Arrays.equals(characters, other.characters))
			return false;
		if (!Arrays.equals(territories, other.territories))
			return false;
		if (typeOfBonusTile != other.typeOfBonusTile)
			return false;
		if (!Arrays.equals(ventures, other.ventures))
			return false;
		return true;
	}

	
	@Override
	public String toString() {
		return "PlayerBoard [territories=" + Arrays.toString(territories) + ", buildings=" + Arrays.toString(buildings)
				+ ", characters=" + Arrays.toString(characters) + ", ventures=" + Arrays.toString(ventures)
				+ ", typeOfBonusTile=" + typeOfBonusTile + ", bonusTile=" + bonusTile + "]";
	}


	public void getBonus (BonusTile bonusTile){
		
	}
}
