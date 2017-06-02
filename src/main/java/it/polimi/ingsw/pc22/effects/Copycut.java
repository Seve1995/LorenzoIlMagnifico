package it.polimi.ingsw.pc22.effects;

import java.util.List;
import java.util.Set;

import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.gamebox.LeaderCard;
import it.polimi.ingsw.pc22.player.Player;

public class Copycut implements Effect
{
	private LeaderCard leaderCard;
	private GameMatch gameMatch;

	@Override
	public boolean isLegal(Player player) {
		
		//Set<CardTypeEnum> currCardType = this.leaderCard.getRequiredCard().keySet();
		
		List<Player> currPlayers = this.gameMatch.getPlayers();
		
		for (Player p : currPlayers)
		{
			for (LeaderCard l : p.getPlayerBoard().getLeaderCards())
			{
				if(l.equals(leaderCard))
				{
					return true;
				}
			}
			
		}
		
		return false;
	
	}

	@Override
	public void executeEffect(Player player)
	{
		if (isLegal(player))
		{
			for (Effect e : leaderCard.getEffects())
			{
				e.executeEffect(player);
			}
		}
		
	}
	
}
