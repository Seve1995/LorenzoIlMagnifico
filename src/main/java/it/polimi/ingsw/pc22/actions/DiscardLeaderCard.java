package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.messages.GameStatusMessage;
import it.polimi.ingsw.pc22.player.Player;

/**
 * This class represent the action used to Discard a leader card, as every action
 * it exposes the isLegal and executeAction
 * this action doesn't position a familiarMember
 */
public class DiscardLeaderCard extends Action
{
	private int index;

	private Effect pickPrivilege;

	public void setPickPrivilege(Effect pickPrivilege)
	{
		this.pickPrivilege = pickPrivilege;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
    public boolean isLegal(Player player, GameBoard gameBoard)
	{
		if (player.getLeaderCards().isEmpty())
			return false;
		
		if (player.getLeaderCards().size() >= index)
			return false;

		return true;
	}

	@Override
	public boolean executeAction(Player player, GameBoard gameBoard)
	{
		player.getLeaderCards().set(index, null);

		pickPrivilege.executeEffects(player, gameBoard);

		IOAdapter adapter = player.getAdapter();

		adapter.printMessage(new GameStatusMessage(gameBoard, player, "leader action"));
		
		return true;
		
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		DiscardLeaderCard that = (DiscardLeaderCard) o;

		return index == that.index;
	}

	@Override
	public int hashCode() {
		return index;
	}
}
