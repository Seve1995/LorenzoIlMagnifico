package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

public class CouncilPalaceCell extends Cell
{
	public CouncilPalaceCell(int requiredDiceValue, List<Effect> effects)
	{
		super(requiredDiceValue, effects);
	}
	
	@Override
	public void executeEffect(Player player) {
		// TODO Auto-generated method stub
		
	}
}
