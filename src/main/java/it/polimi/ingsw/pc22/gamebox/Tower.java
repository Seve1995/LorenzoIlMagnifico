package it.polimi.ingsw.pc22.gamebox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is the representation of the single tower.
 * As the other zones it is composed by an array of single cells.
 * For the correct execution of action and effects, we need to store a reference
 * of the players already present in the tower (PLAYER COLOR)
 */

public class Tower implements Serializable
{
	private CardTypeEnum towerType;
	private List<TowerCell> towerCells;
	private List<PlayerColorsEnum> listPlayers = new ArrayList<>();

    public List<PlayerColorsEnum> getListPlayers() {
		return listPlayers;
	}

	public void setListPlayers(List<PlayerColorsEnum> listPlayers) {
		this.listPlayers = listPlayers;
	}

	public Tower(CardTypeEnum towerType)
	{
		this.towerType = towerType;
		towerCells = new ArrayList<TowerCell>();
	}
	
	public CardTypeEnum getTowerType() {
		return towerType;
	}

	public void setTowerType(CardTypeEnum towerType) {
		this.towerType = towerType;
	}

	public List<TowerCell> getTowerCells() {
		return towerCells;
	}

	public void setTowerCells(List<TowerCell> towerCells) {
		this.towerCells = towerCells;
	}
	
	
	public List<TowerCell> getAvailableCells()
	{
		return towerCells
				.stream()
				.filter(cell -> cell.isEmpty())
				.collect(Collectors.toList());
	}
	
	@Override
	public String toString(){
		StringBuilder output = new StringBuilder(this.towerType.toString()+" Tower\n");
		for(TowerCell t : towerCells)
		{
			output.append(t.toString());
		}
		return output.toString();
	}
	
	public static String gainInfo()
	{
		String output="This area is composed by four towers, each one with four floors."
				+ "On every floor, there is an action space that allows the player to take"
				+ "the corresponding card and, if present, some bonuses.";
		return output;
	}
}
