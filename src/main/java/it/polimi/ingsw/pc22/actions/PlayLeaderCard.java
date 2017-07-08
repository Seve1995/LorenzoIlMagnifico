package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.gamebox.LeaderCard;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.utils.RequiredCard;

import java.util.List;

public class PlayLeaderCard extends Action
{
	private int index;

	public void setIndex(int index)
	{
		this.index = index;
	}

	public boolean isLegal(Player player, GameBoard gameBoard)
	{
		if (index >= player.getLeaderCards().size())
			return false;

		if (player.getPlayerBoard().getLeaderCards().size() > 4)
		{
			return false;
		}

		LeaderCard leaderCard = player.getLeaderCards().get(index);

		if (leaderCard.getRequiredAssets() != null)
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

			if (key.equals(CardTypeEnum.BUILDING) && player.getPlayerBoard().getBuildings().size() < value)
			{
				return false;
			}
			if (key.equals(CardTypeEnum.TERRITORY) && player.getPlayerBoard().getTerritories().size() < value)
			{
				return false;
			}
			if(key.equals(CardTypeEnum.VENTURE) && player.getPlayerBoard().getVentures().size() < value)
			{
				return false;
			}
			if(key.equals(CardTypeEnum.CHARACTER) && player.getPlayerBoard().getCharacters().size() < value)
			{
				return false;
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
		if (!isLegal(player, gameBoard))
			return false;

		LeaderCard leaderCard = player.getLeaderCards().get(index);

		player.getPlayerBoard().getLeaderCards().add(leaderCard);

		leaderCard.setPlayed(true);

		return true;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		PlayLeaderCard that = (PlayLeaderCard) o;

		return index == that.index;
	}

	@Override
	public int hashCode() {
		return index;
	}
}