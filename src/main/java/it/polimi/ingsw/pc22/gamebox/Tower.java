package it.polimi.ingsw.pc22.gamebox;

import java.util.List;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.effects.GainAsset;

import java.util.ArrayList;

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
