package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.gamebox.Cell;
import it.polimi.ingsw.pc22.gamebox.ColorsEnum;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.gamebox.Tower;
import it.polimi.ingsw.pc22.gamebox.TowerCell;
import it.polimi.ingsw.pc22.player.Player;

public class FamiliarSetting extends Action
{
	private FamilyMember familyMember;
	private Cell cell;
	
	public FamiliarSetting(FamilyMember familyMember, Cell cell) 
	{
		super();
		this.familyMember = familyMember;
		this.cell = cell;
	}
	
	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) 
	{
		if (!cell.isEmpty()) return false;
		
		int familiarValue = familyMember.getFamiliarValue();
		
		if (cell.getRequiredDiceValue() > familiarValue) return false;
		
		if (cell instanceof TowerCell)
		{
			TowerCell towerCell = (TowerCell) cell;
			
			CardTypeEnum type = towerCell.getDevelopmentCard().getType();
			
			Tower[] towers = gameBoard.getTower();
			
			Tower selectedTower = null;
			
			for (Tower tower : towers)
			{
				if (tower.getTowerType() == type)
				{
					selectedTower = tower;
					break;
				}
			}
			
			if (selectedTower == null) return false;
			
			TowerCell[] towerCells = selectedTower.getTowerCells();
			
			boolean thereIsAnotherFamilyMember = false;
			
			for (TowerCell cell : towerCells)
			{
				FamilyMember currFamiliMember = cell.getFamilyMember();
				
				if (currFamiliMember == null) continue;
					
				thereIsAnotherFamilyMember = true;
				
				if 
				(
					currFamiliMember.getColor() == ColorsEnum.NEUTER ||
					familyMember.getColor() == ColorsEnum.NEUTER
				)
					break;
				
				if (player.getFamilyMember().contains(currFamiliMember)) 
					return false;
				
			}
			
			if (thereIsAnotherFamilyMember && player.getCoins() < 3)
				return false;
		}
		
		return true;
	}

	@Override
	public void executeAction(Player player, GameBoard gameBoard) 
	{
		// TODO Auto-generated method stub
		
	}
}
