package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

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
	public boolean executeEffects(Player player, GameBoard gameBoard) {
		
		if (isLegal(player,gameBoard))
		{
			player.setProductionValueModifier(player.getProductionValueModifier() + productionValue);
			return true;
		}
		return false;
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + productionValue;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddProductionValueModifier other = (AddProductionValueModifier) obj;
		if (productionValue != other.productionValue)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AddProductionBonus [value=" + productionValue + "]";
	}
	
	
}
