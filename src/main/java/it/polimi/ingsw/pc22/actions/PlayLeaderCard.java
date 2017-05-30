package it.polimi.ingsw.pc22.actions;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.gamebox.LeaderCard;
import it.polimi.ingsw.pc22.player.Player;

public class PlayLeaderCard extends Action {
	
	private LeaderCard leaderCard;

	
	protected boolean isLegal(Player player) 
	{	
		if (player.getPlayerBoard().getLeaderCards().size() > 4)
		{
			return false;
		}
		if (this.leaderCard.getRequiredAssets() != null)
		{
			for (Asset a : this.leaderCard.getRequiredAssets())
			{
				if (player.getAsset(a.getType()) < a.getValue())
				{
					return false;
				}
			}	
		}
		
		if (this.leaderCard.getRequiredCard() != null)
		{
			Set<CardTypeEnum> currCardType = this.leaderCard.getRequiredCard().keySet();
			for (CardTypeEnum key : currCardType)
			{
				
				Integer value = this.leaderCard.getRequiredCard().get(key);
				
				if (key.equals(CardTypeEnum.BUILDING))
				{
					if (player.getPlayerBoard().getBuildings().size() < value)
					{
						return false;
					}
				}
				if (key.equals(CardTypeEnum.TERRITORY))
				{
					if (player.getPlayerBoard().getTerritories().size() < value)
					{
						return false;
					}
				}
				if(key.equals(CardTypeEnum.VENTURE))
				{
					if (player.getPlayerBoard().getVentures().size() < value)
					{
						return false;
					}
				}
				if(key.equals(CardTypeEnum.CHARACTER))
				{
					if (player.getPlayerBoard().getCharacters().size() < value)
					{
						return false;
					}
				}
				if(key.equals(CardTypeEnum.ANY))
				{
					if (player.getPlayerBoard().getVentures().size() >= value || player.getPlayerBoard().getCharacters().size() >= value
							|| player.getPlayerBoard().getTerritories().size() >= value || player.getPlayerBoard().getBuildings().size() >= value)
					{
						return true;
					}
					else
					{
						return false;
					}
				}
			}

		}		
		return true;
		
	}

	@Override
	public boolean executeAction(Player player) 
	{
		if (isLegal(player)){
			player.getPlayerBoard().getLeaderCards().add(leaderCard);
			return true;
		}
		return false;
	}
}