package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.effects.PickOneCouncilPrivilege;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.gamebox.LeaderCard;
import it.polimi.ingsw.pc22.player.Player;

public class DiscardLeaderCard extends Action{
	
	int index;

	@Override
	protected boolean isLegal(Player player, GameBoard gameBoard) {
		
		return true;
	}

	@Override
	public boolean executeAction(Player player, GameBoard gameBoard) {
		
		player.getLeaderCards().set(index, null);
		
		PickOneCouncilPrivilege currCouncilPrivilege=new PickOneCouncilPrivilege();
		
		currCouncilPrivilege.executeEffects(player, gameBoard);
		
		return true;
		
	}
	
}
