package it.polimi.ingsw.pc22.actions;

import java.util.List;
import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.Market;
import it.polimi.ingsw.pc22.gamebox.MarketCell;
import it.polimi.ingsw.pc22.player.Player;

public class SettingFamiliarMemberOnMarket extends Action{
	private Market market;
	private int zone;

	public SettingFamiliarMemberOnMarket(FamilyMember familyMember, Market market, int zone) {
		super(familyMember);
		this.market = market;
		this.zone = zone;
	}

	@Override
	protected boolean isLegal(Player player) {
		
		List<MarketCell> currMarketCells = market.getMarketCells();
		
		if(currMarketCells.get(zone).getRequiredDiceValue() < super.getFamilyMember().getFamiliarValue())
			return false;
		
		if(!currMarketCells.get(zone).isEmpty())
			return false;
		
		return true;
	}
	
	@Override
	public boolean executeAction(Player player) {
		List<Effect> currEffects;
		
		
		if (isLegal(player))
		{
			market.getMarketCells().get(zone).setFamilyMember(this.getFamilyMember());
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
