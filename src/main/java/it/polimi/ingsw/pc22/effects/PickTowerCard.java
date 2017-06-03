package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.BuildingCard;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.gamebox.CharacterCard;
import it.polimi.ingsw.pc22.gamebox.DevelopmentCard;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.gamebox.TerritoryCard;
import it.polimi.ingsw.pc22.gamebox.Tower;
import it.polimi.ingsw.pc22.gamebox.TowerCell;
import it.polimi.ingsw.pc22.gamebox.VentureCard;
import it.polimi.ingsw.pc22.player.CardModifier;
import it.polimi.ingsw.pc22.player.Player;


import java.util.List;

public class PickTowerCard implements Effect{
	

	private int floor;
	private CardTypeEnum cardType;
	private int diceValue;
	
	public int getDiceValue() {
		return diceValue;
	}
	public void setDiceValue(int diceValue) {
		this.diceValue = diceValue;
	}
		
	public PickTowerCard(int floor, CardTypeEnum cardType, int diceValue) {
		super();
		this.floor = floor;
		this.cardType = cardType;
		this.diceValue = diceValue;
	}
	
	/*private void ApplyChanges(DevelopmentCard d, Player p, CardTypeEnum ct)
	{
		
		if (ct.equals(CardTypeEnum.BUILDING))
		{
			BuildingCard currBuildingCard = (BuildingCard) d;
			
			currBuildingCard.se
		}
		
		
		
		
		
		
		
	}*/
	
	
	@Override
	public boolean isLegal(Player player) 
	{
		Tower tower = null;
		
		for (Tower t : player.getGameBoard().getTower())
		{
			if (t.getTowerType().equals(cardType))
			{
				tower = t;
				
				for (TowerCell tc : tower.getTowerCells())
				{
					//ApplyChanges(tc.getDevelopmentCard(), player);
				}
				
			}
		}
		
		//modifico tutte le carte di quella torre!!
	
		if (tower.getTowerCells().get(floor).getRequiredDiceValue() < diceValue)
			return false;
		
		else
		{
		
		if (this.cardType.equals(CardTypeEnum.CHARACTER))
		{
			if (player.getPlayerBoard().getCharacters().size() > 6)
			{
				return false;
			}
			
			CharacterCard currCharacterCard = (CharacterCard) tower.getTowerCells().get(floor).getDevelopmentCard();
			
			if (currCharacterCard.getCoinsCost().getValue() < player.getCoins())
			{
				return false;
			}
				
		}
		
		if (this.cardType.equals(CardTypeEnum.BUILDING)) 
		{
			if (player.getPlayerBoard().getBuildings().size() > 6)
			{
				return false;
			}
			
			BuildingCard currBuildingCard = (BuildingCard) tower.getTowerCells().get(floor).getDevelopmentCard();
			
			for (Asset a : currBuildingCard.getCosts())
			{
				if (player.getAsset(a.getType()) < a.getValue())
					return false;
			}
	
		}
		
		if (this.cardType.equals(CardTypeEnum.VENTURE))
		{
			if (player.getPlayerBoard().getBuildings().size() > 6)
			{
				return false;
			}
			
			VentureCard currVentureCard = (VentureCard) tower.getTowerCells().get(floor).getDevelopmentCard();
			
			if (currVentureCard.getMilitaryPointsRequired().getValue() < player.getMilitaryPoints())
				return false;
		
		}
		
		if (this.cardType.equals(CardTypeEnum.TERRITORY)) 
		{
			
			if (player.getPlayerBoard().getTerritories().size() > 6)
			{
				return false;
			}
			
			else if (!(player.isNoMilitaryPointsForTerritories())) 
			
			{
					if (player.getPlayerBoard().getTerritories().size() + 1 == 3)
					{
						if (player.getMilitaryPoints() < 3)
						{
							return false;
						}
					}
						
					else if (player.getPlayerBoard().getTerritories().size() + 1 == 4)
					{
						if (player.getMilitaryPoints() < 7)
						{
							return false;
						}
					}
						
					else if (player.getPlayerBoard().getTerritories().size() + 1 == 5)
					{
						if (player.getMilitaryPoints() < 12)
						{
							return false;
						}
					}
					
					else if  (player.getPlayerBoard().getTerritories().size() + 1 == 6)
					{
		
						if (player.getMilitaryPoints() < 18)
						{
							return false;
						}
					}
			}
			
			else if (player.isNoMilitaryPointsForTerritories())
				return true;
		}
		
		}
		return true;
	}
	
	
	
	private void removeCards(Tower t, int floor)
	{
		t.getTowerCells().get(floor).setDevelopmentCard(null);
	}
	
	
	private void activeEffects(DevelopmentCard d, Player p)
	{
		for (Effect e : d.getImmediateEffects())
		{
				e.executeEffect(p);
				
				if ((e instanceof AddAsset) && p.isSantaRita())
					e.executeEffect(p);
		}
			
	}
	
	
	@Override
	public void executeEffect(Player player) 
	{

		if (isLegal(player))
		{
				for (Tower t : player.getGameBoard().getTower())
				{
					if (t.getTowerType().equals(cardType))
					{   
						if (cardType.equals(CardTypeEnum.BUILDING))
						{
							player.getPlayerBoard().getBuildings().add((BuildingCard) t.getTowerCells().get(floor).getDevelopmentCard());
							
						}
						
						if (cardType.equals(CardTypeEnum.CHARACTER))
						{
							player.getPlayerBoard().getCharacters().add((CharacterCard) t.getTowerCells().get(floor).getDevelopmentCard());
							
						}
						
						if (cardType.equals(CardTypeEnum.TERRITORY))
						{
							player.getPlayerBoard().getTerritories().add((TerritoryCard) t.getTowerCells().get(floor).getDevelopmentCard());
				
						}
						
						if (cardType.equals(CardTypeEnum.VENTURE))
						{
							player.getPlayerBoard().getVentures().add((VentureCard) t.getTowerCells().get(floor).getDevelopmentCard());
							
						}
						
						activeEffects(t.getTowerCells().get(floor).getDevelopmentCard(), player);
						
						removeCards(t, floor);
						
					}
				}
		}
		
	
	}
	
}
