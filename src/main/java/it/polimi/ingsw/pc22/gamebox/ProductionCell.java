package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.player.Player;

public class ProductionCell extends Cell{
	private boolean blockedCell;
	private boolean penaltyCell;
	
	@Override
	public void executeEffect(Player player) {
		// TODO Auto-generated method stub
		
	}
	
	public void setAsBlocked(){
		this.blockedCell=true;
	}

}
