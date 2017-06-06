package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.player.CardModifier;
import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

public class PickTowerCard implements Effect

{
	private int floor;
	private CardTypeEnum cardType;
	private int diceValue;
	private List<Asset> assetsDiscount;

	private int diceValueCharacter;
	private int diceValueBuilding;
	private int diceValueVenture;
	private int diceValueTerritory;
	
	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public CardTypeEnum getCardType() {
		return cardType;
	}

	public void setCardType(CardTypeEnum cardType) {
		this.cardType = cardType;
	}

	public int getDiceValue() {
		return diceValue;
	}

	public void setDiceValue(int diceValue) {
		this.diceValue = diceValue;
	}

	public List<Asset> getAssetsDiscount() {
		return assetsDiscount;
	}

	public void setAssetsDiscount(List<Asset> assetsDiscount) {
		this.assetsDiscount = assetsDiscount;
	}

	public PickTowerCard() {}

	public PickTowerCard(int floor, CardTypeEnum cardType, int diceValue) {
		this.floor = floor;
		this.cardType = cardType;
		this.diceValue = diceValue;
	}
	
	private void ApplyDiceChanges (Player p)
	{
		for (CardModifier cm : p.getCardModifiers())
		{
			if (cm.getCardType().equals(CardTypeEnum.CHARACTER))
			{
				diceValueCharacter = diceValue + cm.getValueModifier();
			}
			
			if (cm.getCardType().equals(CardTypeEnum.TERRITORY))
			{
				diceValueTerritory = diceValue + cm.getValueModifier();
			}
			
			if (cm.getCardType().equals(CardTypeEnum.VENTURE))
			{
				diceValueVenture = diceValue + cm.getValueModifier();
				
			}
			
			if (cm.getCardType().equals(CardTypeEnum.BUILDING))
			{
				diceValueBuilding = diceValue + cm.getValueModifier();
			}
			
		}
	}
	
	
	private void ApplyChanges (DevelopmentCard d, Player p, CardTypeEnum ct)
	
