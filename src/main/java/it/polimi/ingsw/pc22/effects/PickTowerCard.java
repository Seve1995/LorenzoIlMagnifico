package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.player.CardModifier;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.utils.MilitaryPointsCalc;

import java.util.ArrayList;
import java.util.List;

public class PickTowerCard implements Effect
{
	private int floor;
	private CardTypeEnum cardType;
	private int diceValue;
	private List<Asset> assetsDiscount;
	
	private List<Asset> costs = new ArrayList<Asset>();

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
	
	@Override
	public boolean isLegal(Player player, GameBoard gameBoard)
	{
		
		if (floor==-1 || cardType==null) return false;
		
		Tower tower = null;
		
		for (Tower t : gameBoard.getTowers())
		{
			if (t.getTowerType().equals(cardType))
			{
				tower=t;
			}
			
		}
		
		if(tower.getTowerCells().get(floor).getDevelopmentCard() == null) return false;
		
		for(CardModifier c : player.getCardModifiers())
		{
			if (c.getCardType().equals(cardType)) 
				diceValue += c.getValueModifier();
		}
		
		//applyDiceChanges(player);
		
		if (this.cardType.equals(CardTypeEnum.CHARACTER))
		{
			if (player.getPlayerBoard().getCharacters().size() > 6 || diceValue < tower.getTowerCells().get(floor).getRequiredDiceValue())
				return false;
			
			CharacterCard currCharacterCard = (CharacterCard) tower.getTowerCells().get(floor).getDevelopmentCard();
			
			costs.add(currCharacterCard.getCoinsCost());
			
			if (assetsDiscount!=null)
			{
				for (Asset asset : assetsDiscount)
					if (asset.getType().equals(costs.get(0).getType()))
							costs.get(0).setValue(costs.get(0).getValue() - asset.getValue());
			}
			
			if (applyCardModifiers(player)==false) 
				return false;
			
			if (costs.get(0).getValue() > player.getCoins())
				return false;
			
			return true;
		}
		
		if (this.cardType.equals(CardTypeEnum.BUILDING)) 
		{
			if (player.getPlayerBoard().getBuildings().size() > 6 || diceValue < tower.getTowerCells().get(floor).getRequiredDiceValue())
				return false;
	
			BuildingCard currBuildingCard = (BuildingCard) tower.getTowerCells().get(floor).getDevelopmentCard();
			
			costs.addAll(currBuildingCard.getCosts());
			
			if (assetsDiscount!=null)
			{
				for (Asset asset : assetsDiscount)
					for (Asset asset1 : costs)
						if (asset.getType().equals(asset1.getType()))
							asset1.setValue(asset1.getValue() - asset.getValue());
			}
			
			if (applyCardModifiers(player)==false) return false;
			
			for (Asset a : costs)
			{
				if (a.getValue() > player.getAsset(a.getType()))
				{
					return false;
					
				}
			}
			
			return true;
		}
		
		if (this.cardType.equals(CardTypeEnum.VENTURE))
		{
			if (player.getPlayerBoard().getBuildings().size() > 6 || diceValue < tower.getTowerCells().get(floor).getRequiredDiceValue())
				return false;
			
			VentureCard currVentureCard = (VentureCard) tower.getTowerCells().get(floor).getDevelopmentCard();
			
			if (currVentureCard.isRequiredCostChoice())
			{
				int choice = player.getAdapter().chooseCost(currVentureCard.getMilitaryPointsRequired(), currVentureCard.getMilitaryPointsCost(), currVentureCard.getResourcesCost());
			
				switch (choice) {
				case 1:
					costs.add(currVentureCard.getMilitaryPointsCost());
					break;
				case 2:
					costs.addAll(currVentureCard.getResourcesCost());
					break;
				default:
					return false;
				}
				
			}
			
			if (assetsDiscount!=null)
			{
				for (Asset asset : assetsDiscount)
					for (Asset asset1 : costs)
						if (asset.getType().equals(asset1.getType()))
							asset1.setValue(asset1.getValue() - asset.getValue());
			}
			
			if (currVentureCard.getMilitaryPointsRequired() == null)
				costs.addAll(currVentureCard.getResourcesCost());
			
			if (currVentureCard.getResourcesCost() == null)
				costs.add(currVentureCard.getMilitaryPointsCost());
				
			if (applyCardModifiers(player)==false) return false;

			for (Asset a : costs)
			{
				if (a.getValue() > player.getAsset(a.getType()))
				{
					return false;
					
				}
			
			}
			
			return true;
			
		}
		
		if (this.cardType.equals(CardTypeEnum.TERRITORY)) 
		{
			
			if (player.getPlayerBoard().getTerritories().size() > 6 || diceValue < tower.getTowerCells().get(floor).getRequiredDiceValue())
			{
				return false;
			}
			
			if (!(player.isNoMilitaryPointsForTerritories())) 
			
			{
				int value = player.getPlayerBoard().getTerritories().size();
						
				MilitaryPointsCalc militaryPointsCalc = MilitaryPointsCalc.getMilitaryPointsCalcByValue(value);

				int militaryPoints = militaryPointsCalc.getMilitaryPoints();

				if (player.getMilitaryPoints() < militaryPoints)
				{
					return false;
				}
				
			}
			
			return true;
		}
		
		return true;
	
	}
	
	
	@Override
	public boolean executeEffects(Player player, GameBoard gameBoard)
	{		
		if (cardType.equals(CardTypeEnum.ANY))
			
			cardType = player.getAdapter().askForCardType();

		if (floor == -1)
			
			floor = player.getAdapter().askFloor();
			
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
						
						t.getTowerCells().get(floor).setDevelopmentCard(null);
						
					}
					
				}
				return true;
				
		}
				
		return false;
	
	}
	
	
	
	
	private boolean applyCardModifiers(Player p)
	
	{
		if (cardType.equals(CardTypeEnum.BUILDING))
			
		{			
			for (CardModifier cm : p.getCardModifiers())
			{
				if (cm.getCardType().equals(cardType))
				{
					if (cm.isOnlyOneAsset())
						{
						
						List<Asset> chosenAssets = p.getAdapter().chooseAssets(1, cm.getAssetDiscount());
						
						if (chosenAssets==null) return false;

						Asset chosenAsset = chosenAssets.get(0);
						
						for (Asset a1 : costs)
						{
						
							if (a1.getType().equals(chosenAsset.getType()))
							
								costs.add(new Asset(a1.getValue() - chosenAsset.getValue(), a1.getType()));
						}
						
						continue;
						
						}
						
					for (Asset a : cm.getAssetDiscount())
					{
						for (Asset a1 : costs)
						{
							if (a1.getType().equals(a.getType()))
							
								a1.setValue((a1.getValue() - a.getValue()));
							
						}
					
					}
					
				}
			
			}	
				
		}
		
		if (cardType.equals(CardTypeEnum.CHARACTER))
			
		{			
				Asset coinsCost = costs.get(0);
				
				for (CardModifier cm : p.getCardModifiers())
				{
					if (cm.getCardType().equals(cardType))
					
						for (Asset a : cm.getAssetDiscount())
						{
					
							if (a.getType().equals(AssetType.COIN))
							{
								costs.get(0).setValue(coinsCost.getValue() - a.getValue());
							}
					
						}
			
				}
	
		}
		
		if (cardType.equals(CardTypeEnum.VENTURE))
			
		{
			
			for (CardModifier cm : p.getCardModifiers())
			{
				if (cm.getCardType().equals(cardType))
				{
					
					for (Asset a : cm.getAssetDiscount())
					{
						for (Asset a1 : costs)
						{
						
							if (a1.getType().equals(a.getType()))
							
								a1.setValue(a1.getValue() - a.getValue());	
						}
					
					}
					
				}
			
				}	
				
			}
		
		return true;
		
	}

	
	/*
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
					
					else if (cm.getCardType().equals(ct) && (cm.isOnlyOneAsset()))
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
					{
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
			
			if (ct.equals(CardTypeEnum.VENTURE))
			{
				VentureCard currVentureCard = (VentureCard) d;
				
				for (CardModifier cm : p.getCardModifiers())
				{
					if (cm.getCardType().equals(ct) && !(cm.isOnlyOneAsset()))
					{
						
						for (Asset a : cm.getAssetDiscount())
						{
							for (Asset a1 : currVentureCard.getResourcesCost())
							{
								if (a1.getType().equals(a.getType()))
								
									a1.setValue(a1.getValue() + a.getValue());	
							}
						}
					}
				}
			}
	}
	 */

	
	
	private void activeEffects(DevelopmentCard d, Player p, GameBoard gb)
	{
		List<Effect> immediateEffects = d.getImmediateEffects();

		if (immediateEffects == null) return;

		for (Effect e : immediateEffects)
		{
				e.executeEffects(p, null);
				
				if ((e instanceof AddAsset) && p.isSantaRita())
					
					e.executeEffects(p, gb);
		}
			
	}
	
}
