package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.gamebox.TerritoryCard;
import it.polimi.ingsw.pc22.player.Player;

public class DoProductionAction implements Effect {

	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) {
		if (value < player.getPlayerBoard().getBonusTile().getProductionActivationValue())
			return false;
		return true;
		
	}

	@Override
	public boolean executeEffect(Player player, GameBoard gameBoard) {
		
		if (isLegal(player, gameBoard))
		{
				player.addAsset(player.getPlayerBoard().getBonusTile().getProductionCoinsBonus());
				player.addAsset(player.getPlayerBoard().getBonusTile().getProductionMilitaryPointsBonus());
				player.addAsset(player.getPlayerBoard().getBonusTile().getProductionServantBonus());
			
			
			for (TerritoryCard t : player.getPlayerBoard().getTerritories())
			
			{
				if (value >= t.getPermanentEffectActivationCost())
				{
					for (Effect e : t.getPermanentEffects())
					{
						e.executeEffect(player, gameBoard);
					}
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	

}
