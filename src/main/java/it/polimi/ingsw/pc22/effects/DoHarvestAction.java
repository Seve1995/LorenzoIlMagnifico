package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.BuildingCard;
import it.polimi.ingsw.pc22.player.Player;

public class DoHarvestAction implements Effect {

	@Override
	public boolean isLegal(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void executeEffect(Player player) {
		
		player.setCoins(player.getCoins() + player.getPlayerBoard().getBonusTile().getHarvestCoinsBonus());
		player.setMilitaryPoints(player.getMilitaryPoints() + player.getPlayerBoard().getBonusTile().getHarvestMilitaryPointsBonus());
		player.setServants(player.getServants() + player.getPlayerBoard().getBonusTile().getHarvestServantBonus() );
		player.setStones(player.getServants()+player.getPlayerBoard().getBonusTile().getHarvestStonesBonus());
		player.setWoods(player.getWoods()+player.getPlayerBoard().getBonusTile().getHarvestWoodsBonus());
	
		for (BuildingCard b : player.getPlayerBoard().getBuildings()){
			for (Effect e : b.getPermanentEffects())
			{
				e.executeEffect(player);
			}
		}
		
	}
	

}