package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.effects.PickOneCouncilPrivilege;
import it.polimi.ingsw.pc22.gamebox.LeaderCard;
import it.polimi.ingsw.pc22.player.Player;

public class DiscardLeaderCard extends Action{
	
	private LeaderCard leaderCard;

	@Override
	protected boolean isLegal(Player player) {
		
		return true;
	}

	@Override
	public boolean executeAction(Player player) {
		
		int i=1;//valore fittizio
		player.getLeaderCards().remove(i);
		PickOneCouncilPrivilege currCouncilPrivilege=new PickOneCouncilPrivilege();
		currCouncilPrivilege.executeEffect(player);
		return true;
		
	}
	
	
	
	

}
