package it.polimi.ingsw.pc22.gamebox;

import java.util.List;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.player.Player;

public class CouncilPalaceCell extends Cell
{
	
	public CouncilPalaceCell(int requiredDiceValue, List<Effect> effects)
	{
		super(requiredDiceValue);
		setEffects(effects);
	}
	
	@Override
	public void executeEffect(Player player) {
		// TODO Auto-generated method stub
		
	}
}
