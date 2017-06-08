package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.BuildingCard;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
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
	public boolean isLegal(Player player, GameBoard gameBoard) {
		
		if (value < player.getPlayerBoard().getBonusTile().getHarvestActivationValue())
			
				return false;
		
		return true;
	}

	@Override
	public boolean executeEffect(Player player, GameBoard gameBoard) {
		
		if (isLegal(player,gameBoard))
		{
				player.addAsset(player.getPlayerBoard().getBonusTile().getHarvestServantBonus());
				player.addAsset(player.getPlayerBoard().getBonusTile().getHarvestCoinsBonus());
				player.addAsset(player.getPlayerBoard().getBonusTile().getHarvestMilitaryPointsBonus());
				player.addAsset(player.getPlayerBoard().getBonusTile().getHarvestStonesBonus());
				player.addAsset(player.getPlayerBoard().getBonusTile().getHarvestWoodsBonus());
				
				if (player.getPlayerBoard().getBuildings()!=null)
				{
					for (BuildingCard b : player.getPlayerBoard().getBuildings()){
						
						if(value >= b.getPermanentEffectActivationCost()){
							
							for (Effect e : b.getPermanentEffects())
							{
								e.executeEffect(player, gameBoard);
							}
						
						}
					}
				}
			return true;
		}
		
		return false;
	}
		
	
	
}
