package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public class AddHarvestValueModifier implements Effect{

	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
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
			player.setProductionValueModifier(player.getProductionValueModifier() + value);
			return true;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AddHarvestValueModifier))
			return false;
		AddHarvestValueModifier other = (AddHarvestValueModifier) obj;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AddHarvestValueBonus [value=" + value + "]";
	}
	
	
}
