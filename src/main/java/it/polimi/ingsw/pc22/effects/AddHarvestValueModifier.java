package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public class AddHarvestValueModifier extends Effect{
	
	public AddHarvestValueModifier(GameBoard gameBoard) {
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
	public boolean isLegal(Player player) 
	{
		return true;
	}

	@Override
	public void executeEffect(Player player) 
	{
		if (isLegal(player))
		{
			player.setProductionValueModifier(player.getProductionValueModifier() + value);
		}
		
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
