package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.ColorsEnum;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.gamebox.Harvest;
import it.polimi.ingsw.pc22.gamebox.HarvestCell;
import it.polimi.ingsw.pc22.player.Player;

public class SettingFamiliarMemberOnHarvest extends Action{

	
	public SettingFamiliarMemberOnHarvest(FamilyMember familyMember) {
		super(familyMember);
	}

	@Override
	protected boolean isLegal (Player player, GameBoard gameBoard) {
		
		Harvest harvest = this.gameBoard.getHarvest(); 
		
		if (super.getFamilyMember().getFamiliarValue() < 1)
			return false;
		
		for (HarvestCell harvestCell : harvest.getHarvestCell()){
			
			FamilyMember currFamilyMember = harvestCell.getFamilyMember();
			
			if (currFamilyMember == null) continue;
			
			if
			(
					currFamilyMember.getColor()==ColorsEnum.NEUTER ||
					super.getFamilyMember().getColor() == ColorsEnum.NEUTER
			)
				break;
			
			if (player.getFamilyMember().contains(currFamilyMember))
				
				return false;
			
		}
		
		return true;
	}

	@Override
	public boolean executeAction(Player player, GameBoard gameBoard) {
		
		Harvest harvest = this.gameBoard.getHarvest(); 
		
		if (isLegal(player, gameBoard) && !(player.isDontCareOccupiedPlaces()))
		{
			
			
			harvest.getHarvestCell()[harvest.firstCellFree()].setFamilyMember(this.getFamilyMember());
			player.removeFamilyMember(familyMember);
			
			if (harvest.firstCellFree()>0)
			{
				if ((this.getFamilyMember().getFamiliarValue()-3)>0)
					this.getFamilyMember().setFamiliarValue(this.getFamilyMember().getFamiliarValue()-3);
				else 
					this.getFamilyMember().setFamiliarValue(0);
			}
			return true;
			
		}
		
		else if (isLegal(player, gameBoard) && player.isDontCareOccupiedPlaces())
		{
			harvest.getHarvestCell()[0].setFamilyMember(this.getFamilyMember());
			player.removeFamilyMember(familyMember);
			return true;
		}
		
		else 
		{
			return false;
		}
	}
}