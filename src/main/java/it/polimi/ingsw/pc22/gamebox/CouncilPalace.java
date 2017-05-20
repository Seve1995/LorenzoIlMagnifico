package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.player.Player;

public class CouncilPalace {
	private int councilPalaceMaxPlaces;
	private CouncilPalaceCell[] councilPalaceCells;
	
	public int getcouncilPalaceMaxPlaces() {
		return councilPalaceMaxPlaces;
	}

	public void setcouncilPalaceMaxPlaces(int councilPalaceMaxPlaces) {
		this.councilPalaceMaxPlaces = councilPalaceMaxPlaces;
	}

	public CouncilPalaceCell[] getCouncilPalaceCells() {
		return councilPalaceCells;
	}

	public void setCouncilPalaceCells(CouncilPalaceCell[] councilPalaceCells) {
		this.councilPalaceCells = councilPalaceCells;
	}

	public void ExecuteEffect(Player player)
	{
		//TODO: implement the execution effect for this kind of cells
	}
}
