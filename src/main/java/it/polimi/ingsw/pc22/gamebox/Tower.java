package it.polimi.ingsw.pc22.gamebox;

public class Tower {
	private CardTypeEnum towerType;
	private TowerCell[] towerCells;
	
	public Tower(CardTypeEnum towerType) {
		this.towerType = towerType;
		towerCells = new TowerCell[4];
	}
	
	public CardTypeEnum getTowerType() {
		return towerType;
	}

	public void setTowerType(CardTypeEnum towerType) {
		this.towerType = towerType;
	}

	public TowerCell[] getTowerCells() {
		return towerCells;
	}

	public void setTowerCells(TowerCell[] towerCells) {
		this.towerCells = towerCells;
	}
	
}
