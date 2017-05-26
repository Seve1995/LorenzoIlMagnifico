package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.ColorsEnum;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.HarvestCell;
import it.polimi.ingsw.pc22.gamebox.Production;
import it.polimi.ingsw.pc22.gamebox.ProductionCell;
import it.polimi.ingsw.pc22.player.Player;

public class SettingFamiliarMemberOnProduction extends Action {
	private Production production;
	
	public SettingFamiliarMemberOnProduction(FamilyMember familyMember, Production production) {
		super(familyMember);
		this.production = production;
	}

	@Override
	protected boolean isLegal(Player player) {
		
		if (super.getFamilyMember().getFamiliarValue()<1)
			return false;
		
		for (ProductionCell productionCell : production.getProductionCell()){
			
			FamilyMember currFamilyMember = productionCell.getFamilyMember();
			
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
			production.getProductionCell()[production.firstCellFree()].setFamilyMember(this.getFamilyMember());
			
			if (production.firstCellFree()>0)
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

