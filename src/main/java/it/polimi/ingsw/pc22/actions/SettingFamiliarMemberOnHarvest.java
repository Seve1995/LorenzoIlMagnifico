package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.effects.DoHarvestAction;
import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.player.Player;

public class SettingFamiliarMemberOnHarvest extends Action{

	public SettingFamiliarMemberOnHarvest(FamilyMember familyMember) {
		super(familyMember);
	}

    public SettingFamiliarMemberOnHarvest() {

    }

    @Override
	protected boolean isLegal (Player player, GameBoard gameBoard) {
		
		Harvest harvest = gameBoard.getHarvest(); 
		
		if (super.getFamilyMember().getValue() < 1)
			
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
			
			if (player.getFamilyMembers().contains(currFamilyMember))
				
				return false;
			
		}
		
		return true;
	}

	@Override
	public boolean executeAction(Player player, GameBoard gameBoard) {
		
		Harvest harvest = gameBoard.getHarvest(); 
		
		DoHarvestAction doHarvestAction = new DoHarvestAction();
		
		if (isLegal(player, gameBoard) && !(player.isDontCareOccupiedPlaces()))
		{

			harvest.getHarvestCell()[harvest.firstCellFree()].setFamilyMember(this.getFamilyMember());

			familyMember.setPlayed(true);
			
			if (harvest.firstCellFree()>0)
			{
				this.familyMember.setFamiliarValue(this.familyMember.getFamiliarValue() - 3);
			}
			
			doHarvestAction.setValue(familyMember.getValue());
			
			doHarvestAction.executeEffect(player, gameBoard);
			
			return true;
			
		}
		
		else if (isLegal(player, gameBoard) && player.isDontCareOccupiedPlaces())
		{
			harvest.getHarvestCell()[0].setFamilyMember(this.getFamilyMember());
			
			familyMember.setPlayed(true);
			
			doHarvestAction.setValue(familyMember.getValue());
			
			doHarvestAction.executeEffect(player, gameBoard);
			
			return true;
		}
		
		else 
		{
			return false;
		}
	}
}