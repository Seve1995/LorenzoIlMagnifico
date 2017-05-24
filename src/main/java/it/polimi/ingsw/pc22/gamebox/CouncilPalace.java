package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.player.Player;

public class CouncilPalace
{

	private CouncilPalaceCell[] councilPalaceCells;

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
	
	public int firstCellFree() {
		int i=0;
		while ( i < this.councilPalaceCells.length)
		{
			if (!(councilPalaceCells[i].isEmpty()))
			{
				i++;
			}
		}
		return i;
	}
}
