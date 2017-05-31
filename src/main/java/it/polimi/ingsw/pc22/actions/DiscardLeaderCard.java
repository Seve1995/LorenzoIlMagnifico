package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.effects.PickOneCouncilPrivilege;
import it.polimi.ingsw.pc22.player.Player;

public class DiscardLeaderCard extends Action{
	
	private int index;

	@Override
	protected boolean isLegal(Player player) {
		
		return true;
	}

	@Override
	public boolean executeAction(Player player) {
		
		
		player.getLeaderCards().remove(index);
		PickOneCouncilPrivilege currCouncilPrivilege=new PickOneCouncilPrivilege();
		currCouncilPrivilege.executeEffect(player);
		return true;
		
	}
	
	
	
	

}
