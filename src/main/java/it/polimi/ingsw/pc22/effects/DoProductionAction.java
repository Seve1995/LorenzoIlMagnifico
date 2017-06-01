package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.TerritoryCard;
import it.polimi.ingsw.pc22.player.Player;

public class DoProductionAction implements Effect {
	
	private int value;

	@Override
	public boolean isLegal(Player player) {
		
		return true;
		
	}

	@Override
	public void executeEffect(Player player) {
			
			if (value >= player.getPlayerBoard().getBonusTile().getProductionActivationValue())
			{
				player.setServants(player.getServants() + player.getPlayerBoard().getBonusTile().getProductionServantBonus() );
				player.setCoins(player.getCoins() + player.getPlayerBoard().getBonusTile().getProductionCoinsBonus());
				player.setMilitaryPoints(player.getMilitaryPoints() + player.getPlayerBoard().getBonusTile().getProductionMilitaryPointsBonus());
			}
			
			for (TerritoryCard t : player.getPlayerBoard().getTerritories())
			{
				if (value >= t.getPermanentEffectActivationCost())
				{
					for (Effect e : t.getPermanentEffects())
					{
						e.executeEffect(player);
					}
				}
			}
	}
	
	

}
