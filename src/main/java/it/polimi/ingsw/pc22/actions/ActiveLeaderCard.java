package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public class ActiveLeaderCard extends Action 
{
	int index;

	@Override
	protected boolean isLegal(Player player, GameBoard gameBoard) 
	{
		if (player.getPlayerBoard().getLeaderCards() == null)
		{
			return false;
		}
		
		if(player.getPlayerBoard().getLeaderCards().get(index).isFaceUp())
		{
			return true;
		}
		
		return false;
	}

	@Override
	public boolean executeAction(Player player, GameBoard gameBoard)
	{
		
		
		player.getPlayerBoard().getLeaderCards().get(index).setFaceUp(false);
		
		for (Effect e : player.getPlayerBoard().getLeaderCards().get(index).getEffects())
		{
			e.executeEffects(player, gameBoard);
		}
		
		return true;
	}


}
