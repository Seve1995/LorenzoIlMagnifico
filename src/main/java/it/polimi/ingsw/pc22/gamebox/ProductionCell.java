package it.polimi.ingsw.pc22.gamebox;

import java.util.List;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.player.Player;

public class ProductionCell extends Cell{
	private boolean isABlockedCell=false;
	private boolean isAPenaltyCell=false;
	
	public ProductionCell(int requiredDiceValue, List<Effect> effects) {
		super(requiredDiceValue, effects);
	}
	
	@Override
	public void executeEffect(Player player) {
		// TODO Auto-generated method stub
	}
	
	public void setAsBlockedCell(){
		this.isABlockedCell=true;
	}
	
	public void setAsPenaltyCell() {
		this.isAPenaltyCell=true;
	}

}
