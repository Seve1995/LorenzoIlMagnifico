package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.effects.GainAsset;
import it.polimi.ingsw.pc22.effects.PickCouncilPrivilege;
import it.polimi.ingsw.pc22.gamebox.CouncilPalace;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.Tower;
import it.polimi.ingsw.pc22.player.Player;

public class SettingFamiliarMemberOnCounsilPalace extends Action {
	private CouncilPalace councilPalace;

	public SettingFamiliarMemberOnCounsilPalace(FamilyMember familyMember, CouncilPalace councilPalace) {
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
			PickCouncilPrivilege pickCouncilPrivilege= new PickCouncilPrivilege();
			pickCouncilPrivilege.executeEffect(player);
			GainAsset gainAsset=new GainAsset();
			
			//TODO gainAsset() definire come parametri tipo e quantita dell'asset
			return true;
		}
		
		else {
			
			return false;
		}

	}
	

}
