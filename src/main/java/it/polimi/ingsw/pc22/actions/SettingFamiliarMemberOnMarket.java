package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.gamebox.Market;
import it.polimi.ingsw.pc22.gamebox.MarketCell;
import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

public class SettingFamiliarMemberOnMarket extends Action
{
	private int zone;

	public SettingFamiliarMemberOnMarket(){}

	public SettingFamiliarMemberOnMarket(FamilyMember familyMember, int zone)
	{
		super(familyMember);
		this.zone = zone;
	}

	public void setZone(int zone) {
		this.zone = zone;
	}

	@Override
    public boolean isLegal(Player player, GameBoard gameBoard)
	{
		Market market = gameBoard.getMarket(); 
		
		List<MarketCell> currMarketCells = market.getMarketCells();

		if (player.isDisableMarket())
			return false;

		if (currMarketCells.get(zone).isABlockedCell())
		{
			return false;
		}

		if (currMarketCells.get(zone).getRequiredDiceValue() > familyMember.getValue())
			return false;
		
		if (!currMarketCells.get(zone).isEmpty() && !player.isDontCareOccupiedPlaces())
			return false;

		return true;
	}
	
	@Override
	public boolean executeAction(Player player, GameBoard gameBoard)
	{
		Market market = gameBoard.getMarket();
		
		if (!isLegal(player, gameBoard))
			return false;

		market.getMarketCells().get(zone).setFamilyMember(familyMember);

		familyMember.setPlayed(true);

		List<Effect> currEffects = market.getMarketCells().get(zone).getEffects();

		for (Effect e : currEffects)
		{
			e.executeEffects(player, gameBoard);
		}

		player.setFamiliarPositioned(true);

		return true;
		
	}
	

}
