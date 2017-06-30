package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.adapters.SocketIOAdapter;
import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.messages.ChooseAssetsMessage;
import it.polimi.ingsw.pc22.player.CardModifier;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.utils.MilitaryPointsCalc;

import java.util.ArrayList;
import java.util.List;

public class PickTowerCard extends ChooseAsset implements Effect
{
	private int floor;
	private CardTypeEnum cardType;
	private int diceValue;
	private List<Asset> assetsDiscount; //This contains the assets 
	private List<Asset> costs = new ArrayList<Asset>(); //Auxiliary arraylist that storage the card's cost
	private List<CardModifier> currentCardModifiers = new ArrayList<CardModifier>(); //Auxiliary arraylist that storage the card modifiers associated with the cardType

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
			{
				currentCardModifiers.add(c);
				diceValue += c.getValueModifier();
			}
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
		GameMatch.getCurrentGameBoard().setCurreEffect(this);

		if (cardType.equals(CardTypeEnum.ANY))
			
			cardType = player.getAdapter().askForCardType();

		if (floor == -1)
			
			floor = player.getAdapter().askFloor();

		Tower currTower = new Tower(CardTypeEnum.ANY);

		for (Tower t : gameBoard.getTowers())
		{
			if (t.getTowerType().equals(cardType))
			{
				currTower = t;
			}
		}

		if (isLegal(player, gameBoard))
		{

			if (cardType.equals(CardTypeEnum.BUILDING))
			{
				player.getPlayerBoard().getBuildings().add((BuildingCard) currTower.getTowerCells().get(floor).getDevelopmentCard());

			}

			if (cardType.equals(CardTypeEnum.CHARACTER))
			{
				player.getPlayerBoard().getCharacters().add((CharacterCard) currTower.getTowerCells().get(floor).getDevelopmentCard());

			}

			if (cardType.equals(CardTypeEnum.TERRITORY))
			{
				player.getPlayerBoard().getTerritories().add((TerritoryCard) currTower.getTowerCells().get(floor).getDevelopmentCard());

			}

			if (cardType.equals(CardTypeEnum.VENTURE))
			{
				player.getPlayerBoard().getVentures().add((VentureCard) currTower.getTowerCells().get(floor).getDevelopmentCard());

			}

			activeEffects(currTower.getTowerCells().get(floor).getDevelopmentCard(), player, gameBoard);

			currTower.getTowerCells().get(floor).setDevelopmentCard(null);

			return true;
		}


		return false;
	
	}
	

	private boolean applyCardModifiers(Player player)
	{
		if (cardType.equals(CardTypeEnum.BUILDING))
		{			
			for (CardModifier cm : currentCardModifiers)
			{		
				if (cm.isOnlyOneAsset())
				{
					List<Asset> assets = cm.getAssetDiscount();

					IOAdapter adapter = player.getAdapter();

					adapter.printMessage(new ChooseAssetsMessage(assets));

					if (adapter instanceof SocketIOAdapter)
						new Thread(new ReceiveAssetDecisionThread(assets)).start();

					if (!super.waitForResult()) return false;

					Asset chosenAsset = assets.get(getChosenAssetsToPay());
					
					for (Asset cost : costs)
					{
						if (cost.getType().equals(chosenAsset.getType()))
							costs.add(new Asset(cost.getValue() - chosenAsset.getValue(), cost.getType()));
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
		
		if (cardType.equals(CardTypeEnum.CHARACTER))
		{			
			Asset coinsCost = costs.get(0);

			for (CardModifier cm : currentCardModifiers)
			{
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
			for (CardModifier cm : currentCardModifiers)
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
		return true;
	}

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
