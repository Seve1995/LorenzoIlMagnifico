package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.gamebox.Market;
import it.polimi.ingsw.pc22.gamebox.MarketCell;
import it.polimi.ingsw.pc22.player.Player;

import java.util.ArrayList;
import java.util.List;

public class SettingFamiliarMemberOnMarket extends Action{
	
	private int zone;

	public SettingFamiliarMemberOnMarket(){}

	public SettingFamiliarMemberOnMarket(FamilyMember familyMember, int zone) {
		
		super(familyMember);
		this.zone = zone;
		
	}

	public int getZone() {
		return zone;
	}

	public void setZone(int zone) {
		this.zone = zone;
	}

	@Override
	protected boolean isLegal(Player player, GameBoard gameBoard) {
		
		Market market = gameBoard.getMarket(); 
		
		List<MarketCell> currMarketCells = market.getMarketCells();
		
		if(currMarketCells.get(zone).getRequiredDiceValue() > familyMember.getValue())
			return false;
		
		if (!currMarketCells.get(zone).isEmpty() && player.isDontCareOccupiedPlaces())
			return true;

		if(!currMarketCells.get(zone).isEmpty())
			return false;		
		
		if (player.isDisableMarket())
			return false;
		
		return true;
	}
	
	@Override
	public boolean executeAction(Player player, GameBoard gameBoard) {
		
		List<Effect> currEffects = new ArrayList<>();
		
		Market market = gameBoard.getMarket();
		
		if (isLegal(player, gameBoard))
		{
			market.getMarketCells().get(zone).setFamilyMember(familyMember);
			
			familyMember.setPlayed(true);
			
			currEffects = market.getMarketCells().get(zone).getEffects();
			
			for (Effect e : currEffects)
			{
				
				e.executeEffect(player, gameBoard);
				
			}
			
			return true;
		}
		
		else 
		{
			return false;
		}
		
	}
	

}
