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
		
		if (familyMember.getValue() < 1)
			
			return false;
		
		for (HarvestCell harvestCell : harvest.getHarvestCell()){
			
			FamilyMember currFamilyMember = harvestCell.getFamilyMember();
			
			if (currFamilyMember == null) continue;
			
			if
			(
					currFamilyMember.getColor()==ColorsEnum.NEUTER ||
					familyMember.getColor() == ColorsEnum.NEUTER
			)
				break;
			
			if (familyMember.getColor().equals(currFamilyMember.getColor()) && !player.isDontCareOccupiedPlaces())

				return false;

			
		}
		
		return true;
	}

	@Override
	public boolean executeAction(Player player, GameBoard gameBoard)
	{
		Harvest harvest = gameBoard.getHarvest(); 
		
		DoHarvestAction doHarvestAction = new DoHarvestAction();
		
		if (isLegal(player, gameBoard) && !(player.isDontCareOccupiedPlaces()))
		{
			harvest.getHarvestCell()[harvest.firstCellFree()].setFamilyMember(familyMember);

			familyMember.setPlayed(true);
			
			if (harvest.firstCellFree()>0)
			{
				familyMember.setValueModifier(familyMember.getValueModifier()-3);
				
				doHarvestAction.setValue(familyMember.getValue());
			}
			else
			{
				doHarvestAction.setValue(familyMember.getValue());
			}
			
			doHarvestAction.executeEffects(player, gameBoard);

			player.setFamiliarPositioned(true);

			return true;
			
		}
		
		else if (isLegal(player, gameBoard) && player.isDontCareOccupiedPlaces())
		{
			harvest.getHarvestCell()[harvest.firstCellFree()].setFamilyMember(familyMember);
			
			familyMember.setPlayed(true);
			
			doHarvestAction.setValue(familyMember.getValue());
			
			doHarvestAction.executeEffects(player, gameBoard);

			player.setFamiliarPositioned(true);

			return true;
		}
		
		else 
		{
			return false;
		}
	}
}