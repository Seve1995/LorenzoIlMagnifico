package it.polimi.ingsw.pc22.gamebox;

import java.util.List;
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
		String output=this.towerType.toString()+"/n";
		for(TowerCell t : towerCells)
		{
			output = t.toString()+"/n";
		}
		return output;
	}
	
}
