package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.Market;
import it.polimi.ingsw.pc22.gamebox.MarketCell;
import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

public class SettingFamiliarMemberOnMarket extends Action{
	
	private int zone;

	public SettingFamiliarMemberOnMarket(FamilyMember familyMember, int zone) {
		super(familyMember);
		this.zone = zone;
	}

	@Override
	protected boolean isLegal(Player player) {
		
		Market market = this.gameBoard.getMarket(); 
		
		List<MarketCell> currMarketCells = market.getMarketCells();
		
		if(currMarketCells.get(zone).getRequiredDiceValue() < super.getFamilyMember().getFamiliarValue())
			return false;
		
		if(!currMarketCells.get(zone).isEmpty())
			return false;
		
		if (!currMarketCells.get(zone).isEmpty() && player.isDontCareOccupiedPlaces())
			return true;
		
		if (player.isDisableMarket())
			return false;
		
		return true;
	}
	
	@Override
	public boolean executeAction(Player player) {
		
		List<Effect> currEffects;
		
		Market market = this.gameBoard.getMarket(); 
		
		if (isLegal(player) )
		{
			market.getMarketCells().get(zone).setFamilyMember(this.getFamilyMember());
			
			player.removeFamilyMember(familyMember);
			
			currEffects = market.getMarketCells().get(zone).getEffects();
			
			for (Effect e : currEffects)
			{
				e.executeEffect(player);
			}
			
			return true;
		}
		
		else 
		{
			return false;
		}
		
	}
	

}
