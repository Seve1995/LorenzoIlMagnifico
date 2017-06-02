package it.polimi.ingsw.pc22.actions;


import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.effects.PickTowerCard;
import it.polimi.ingsw.pc22.gamebox.ColorsEnum;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.Tower;
import it.polimi.ingsw.pc22.gamebox.TowerCell;
import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

public class SettingFamiliarMemberOnTower extends Action {
	private int floor;
	private Tower tower; 
	
	public SettingFamiliarMemberOnTower (FamilyMember familyMember, Tower tower, int floor) 
	{
		super(familyMember);
		this.floor = floor;
		this.tower = tower;
	}

	@Override
	protected boolean isLegal (Player player)
	{
		
		PickTowerCard pickTowerCard = new PickTowerCard(floor, tower.getTowerType(), familyMember.getFamiliarValue());
		
		//if (floor > 4 || floor < 1) return false; TODO check validazione dell'input
		
		if (!tower.getTowerCells().get(floor).isEmpty() && !(player.isDontCareOccupiedPlaces())) return false;
		
		int familiarValue = super.getFamilyMember().getFamiliarValue();
		
		if (tower.getTowerCells().get(floor).getRequiredDiceValue() > familiarValue) return false;
		
		boolean thereIsAnotherFamilyMember = false;
		
		for (TowerCell towerCell : tower.getTowerCells())
		{
			FamilyMember currFamilyMember = towerCell.getFamilyMember();
			
			if (currFamilyMember == null) continue;
			
			thereIsAnotherFamilyMember = true;
			
			if
			(
					currFamilyMember.getColor()==ColorsEnum.NEUTER ||
					super.getFamilyMember().getColor() == ColorsEnum.NEUTER
			)
				break;
			
			if (player.getFamilyMember().contains(currFamilyMember))
				return false;
			
		}
		
		if (thereIsAnotherFamilyMember && !(player.isDontPayThreeCoinsInTowers()) && player.getCoins() < 3)
			return false;
		
		if ((thereIsAnotherFamilyMember && (player.isDontPayThreeCoinsInTowers())) 
				||  (!tower.getTowerCells().get(floor).isEmpty() && (player.isDontCareOccupiedPlaces())))
			if (!(pickTowerCard.isLegal(player)))
				return false;
				
		return true;
		
	}
	
	@Override
	public boolean executeAction (Player player) {
		
		List<Effect> currEffects;
		
		PickTowerCard pickTowerCard = new PickTowerCard(floor, tower.getTowerType(), familyMember.getFamiliarValue());
		
		if (isLegal(player))
		{
			
			this.tower.getTowerCells().get(floor).setFamilyMember(this.getFamilyMember());
			
			player.removeFamilyMember(familyMember);
			
			currEffects = this.tower.getTowerCells().get(floor).getEffects();
				
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
