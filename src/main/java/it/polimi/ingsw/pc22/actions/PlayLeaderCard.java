package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.gamebox.LeaderCard;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.utils.RequiredCard;

import java.util.List;
import java.util.Set;

public class PlayLeaderCard extends Action {
	
	private int index;

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	protected boolean isLegal(Player player, GameBoard gameBoard)
	{

		LeaderCard leaderCard = player.getLeaderCards().get(index);

		if (player.getPlayerBoard().getLeaderCards().size() > 4)
		{
			return false;
		}

		if (player.getLeaderCards().get(index).getRequiredAssets() != null)
		{
			for (Asset a : leaderCard.getRequiredAssets())
			{
				if (player.getAsset(a.getType()) < a.getValue())
				{
					return false;
				}
			}	
		}
		
		if (leaderCard.getRequiredCards() == null)
			return true;

		List<RequiredCard> requiredCards = leaderCard.getRequiredCards();

		for (RequiredCard requiredCard : requiredCards)
		{

			int value = requiredCard.getValue();

			CardTypeEnum key = requiredCard.getType();

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

		return true;
		
	}

	@Override
	public boolean executeAction(Player player, GameBoard gameBoard) 
	{
		if (isLegal(player, gameBoard))
		{
			LeaderCard leaderCard = player.getLeaderCards().get(index);

			player.getPlayerBoard().getLeaderCards().add(leaderCard);

			leaderCard.setPlayed(true);
			
			return true;
		}

		return false;
	}
}
