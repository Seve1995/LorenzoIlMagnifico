package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.gamebox.LeaderCard;
import it.polimi.ingsw.pc22.messages.GameStatusMessage;
import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

/**
 * This class represent the action used to active a leader card, as every action
 * it exposes the isLegal and executeAction
 */

public class ActiveLeaderCard extends Action 
{
	private int index;

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
    public boolean isLegal(Player player, GameBoard gameBoard)
	{
		List<LeaderCard> cards = player.getPlayerBoard().getLeaderCards();

		if (cards == null)
		{
			return false;
		}
		
		if(cards.get(index).isFaceUp())
		{
			return true;
		}
		
		return false;
	}

	@Override
	public boolean executeAction(Player player, GameBoard gameBoard)
	{
		List<LeaderCard> cards = player.getPlayerBoard().getLeaderCards();

		cards.get(index).setFaceUp(false);
		
		for (Effect e : cards.get(index).getEffects())
		{
			e.executeEffects(player, gameBoard);
		}

		IOAdapter adapter = player.getAdapter();

		adapter.printMessage(new GameStatusMessage(gameBoard, player, "leader action"));

		return true;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ActiveLeaderCard that = (ActiveLeaderCard) o;

		return index == that.index;
	}

	@Override
	public int hashCode()
	{
		return index;
	}
}
