package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.player.Player;

public class CouncilPalaceCell extends Cell
{
	
	public CouncilPalaceCell(int requiredDiceValue, Effect[] effects)
	{
		super(requiredDiceValue);
		setEffects(effects);
	}
	
	@Override
	public void executeEffect(Player player) {
		// TODO Auto-generated method stub
		
	}
}
