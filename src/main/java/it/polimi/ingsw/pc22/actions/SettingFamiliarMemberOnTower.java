package it.polimi.ingsw.pc22.actions;


import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.effects.PickTowerCard;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.gamebox.ColorsEnum;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.Tower;
import it.polimi.ingsw.pc22.gamebox.TowerCell;
import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

public class SettingFamiliarMemberOnTower extends Action {
	private int floor;
	private CardTypeEnum cardTypeEnum;
	
	
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
	protected boolean isLegal (Player player)
	{
		
		Tower tower = selectedTower(cardTypeEnum, this.gameBoard.getTowers()); 
		
		PickTowerCard pickTowerCard = new PickTowerCard(floor, tower.getTowerType(), familyMember.getFamiliarValue());
		
		if (!tower.getTowerCells().get(floor).isEmpty() && !(player.isDontCareOccupiedPlaces())) return false;
		
		int familiarValue = super.getFamilyMember().getFamiliarValue();
		
		if (tower.getTowerCells().get(floor).getRequiredDiceValue() > familiarValue) return false;
		
		if(AlreadySetAMember(player, tower)) return false;
		
		if(PayThreeCoins(player, tower) && player.getCoins() < 3 && !(player.isDontPayThreeCoinsInTowers())) return false;
		
		if (!(pickTowerCard.isLegal(player))) return false;
		
		
		return true;
	}
	
	
	@Override
	public boolean executeAction (Player player) {
		
		Tower tower = selectedTower(cardTypeEnum, this.gameBoard.getTowers()); 
		
		List<Effect> currEffects;
		
		PickTowerCard pickTowerCard = new PickTowerCard(floor, tower.getTowerType(), familyMember.getFamiliarValue());
		
		if (isLegal(player))
		{
			if (PayThreeCoins(player, tower))
			{
				player.setCoins(player.getCoins() - 3);
			}
			
			tower.getTowerCells().get(floor).setFamilyMember(this.getFamilyMember());
			
			player.removeFamilyMember(familyMember);
			
			currEffects = tower.getTowerCells().get(floor).getEffects();
				
			for (Effect e : currEffects)
			{
				e.executeEffect(player);
			}
			
			pickTowerCard.executeEffect(player);
			
			return true;
		}

		else
		{
			
			return false;
		}
		
	}
	
}
