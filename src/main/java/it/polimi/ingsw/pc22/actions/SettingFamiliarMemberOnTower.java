package it.polimi.ingsw.pc22.actions;


import it.polimi.ingsw.pc22.gamebox.Tower;
import it.polimi.ingsw.pc22.gamebox.TowerCell;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.gamebox.ColorsEnum;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;


public class SettingFamiliarMemberOnTower extends Action {
	private FamilyMember familyMember;
	private int floor;
	private Tower tower;
	
	public SettingFamiliarMemberOnTower (FamilyMember familyMember, Tower tower, int floor) 
	{
		this.familyMember = familyMember;
		this.floor = floor;
		this.tower = tower;
	}

	@Override
	public boolean isLegal (Player player)
	{
		
		//if (floor > 4 || floor < 1) return false; TODO check validazione dell'input
		
		if (!tower.getTowerCells()[floor].isEmpty()) return false; 
		
		int familiarValue = familyMember.getFamiliarValue();
		
		if (tower.getTowerCells()[floor].getRequiredDiceValue() > familiarValue) return false;
		
		boolean thereIsAnotherFamilyMember = false;
		
		for (TowerCell towerCell : tower.getTowerCells())
		{
			FamilyMember currFamilyMember = towerCell.getFamilyMember();
			
			if (currFamilyMember == null) continue;
			
			thereIsAnotherFamilyMember = true;
			
			if
			(
					currFamilyMember.getColor()==ColorsEnum.NEUTER ||
					familyMember.getColor() == ColorsEnum.NEUTER
			)
				break;
			
			if (player.getFamilyMember().contains(currFamilyMember))
				return false;
			
		}
		
		if (thereIsAnotherFamilyMember && player.getCoins() < 3)
			return false;
			
		return true;
		
	}
	
	@Override
	public void executeAction (Player player) {
		// TODO Auto-generated method stub
		
	}
	

}