	{

		if (ct.equals(CardTypeEnum.BUILDING))
			
		{
			BuildingCard currBuildingCard = (BuildingCard) d;
			
			for (CardModifier cm : p.getCardModifiers())
			{
				if (cm.getCardType().equals(ct))
				{
					
					for (Asset a : cm.getAssetDiscount())
					{
						for (Asset a1 : currBuildingCard.getCosts())
						{
						
							if (a1.getType().equals(a.getType()))
							
								a1.setValue(a1.getValue() - a.getValue());	
						}
					
					}
					
				}
			
			}
			
		}
		
		if (ct.equals(CardTypeEnum.CHARACTER))
			
		{
			CharacterCard currCharacterCard = (CharacterCard) d;
			
			for (CardModifier cm : p.getCardModifiers())
			{
				if (cm.getCardType().equals(ct))
					
				for (Asset a : cm.getAssetDiscount())
				{
					
					if (a.getType().equals(AssetType.COIN))
					{
						currCharacterCard.getCoinsCost().setValue(currCharacterCard.getCoinsCost().getValue() - a.getValue());
					}
					
				}
			
			}
	
		}
		
	}
	
	
	private void RemoveChanges(DevelopmentCard d, Player p, CardTypeEnum ct)
	{

		if (ct.equals(CardTypeEnum.BUILDING))
			
		{
			BuildingCard currBuildingCard = (BuildingCard) d;
			
			for (CardModifier cm : p.getCardModifiers())
			{
				if (cm.getCardType().equals(ct) && !(cm.isOnlyOneAsset()))
				{
					
					for (Asset a : cm.getAssetDiscount())
					{
						for (Asset a1 : currBuildingCard.getCosts())
						{
						
							if (a1.getType().equals(a.getType()))
							
								a1.setValue(a1.getValue() + a.getValue());	
						}
					
					}
					
				}
				
				else if (cm.getCardType().equals(ct) && !(cm.isOnlyOneAsset()))
				{
					//stampa a video l'elenco degli asset, selezionane uno 
					
				}
				
			
			}
			
		}
		
		if (ct.equals(CardTypeEnum.CHARACTER))
			
		{
			CharacterCard currCharacterCard = (CharacterCard) d;
			
			for (CardModifier cm : p.getCardModifiers())
			{
				if (cm.getCardType().equals(ct))
					
				for (Asset a : cm.getAssetDiscount())
				{
					
					if (a.getType().equals(AssetType.COIN))
					{
						currCharacterCard.getCoinsCost().setValue(currCharacterCard.getCoinsCost().getValue() + a.getValue());
					}
					
				}
			
			}
	
		}
		
	}
	
	
	
	
	
	
	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) 
	{
		Tower tower = null;
		
		for (Tower t : gameBoard.getTowers())
		{
			if (t.getTowerType().equals(cardType))
			{
				tower=t;
			}
			
		}
		
		ApplyDiceChanges(player);
		
		if (this.cardType.equals(CardTypeEnum.CHARACTER))
		{
			
			
			if (player.getPlayerBoard().getCharacters().size() > 6 || diceValueCharacter < tower.getTowerCells().get(floor).getRequiredDiceValue())
			{
				
				return false;
			}
			
			CharacterCard currCharacterCard = (CharacterCard) tower.getTowerCells().get(floor).getDevelopmentCard();
			
			ApplyChanges (currCharacterCard, player, CardTypeEnum.CHARACTER);
			
			if (currCharacterCard.getCoinsCost().getValue() < player.getCoins())
			{
				
				RemoveChanges(currCharacterCard, player, CardTypeEnum.CHARACTER);
				
				return false;
			}
			
			RemoveChanges(currCharacterCard, player, CardTypeEnum.CHARACTER);
				
		}
		
		if (this.cardType.equals(CardTypeEnum.BUILDING)) 
		{
			if (player.getPlayerBoard().getBuildings().size() > 6 || diceValueBuilding < tower.getTowerCells().get(floor).getRequiredDiceValue())
			{
				
				return false;
			}
	
			BuildingCard currBuildingCard = (BuildingCard) tower.getTowerCells().get(floor).getDevelopmentCard();
			
			ApplyChanges (currBuildingCard, player, CardTypeEnum.BUILDING);
			
			for (Asset a : currBuildingCard.getCosts())
			{
				if (player.getAsset(a.getType()) < a.getValue())
					
					RemoveChanges(currBuildingCard, player, CardTypeEnum.BUILDING);
					
					return false;
			}
			
			RemoveChanges(currBuildingCard, player, CardTypeEnum.CHARACTER);
	
		}
		
		if (this.cardType.equals(CardTypeEnum.VENTURE))
		{
			if (player.getPlayerBoard().getBuildings().size() > 6 || diceValueVenture < tower.getTowerCells().get(floor).getRequiredDiceValue())
			{				
				return false;
			}
			
			VentureCard currVentureCard = (VentureCard) tower.getTowerCells().get(floor).getDevelopmentCard();
			
			if (currVentureCard.getMilitaryPointsRequired().getValue() < player.getMilitaryPoints())
			
				return false;
		
		}
		
		if (this.cardType.equals(CardTypeEnum.TERRITORY)) 
		{
			
			if (player.getPlayerBoard().getTerritories().size() > 6 || diceValueTerritory < tower.getTowerCells().get(floor).getRequiredDiceValue())
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

		return true;
	}
	
	
	
	private void removeCards(Tower t, int floor)
	{
		t.getTowerCells().get(floor).setDevelopmentCard(null);
	}
	
	
	private void activeEffects(DevelopmentCard d, Player p, GameBoard gb)
	{
		for (Effect e : d.getImmediateEffects())
		{
				e.executeEffect(p, null);
				
				if ((e instanceof AddAsset) && p.isSantaRita())
					e.executeEffect(p, gb);
		}
			
	}
	
	
	@Override
	public void executeEffect(Player player, GameBoard gameBoard) 
	{
		if (floor == -1)
			
			//ask for floor 
			
			floor = player.getAdapter().askFloor();

		if (cardType.equals(CardTypeEnum.ANY))
			
			//ask for card type
			
			cardType = player.getAdapter().askForCardType();
			
		if (isLegal(player, gameBoard))
		{
				for (Tower t : gameBoard.getTowers())
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
						
						activeEffects(t.getTowerCells().get(floor).getDevelopmentCard(), player, gameBoard);
						
						removeCards(t, floor);
						
					}
				}
		}
	
	}
	
}
