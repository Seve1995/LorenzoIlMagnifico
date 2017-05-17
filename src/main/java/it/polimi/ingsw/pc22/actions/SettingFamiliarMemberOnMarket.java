package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.Market;
import it.polimi.ingsw.pc22.gamebox.MarketCell;
import it.polimi.ingsw.pc22.player.Player;

public class SettingFamiliarMemberOnMarket extends Action{
	private FamilyMember familyMember;
	private Market market;
	private int zone;

	public SettingFamiliarMemberOnMarket(FamilyMember familyMember, Market market, int zone) {
		super();
		this.familyMember = familyMember;
		this.market = market;
		this.zone = zone;
	}

	@Override
	public boolean isLegal(Player player) {
		
		MarketCell[] currMarketCell = market.getMarketCell();
		
		if(currMarketCell[zone].getRequiredDiceValue() < familyMember.getFamiliarValue())
			return false;
		
		if(!currMarketCell[zone].isEmpty())
			return false;
		
		return true;
	}
	
	@Override
	public void executeAction(Player player) {
		// TODO Auto-generated method stub
		
	}
	

}
