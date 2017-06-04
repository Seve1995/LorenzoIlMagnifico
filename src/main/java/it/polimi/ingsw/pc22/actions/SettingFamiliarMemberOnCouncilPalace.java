package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.CouncilPalace;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.player.Player;

public class SettingFamiliarMemberOnCouncilPalace extends Action {
	
	private CouncilPalace councilPalace;

	public SettingFamiliarMemberOnCouncilPalace(FamilyMember familyMember, CouncilPalace councilPalace) {
		super(familyMember);
		this.councilPalace = councilPalace;
	}

	@Override
	protected boolean isLegal(Player player) {
		
		if (super.getFamilyMember().getFamiliarValue()<1) 
			return false;
		
		return true;
	}

	@Override
	public boolean executeAction(Player player) {
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
