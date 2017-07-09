package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.adapters.SocketIOAdapter;
import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.connection.GameServer;
import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.messages.ChooseAssetsMessage;
import it.polimi.ingsw.pc22.messages.ChooseCardMessage;
import it.polimi.ingsw.pc22.messages.ChooseCostsMessage;
import it.polimi.ingsw.pc22.player.CardModifier;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.utils.MilitaryPointsCalc;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This effect is quite complex, full of rules and conditions.
 * It represents the fact that a player, in some condition,
 * for example, for the effect of a card, or setting a familyMember,
 * can take a certain card from a tower.
 */

public class PickTowerCard extends ChooseAsset implements Effect
{
	private int floor;
	private CardTypeEnum cardType;
	private int diceValue;
	//This contains the assets
	private List<Asset> assetsDiscount;
	//Auxiliary arrayList that storage the card's cost
	private List<Asset> costs = new ArrayList<>();
	//Auxiliary arrayList that storage the card modifiers associated with the cardType
	private transient List<CardModifier> currentCardModifiers = new ArrayList<>();

	private static final Logger LOGGER = Logger.getLogger(PickTowerCard.class.getName());

	private Integer costDecision = null;

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

	public void setDiceValue(int diceValue) {
		this.diceValue = diceValue;
	}

	public void setAssetsDiscount(List<Asset> assetsDiscount) {
		this.assetsDiscount = assetsDiscount;
	}

	public void setCostDecision(Integer costDecision)
	{
		this.costDecision = costDecision;
	}

	public PickTowerCard() {}

	public PickTowerCard(int floor, CardTypeEnum cardType, int diceValue)
	{
		this.floor = floor;
		this.cardType = cardType;
		this.diceValue = diceValue;
	}
	
