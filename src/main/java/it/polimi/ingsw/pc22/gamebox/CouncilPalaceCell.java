package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.effects.Effect;

import java.util.List;

/**
 * This class represents the council palace cell;
 * in each one there is a required dice value (=1 by default)
 * and the list of effect associated with the council palace
 * zone (=add one coin & pick one council privilege). These values
 * are loaded from the GameBoard's json. 
 */

public class CouncilPalaceCell extends Cell
{
	public CouncilPalaceCell(int requiredDiceValue, List<Effect> effects)
	{
		super(requiredDiceValue, effects);
	}
}
