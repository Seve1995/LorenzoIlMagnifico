package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.ColorsEnum;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.Harvest;
import it.polimi.ingsw.pc22.gamebox.HarvestCell;
import it.polimi.ingsw.pc22.player.Player;

public class SettingFamiliarMemberOnHarvest extends Action{
	
	private Harvest harvest;
	
	
	public SettingFamiliarMemberOnHarvest(FamilyMember familyMember, Harvest harvest) {
		super(familyMember);
		this.harvest = harvest;
	}

	@Override
	protected boolean isLegal (Player player) {
		
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
	public boolean executeAction(Player player) {
		if (isLegal(player))
		{
			harvest.getHarvestCell()[harvest.firstCellFree()].setFamilyMember(this.getFamilyMember());
			
			if (harvest.firstCellFree()>0)
			{
				if ((this.getFamilyMember().getFamiliarValue()-3)>0)
					this.getFamilyMember().setFamiliarValue(this.getFamilyMember().getFamiliarValue()-3);
				else 
					this.getFamilyMember().setFamiliarValue(0);
			}
			return true;
			
		}
		
		else 
		{
			return false;
		}
	}
}