	@Override
	public boolean isLegal(Player player, GameBoard gameBoard)
	{
		String gameName = gameBoard.getGameMatchName();

		GameMatch gameMatch = GameServer.getGameMatchMap().get(gameName);

		gameMatch.setCurrEffect(this);

		if (floor==-1 || cardType==null)
			return false;
		
		Tower tower = gameBoard.getTowerByType(cardType);
		
		if(tower.getTowerCells().get(floor).getDevelopmentCard() == null)
			return false;
		
		for(CardModifier c : player.getCardModifiers())
		{
			if (c.getCardType().equals(cardType))
			{
				currentCardModifiers.add(c);
				diceValue += c.getValueModifier();
			}
		}

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
			
			if (applyCardModifiers(player, gameName) == false)
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
			
			if (applyCardModifiers(player, gameName)==false)
				return false;
			
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

			if (currVentureCard.isRequiredCostChoice() && currVentureCard != null)
			{

				if (costDecision == 0 && player.getMilitaryPoints() < currVentureCard.getMilitaryPointsCost().getValue())
					return false;

				if (costDecision == 1)
				{
					for (Asset asset : currVentureCard.getResourcesCost())
					{
						if (asset.getValue() > player.getAsset(asset.getType()))
						{
							return false;
						}
					}
				}
			}

			if (assetsDiscount!=null)
			{
				for (Asset asset : assetsDiscount)
					for (Asset asset1 : costs)
						if (asset.getType().equals(asset1.getType()))
							asset1.setValue(asset1.getValue() - asset.getValue());
			}
			
				
			if (!applyCardModifiers(player, gameName)) return false;

			if (!currVentureCard.isRequiredCostChoice())
			{
				if (currVentureCard.getMilitaryPointsRequired() == null)
					costs.addAll(currVentureCard.getResourcesCost());
				
				if (currVentureCard.getResourcesCost() == null)
					costs.add(currVentureCard.getMilitaryPointsCost());

				for (Asset a : costs)
				{
					if (a.getValue() > player.getAsset(a.getType()))
					{
						return false;
					}
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
		String gameName = gameBoard.getGameMatchName();

		Tower tower = gameBoard.getTowerByType(cardType);
		
		GameMatch gameMatch = GameServer.getGameMatchMap().get(gameName);

		gameMatch.setCurrEffect(this);

		if (cardType.equals(CardTypeEnum.ANY) || floor == -1)
		{
			IOAdapter adapter = player.getAdapter();

			adapter.printMessage(new ChooseCardMessage(cardType, gameBoard));

			if (adapter instanceof SocketIOAdapter)
				new Thread(new ReceiveCardDecisionThread(gameName)).start();

			Long timestamp = System.currentTimeMillis();

			Long timeout = GameMatch.getTimeout();

			while (System.currentTimeMillis() < timestamp + timeout)
			{
				try
				{
					Thread.sleep(100L);
				}
					catch (InterruptedException e)
				{
					LOGGER.log(Level.WARNING, "Interrupted!", e);
					Thread.currentThread().interrupt();
				}

				if (!cardType.equals(CardTypeEnum.ANY) && floor != -1)
				{
					break;
				}
			}
		}

		DevelopmentCard card = tower.getTowerCells().get(floor).getDevelopmentCard();

		if (card instanceof VentureCard && ((VentureCard)card).isRequiredCostChoice())
		{
			VentureCard currVentureCard = (VentureCard) card;

			IOAdapter adapter = player.getAdapter();

			Asset militaryPointsRequired = currVentureCard.getMilitaryPointsRequired();
			Asset militaryPointsCost = currVentureCard.getMilitaryPointsCost();
			List<Asset> resourcesCost = currVentureCard.getResourcesCost();

			ChooseCostsMessage costsMessage = new ChooseCostsMessage
					(militaryPointsRequired, militaryPointsCost, resourcesCost);

			adapter.printMessage(costsMessage);

			if (adapter instanceof SocketIOAdapter)
				new Thread(new ReceiveCostsDecisionThread(gameName)).start();

			Long timestamp = System.currentTimeMillis();

			Long timeout = GameMatch.getTimeout();

			while (System.currentTimeMillis() < timestamp + timeout)
			{
				try
				{
					Thread.sleep(100L);
				}
				catch (InterruptedException e)
				{
					LOGGER.log(Level.WARNING, "Interrupted!", e);
					Thread.currentThread().interrupt();
				}

				if (costDecision != null)
				{
					break;
				}
			}

			if (costDecision == null)
				return false;
		}

		Tower currTower = gameBoard.getTowerByType(cardType);

		if (!isLegal(player, gameBoard))
			return false;

		activeEffects(currTower.getTowerCells().get(floor).getDevelopmentCard(), player, gameBoard);

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

			VentureCard currVentureCard = (VentureCard) card;
			
			if(currVentureCard.isRequiredCostChoice())
			{
				switch (costDecision)
				{
					case 0:
						costs.add(currVentureCard.getMilitaryPointsCost());
						break;
					case 1:
						costs.addAll(currVentureCard.getResourcesCost());
						break;
					default:
						return false;
				}
		
				costDecision = null;
			}
			
		}

		for (Asset asset : costs)
		{
			
			Asset costAsset = new Asset(-asset.getValue(), asset.getType());

			player.addAsset(costAsset);
		}

		currTower.getTowerCells().get(floor).setDevelopmentCard(null);

		return true;
	}
	

	private boolean applyCardModifiers(Player player, String gameName)
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
						new Thread(new ReceiveAssetDecisionThread(assets, gameName)).start();

					if (!super.waitForResult()) return false;

					Asset chosenAsset = assets.get(getChosenAssetsToPay());
					
					for (Asset cost : costs)
					{
						if (cost.getType().equals(chosenAsset.getType()))
							cost.setValue(cost.getValue() - chosenAsset.getValue());
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
				e.executeEffects(p, gb);
				
				if ((e instanceof AddAsset) && p.isSantaRita())

					e.executeEffects(p, gb);
		}
	}

}
