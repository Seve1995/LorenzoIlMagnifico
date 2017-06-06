package it.polimi.ingsw.pc22.actions;


import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.effects.PickTowerCard;
import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

public class SettingFamiliarMemberOnTower extends Action {
	private int floor;
	private CardTypeEnum cardTypeEnum;

	public SettingFamiliarMemberOnTower(){}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public CardTypeEnum getCardTypeEnum() {
		return cardTypeEnum;
	}

	public void setCardTypeEnum(CardTypeEnum cardTypeEnum) {
		this.cardTypeEnum = cardTypeEnum;
	}

	public SettingFamiliarMemberOnTower (FamilyMember familyMember, CardTypeEnum cardTypeEnum, int floor)
	{
		super(familyMember);
		this.floor = floor;
		this.cardTypeEnum = cardTypeEnum;
	}
	
	private boolean PayThreeCoins (Player p, Tower t)
	{
	
		for (TowerCell tc : t.getTowerCells())
		{
			FamilyMember currFM = tc.getFamilyMember();
		
			if(currFM !=null && !(currFM.getPlayerColor().equals(p.getPlayerColorsEnum())))
			{
				return true;
			}	
		}
		
		return false;
			
	}
	
	private boolean AlreadySetAMember(Player p, Tower t)
	{
		for (TowerCell tc : t.getTowerCells())
		{
			FamilyMember currFM = tc.getFamilyMember();
		
			if(currFM !=null && (currFM.getPlayerColor().equals(p.getPlayerColorsEnum())) && currFM.getColor().equals(ColorsEnum.NEUTER) 
					|| super.getFamilyMember().getColor().equals(ColorsEnum.NEUTER))
			{
				return false;
			}	 
		}
		
		return true;
		
	}
	
	private Tower selectedTower(CardTypeEnum c, Tower[] ts)
	{
		
		for (Tower t : ts)
		{
			if (t.getTowerType().equals(cardTypeEnum))
				
				return t;
		}
		
		return null;
		
	}
	

	@Override
	protected boolean isLegal (Player player, GameBoard gameBoard)
	{
		Tower tower = selectedTower(cardTypeEnum, gameBoard.getTowers()); 
		
		PickTowerCard pickTowerCard = new PickTowerCard(floor, tower.getTowerType(), familyMember.getValue());
		
		if (!tower.getTowerCells().get(floor).isEmpty() && !(player.isDontCareOccupiedPlaces())) return false;
		
		int familiarValue = super.getFamilyMember().getValue();
		
		if (tower.getTowerCells().get(floor).getRequiredDiceValue() > familiarValue) return false;
		
		if(AlreadySetAMember(player, tower)) return false;
		
		if(PayThreeCoins(player, tower) && player.getCoins() < 3 && !(player.isDontPayThreeCoinsInTowers())) return false;
		
		if (!(pickTowerCard.isLegal(player, gameBoard))) return false;
		
		
		return true;
	}
	
	
	@Override
	public boolean executeAction (Player player, GameBoard gameBoard) {
		
		Tower tower = selectedTower(cardTypeEnum, gameBoard.getTowers()); 
		
		List<Effect> currEffects;
		
		PickTowerCard pickTowerCard = new PickTowerCard(floor, tower.getTowerType(), familyMember.getValue());
		
		if (!isLegal(player, gameBoard))
			return false;

		if (PayThreeCoins(player, tower))
		{
			player.setCoins(player.getCoins() - 3);
		}

		tower.getTowerCells().get(floor).setFamilyMember(this.getFamilyMember());

		familyMember.setPlayed(true);

		currEffects = tower.getTowerCells().get(floor).getEffects();

		for (Effect e : currEffects)
		{
			e.executeEffect(player, gameBoard);
		}

		pickTowerCard.executeEffect(player, gameBoard);

		return true;
		
	}
	
}
