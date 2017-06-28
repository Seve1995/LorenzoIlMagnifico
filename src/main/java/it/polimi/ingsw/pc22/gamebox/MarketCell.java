package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.effects.Effect;

import java.util.List;

public class MarketCell extends Cell
{
	private boolean isABlockedCell=false;

	public MarketCell(int requiredDiceValue, List<Effect> effects)
	{
		super(requiredDiceValue, effects);
	}
	
	public boolean isABlockedCell() {
		return isABlockedCell;
	}

	public void setABlockedCell(boolean isABlockedCell) {
		this.isABlockedCell = isABlockedCell;
	}

	@Override
	public String toString()
	{
		StringBuilder output = new StringBuilder("You will gain ");

		List<Effect> effects = super.getEffects();

		for (Effect e : effects) {
			if (!e.equals(effects.get(effects.size()-1)))
				output.append(e.toString() + " & ");
			else 
				output.append(e.toString() + ".");
		}
		return output.toString();
	}
}
