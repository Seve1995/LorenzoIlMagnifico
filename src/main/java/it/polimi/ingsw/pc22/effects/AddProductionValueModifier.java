package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;
/**
 * This class is used to store bonus/malus associated to the production action
 * It is useful for Leader Cards effects.
 */

public class AddProductionValueModifier implements Effect{
	
	private int productionValue;

	public int getValue() {
		return productionValue;
	}

	public void setValue(int value) {
		this.productionValue = value;
	}

	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) 
	{
		return true;
	}

	@Override
	public boolean executeEffects(Player player, GameBoard gameBoard) 
	{	
		player.setProductionValueModifier(player.getProductionValueModifier() + productionValue);
		
		return true;
	}
	
}
