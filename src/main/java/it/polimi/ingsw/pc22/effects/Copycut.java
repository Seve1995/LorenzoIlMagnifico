package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.gamebox.LeaderCard;
import it.polimi.ingsw.pc22.player.Player;

public class Copycut implements Effect
{
	private LeaderCard leaderCard;
	private GameMatch gameMatch;

	@Override
	public boolean isLegal(Player player) {
		
		for (LeaderCard l : player.getPlayerBoard().getLeaderCards())
		{
			if(l.equals(leaderCard))
			{
				return false;
			}
		}
		
		for (LeaderCard l : player.getLeaderCards())
		{
			if(l.equals(leaderCard))
			{
				return false;
			}
		}
		
		return false;
		
	}

	@Override
	public void executeEffect(Player player)
	{
		for (Effect e : leaderCard.getEffects())
		{
			e.executeEffect(player);
		}
		
	}
	
}
