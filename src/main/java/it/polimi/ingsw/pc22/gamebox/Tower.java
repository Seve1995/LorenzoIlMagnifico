package it.polimi.ingsw.pc22.gamebox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	
	public static String gainInfo(){
		String output="This area is composed by four towers, each one with four floors."
				+ "On every floor, there is an action space that allows the player to take"
				+ "the corresponding card and, if present, some bonuses.";
		return output;
	}
	
	
	
	
	/*
	public static void main(String[] args) {
		List<Effect> effects = new ArrayList<Effect>();
		Asset asset = new Asset(3, AssetType.COIN);
		AddAsset gainAsset = new AddAsset();
		gainAsset.setAsset(asset);
		effects.add(gainAsset);
		DevelopmentCard developmentCard = new VentureCard("Morgana", 1, effects, null, 0);
		DevelopmentCard developmentCard2 = new VentureCard("Pippo", 3, effects, null, 0);
		TowerCell c = new TowerCell(3, effects);
		TowerCell c1 = new TowerCell(5, effects);
		TowerCell c2 = new TowerCell(3, effects);
		TowerCell c3 = new TowerCell(3, effects);
		c.setDevelopmentCard(developmentCard);
		c1.setDevelopmentCard(developmentCard2);
		c2.setDevelopmentCard(developmentCard);
		c3.setDevelopmentCard(developmentCard);
		List<TowerCell> towerCells = new ArrayList<TowerCell>();
		towerCells.add(c);
		towerCells.add(c1);
		towerCells.add(c2);
		towerCells.add(c3);
		Tower tower = new Tower(CardTypeEnum.BUILDING);
		tower.setTowerCells(towerCells);
		System.out.println(tower.toString());
	}
	*/
}
