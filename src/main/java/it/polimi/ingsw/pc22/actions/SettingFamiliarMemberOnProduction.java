package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.Production;
import it.polimi.ingsw.pc22.player.Player;

public class SettingFamiliarMemberOnProduction extends Action {
	private FamilyMember familyMember; 
	private Production production;
	
	public SettingFamiliarMemberOnProduction(FamilyMember familyMember, Production production) {
		super();
		this.familyMember = familyMember;
		this.production = production;
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
