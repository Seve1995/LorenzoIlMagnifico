package it.polimi.ingsw.pc22.actions;


import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.effects.PickTowerCard;
import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

/**
 * This action is used to play a familiar on the the tower section
 * it checks that the requirements are satisfied and executes
 * the action
 */

public class SettingFamiliarMemberOnTower extends Action
{
	private int floor;
	private CardTypeEnum cardTypeEnum;

	private PickTowerCard pickTowerCard = null;

	public int getFloor()
	{
		return floor;
	}

	public void setFloor(int floor)
	{
		this.floor = floor;
	}

	public void setCardTypeEnum(CardTypeEnum cardTypeEnum)
	{
		this.cardTypeEnum = cardTypeEnum;
	}

	public SettingFamiliarMemberOnTower (FamilyMember familyMember, CardTypeEnum cardTypeEnum, int floor)
	{
		super(familyMember);
		this.floor = floor;
		this.cardTypeEnum = cardTypeEnum;
	}

	public SettingFamiliarMemberOnTower() {}

	private boolean payThreeCoins(Tower t)
	{
		for (TowerCell tc : t.getTowerCells())
			if (!tc.isEmpty()) return true;

		return false;
	}

    private boolean alreadySetAMember(Player p, Tower t)
    {
        if (t.getListPlayers().isEmpty())
        	return false;

        for (PlayerColorsEnum pc : t.getListPlayers())
        {
            if(p.getPlayerColorsEnum().equals(pc))
            {
                return true;
            }
        }

        return false;
    }

	private Tower selectedTower(Tower[] towers)
	{
		for (Tower t : towers)
		{
			if (t.getTowerType().equals(cardTypeEnum))
				return t;
		}
		
		return null;
		
	}

	@Override
	public boolean isLegal(Player player, GameBoard gameBoard)
	{
		Tower tower = selectedTower(gameBoard.getTowers());

		if (tower == null)
			return false;

		if (pickTowerCard == null)
		{
			pickTowerCard = new PickTowerCard(floor, tower.getTowerType(), familyMember.getValue());
		}

		if (!tower.getTowerCells().get(floor).isEmpty() && !(player.isDontCareOccupiedPlaces()))
			return false;
		
		int familiarValue = familyMember.getValue();

		if (tower.getTowerCells().get(floor).getRequiredDiceValue() > familiarValue)
			return false;

		if(alreadySetAMember(player, tower) && !(familyMember.getColor().equals(ColorsEnum.NEUTER)))
			return false;

		if((payThreeCoins(tower) && player.getCoins() < 3) && !player.isDontPayThreeCoinsInTowers())
			return false;
		
		return true;
	}
	
	
	@Override
	public boolean executeAction (Player player, GameBoard gameBoard)
	{
		Tower tower = selectedTower(gameBoard.getTowers());

		if (tower == null)
			return false;

		if (pickTowerCard == null)
		{
			pickTowerCard = new PickTowerCard(floor, tower.getTowerType(), familyMember.getValue());
		}

		if (!isLegal(player, gameBoard))
			return false;

		boolean executed = pickTowerCard.executeEffects(player, gameBoard);

		if (!executed)
			return false;
		
		if (payThreeCoins(tower))
		{
			player.setCoins(player.getCoins() - 3);
		}
		
		if(familyMember.getColor()!=ColorsEnum.NEUTER)
			tower.getListPlayers().add(player.getPlayerColorsEnum());
		
		List<Effect> currEffects = tower.getTowerCells().get(floor).getEffects();

		if (currEffects != null)
		{
			for (Effect e : currEffects)
			{
				e.executeEffects(player, gameBoard);
			}
		}

		player.setFamiliarPositioned(true);

		familyMember.setPlayed(true);

		tower.getTowerCells().get(floor).setFamilyMember(familyMember);

		return true;
	}

	public void setPickTowerCard(PickTowerCard pickTowerCard)
	{
		this.pickTowerCard = pickTowerCard;
	}
}
