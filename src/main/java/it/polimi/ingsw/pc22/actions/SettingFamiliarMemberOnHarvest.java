package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.ColorsEnum;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.Harvest;
import it.polimi.ingsw.pc22.gamebox.HarvestCell;
import it.polimi.ingsw.pc22.gamebox.TowerCell;
import it.polimi.ingsw.pc22.player.Player;

public class SettingFamiliarMemberOnHarvest extends Action{
	
	private Harvest harvest; 
	
	public SettingFamiliarMemberOnHarvest(FamilyMember familyMember, Harvest harvest) {
		super(familyMember);
		this.harvest = harvest;
	}

	@Override
	public boolean isLegal (Player player) {
		
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
	public void executeAction(Player player) {
		// TODO Auto-generated method stub
		
	}
	

}
