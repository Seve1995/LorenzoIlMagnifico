package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.CouncilPalace;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.player.Player;

public class SettingFamiliarMemberOnCouncilPalace extends Action {
	
	

	public SettingFamiliarMemberOnCouncilPalace(FamilyMember familyMember) {
		super(familyMember);
	}

	@Override
	protected boolean isLegal(Player player) {
		
		if (super.getFamilyMember().getFamiliarValue()<1) 
			return false;
		
		return true;
	}

	@Override
	public boolean executeAction(Player player) {
		
		CouncilPalace councilPalace = this.gameBoard.getCouncilPalace(); 
		
		if (isLegal(player))
		{
			
			
			councilPalace.getCouncilPalaceCells()[councilPalace.firstCellFree()].setFamilyMember(this.getFamilyMember());
			player.removeFamilyMember(familyMember);
			councilPalace.getCouncilPalaceCells()[councilPalace.firstCellFree()].executeEffect(player);
			
			
			return true;
		}
		
		else {
			
			return false;
		}

	}
	

}
