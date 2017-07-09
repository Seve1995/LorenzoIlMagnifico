package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the council palace zone;
 * it is composed as an array of 16 council palace
 * cells. It includes the method firstCellFree() that
 * return the first council palace cell that doesn't include
 * a family member. It also has the list of players already in council
 * palace, orderer from the first player that enter in the council palace
 * in the current turn to the last, in order to easily check the new turn's starting 
 * order.
 */

public class CouncilPalace implements Serializable
{

	private CouncilPalaceCell[] councilPalaceCells;
	private List<Player> playersInCouncilPalace = new ArrayList<>();
	
	public List<Player> getPlayersInCouncilPalace() {
		return playersInCouncilPalace;
	}

	public void setPlayersInCouncilPalace(List<Player> playersInCouncilPalace) {
		this.playersInCouncilPalace = playersInCouncilPalace;
	}

	public CouncilPalaceCell[] getCouncilPalaceCells() {
		return councilPalaceCells;
	}

	public void setCouncilPalaceCells(CouncilPalaceCell[] councilPalaceCells) {
		this.councilPalaceCells = councilPalaceCells;
	}

	public int firstCellFree()
	{
		int i=0;
		while ( i < this.councilPalaceCells.length)
		{
			System.out.println(councilPalaceCells.length + " " + councilPalaceCells[i]);
			if ((councilPalaceCells[i].isEmpty()))
			{
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public String toString() {
		StringBuilder output = new StringBuilder("COUNCIL PALACE\n"
				+ "FamilyMembers already in council palace:\n");
		for (int i=0; i<firstCellFree(); i++)
			output.append(i + ") " + councilPalaceCells[i].getFamilyMember().toString() + "\n");
		if (firstCellFree()>0 && firstCellFree()%4==0) output.append("\n");
		return output.toString();
	}
}