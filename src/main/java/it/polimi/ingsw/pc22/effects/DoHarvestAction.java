package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.BuildingCard;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public class DoHarvestAction extends Effect {
	
	public DoHarvestAction(GameBoard gameBoard) {
		super(gameBoard);
		
	}

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
		
			player.addAsset(player.getPlayerBoard().getBonusTile().getHarvestServantBonus());
			player.addAsset(player.getPlayerBoard().getBonusTile().getHarvestCoinsBonus());
			player.addAsset(player.getPlayerBoard().getBonusTile().getHarvestMilitaryPointsBonus());
			player.addAsset(player.getPlayerBoard().getBonusTile().getHarvestStonesBonus());
			player.addAsset(player.getPlayerBoard().getBonusTile().getHarvestWoodsBonus());
		
			
		
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
