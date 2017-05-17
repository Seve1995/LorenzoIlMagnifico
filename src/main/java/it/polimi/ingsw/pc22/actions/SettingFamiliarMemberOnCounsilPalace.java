package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.CouncilPalace;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.Tower;
import it.polimi.ingsw.pc22.player.Player;

public class SettingFamiliarMemberOnCounsilPalace extends Action {
	private FamilyMember familyMember;
	private CouncilPalace councilPalace;

	public SettingFamiliarMemberOnCounsilPalace(FamilyMember familyMember, CouncilPalace councilPalace) {
		super();
		this.familyMember = familyMember;
		this.councilPalace = councilPalace;
	}

	@Override
	public boolean isLegal(Player player) {
		
		if (familyMember.getFamiliarValue()<1) 
			return false;
		
		return true;
	}

	@Override
	public void executeAction(Player player) {
		// TODO Auto-generated method stub
		
	}
	

}
