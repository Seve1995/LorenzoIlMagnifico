package it.polimi.ingsw.pc22.gamebox;

import java.util.ArrayList;
import java.util.List;

public class Tower {
	private CardTypeEnum towerType;
	private List<TowerCell> towerCells;
	
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
	
	
	
	
	@Override
	public String toString(){
		String output=this.towerType.toString()+" Tower\n";
		for(TowerCell t : towerCells)
		{
			output += t.toString()+"\n";
		}
		return output;
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
		GainAsset gainAsset = new GainAsset();
		gainAsset.setAsset(asset);
		effects.add(gainAsset);
		DevelopmentCard developmentCard = new VentureCard("Morgana", 1, effects, null, null, null, 0);
		DevelopmentCard developmentCard2 = new VentureCard("PIPPOOO", 3, effects, null, null, null, 0);
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
