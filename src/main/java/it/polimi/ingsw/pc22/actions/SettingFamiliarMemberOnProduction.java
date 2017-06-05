package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.effects.DoProductionAction;
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
		
		if (super.getFamilyMember().getFamiliarValue() < 1)
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
		
		DoProductionAction doProductionAction = new DoProductionAction();
		
		if (isLegal(player, gameBoard) && !(player.isDontCareOccupiedPlaces()))
		{
			
			
			production.getProductionCell()[production.firstCellFree()].setFamilyMember(this.getFamilyMember());
			
			familyMember.setPlayed(true);
			
			if (production.firstCellFree()>0)
			{
				familyMember.setFamiliarValue(familyMember.getFamiliarValue() - 3);
			}
			
			doProductionAction.setValue(familyMember.getValue());
			
			doProductionAction.executeEffect(player, gameBoard);
			
			return true;
			
		}
		
		else if (isLegal(player, gameBoard) && player.isDontCareOccupiedPlaces())
		{
			production.getProductionCell()[0].setFamilyMember(this.getFamilyMember());

			familyMember.setPlayed(true);
			
			doProductionAction.setValue(familyMember.getValue());
			
			doProductionAction.executeEffect(player, gameBoard);
			
			return true;
		}
		
		else
		{
			return false;
		}
	}
		
}