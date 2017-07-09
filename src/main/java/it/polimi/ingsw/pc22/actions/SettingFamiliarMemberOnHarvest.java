package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.effects.DoHarvestAction;
import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.player.Player;

/**
 * This action is used to play a familiar on the the harvest section
 * it checks that the requirements are satisfied and executes
 * the action
*/

public class SettingFamiliarMemberOnHarvest extends Action
{
	private DoHarvestAction doHarvestAction;

	public SettingFamiliarMemberOnHarvest(FamilyMember familyMember)
	{
		super(familyMember);
	}

    public SettingFamiliarMemberOnHarvest() {}

    @Override
	public boolean isLegal(Player player, GameBoard gameBoard)
	{
		System.out.println("Harvest the correct value is false: "
				+ (familyMember.getValue() < 1));

		if (familyMember.getValue() < 1)
			return false;

		Harvest harvest = gameBoard.getHarvest();

		System.out.println("Harvest, the correct blocked is false: "
				+ (harvest.getHarvestCell()[0].getFamilyMember() != null &&
				harvest.getHarvestCell()[1].isABlockedCell()));

		if (harvest.getHarvestCell()[0].getFamilyMember() != null &&
				harvest.getHarvestCell()[1].isABlockedCell())
		{
			return false;
		}
		
		for (HarvestCell harvestCell : harvest.getHarvestCell())
		{
			FamilyMember currFamilyMember = harvestCell.getFamilyMember();
			
			if (currFamilyMember == null)
				continue;
			
			if
			(
				currFamilyMember.getColor() == ColorsEnum.NEUTER ||
				familyMember.getColor() == ColorsEnum.NEUTER
			)
				break;

			System.out.println("Harvest, the correct color is false: "
					+ familyMember.getColor().equals(currFamilyMember.getColor()));

			if (familyMember.getColor().equals(currFamilyMember.getColor()) && !player.isDontCareOccupiedPlaces())

				return false;
		}
		
		return true;
	}

	@Override
	public boolean executeAction(Player player, GameBoard gameBoard)
	{
		Harvest harvest = gameBoard.getHarvest(); 

		if (!isLegal(player, gameBoard))
			return false;

		if (!player.isDontCareOccupiedPlaces())
		{
			if (harvest.firstCellFree() > 0)
			{
				familyMember.setValueModifier(familyMember.getValueModifier() -3);
				
				doHarvestAction.setValue(familyMember.getValue() -3);
			}
			else
			{
				doHarvestAction.setValue(familyMember.getValue());
			}
			
			boolean executed = doHarvestAction.executeEffects(player, gameBoard);

			if (!executed)
				return false;

			harvest.getHarvestCell()[harvest.firstCellFree()].setFamilyMember(familyMember);

			familyMember.setPlayed(true);

			player.setFamiliarPositioned(true);

			return true;
			
		}
		else
		{
			doHarvestAction.setValue(familyMember.getValue());

			boolean executed = doHarvestAction.executeEffects(player, gameBoard);

			if (!executed)
				return false;

			harvest.getHarvestCell()[harvest.firstCellFree()].setFamilyMember(familyMember);
			
			familyMember.setPlayed(true);

			player.setFamiliarPositioned(true);

			return true;
		}
	}

	public void setDoHarvestAction(DoHarvestAction doHarvestAction)
	{
		this.doHarvestAction = doHarvestAction;
	}
}