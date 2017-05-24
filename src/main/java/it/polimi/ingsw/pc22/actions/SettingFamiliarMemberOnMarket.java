package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.Market;
import it.polimi.ingsw.pc22.gamebox.MarketCell;
import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

public class SettingFamiliarMemberOnMarket extends Action{
	private Market market;
	private int zone;

	public SettingFamiliarMemberOnMarket(FamilyMember familyMember, Market market, int zone) {
		super(familyMember);
		this.market = market;
		this.zone = zone;
	}

	@Override
	public boolean isLegal(Player player) {
		
		List<MarketCell> currMarketCells = market.getMarketCells();
		
		if(currMarketCells.get(zone).getRequiredDiceValue() < super.getFamilyMember().getFamiliarValue())
			return false;
		
		if(!currMarketCells.get(zone).isEmpty())
			return false;
		
		return true;
	}
	
	@Override
	public void executeAction(Player player) {
		// TODO Auto-generated method stub
	}
	

}
