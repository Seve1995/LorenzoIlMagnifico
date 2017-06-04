package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.BuildingCard;
import it.polimi.ingsw.pc22.player.Player;

public class DoHarvestAction implements Effect {
	
	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public boolean isLegal(Player player) {
		
		return true;
	}

	@Override
	public void executeEffect(Player player) {
		
		if (value >= player.getPlayerBoard().getBonusTile().getHarvestActivationValue())
		{
		
			player.setCoins(player.getCoins() + player.getPlayerBoard().getBonusTile().getHarvestCoinsBonus());
			player.setMilitaryPoints(player.getMilitaryPoints() + player.getPlayerBoard().getBonusTile().getHarvestMilitaryPointsBonus());
			player.setServants(player.getServants() + player.getPlayerBoard().getBonusTile().getHarvestServantBonus() );
			player.setStones(player.getServants()+player.getPlayerBoard().getBonusTile().getHarvestStonesBonus());
			player.setWoods(player.getWoods()+player.getPlayerBoard().getBonusTile().getHarvestWoodsBonus());
		
		}
		for (BuildingCard b : player.getPlayerBoard().getBuildings()){
			
			if(value >= b.getPermanentEffectActivationCost()){
				
				for (Effect e : b.getPermanentEffects())
				{
					e.executeEffect(player);
				}
				
			}
		}
		
	}
	

}
