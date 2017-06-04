package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.ColorsEnum;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.gamebox.Production;
import it.polimi.ingsw.pc22.gamebox.ProductionCell;
import it.polimi.ingsw.pc22.player.Player;

public class SettingFamiliarMemberOnProduction extends Action {

	
	public SettingFamiliarMemberOnProduction(FamilyMember familyMember) {
		super(familyMember);
		
	}

	@Override
	protected boolean isLegal(Player player, GameBoard gameBoard) {
		
		Production production = this.gameBoard.getProduction();
		
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
	public boolean executeAction(Player player, GameBoard gameBoard) {
		
		Production production = this.gameBoard.getProduction();
		
		if (isLegal(player, gameBoard) && !(player.isDontCareOccupiedPlaces()))
		{
			
			
			production.getProductionCell()[production.firstCellFree()].setFamilyMember(this.getFamilyMember());
			player.removeFamilyMember(familyMember);
			
			if (production.firstCellFree()>0)
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
			production.getProductionCell()[0].setFamilyMember(this.getFamilyMember());
			player.removeFamilyMember(familyMember);
			return true;
		}
		
		else
		{
			return false;
		}
	}
		
}