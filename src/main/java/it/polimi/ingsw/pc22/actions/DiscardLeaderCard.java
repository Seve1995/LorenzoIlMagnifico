package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.effects.PickOneCouncilPrivilege;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.gamebox.LeaderCard;
import it.polimi.ingsw.pc22.player.Player;

public class DiscardLeaderCard extends Action
{
	private int index;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	protected boolean isLegal(Player player, GameBoard gameBoard) {

		if (player.getLeaderCards().isEmpty()) return false;
		
		if (player.getLeaderCards().size() >= index ) return false;

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
