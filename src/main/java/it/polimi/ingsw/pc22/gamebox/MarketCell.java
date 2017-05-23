package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.player.Player;

public class MarketCell extends Cell {
	private boolean isABlockedCell=false;

	public MarketCell(int requiredDiceValue, Effect[] effects)
	{
		super(requiredDiceValue);
		super.setEffects(effects);
	}
	
	public boolean isABlockedCell() {
		return isABlockedCell;
	}

	public void setABlockedCell(boolean isABlockedCell) {
		this.isABlockedCell = isABlockedCell;
	}
	
	@Override
	public void executeEffect(Player player) {
		
	}

	@Override
	public String toString() {
		String output = "You will gain ";

		Effect[] effects = super.getEffects();

		for (Effect e : effects) {
			if (!e.equals(effects[effects.length-1]))
				output += e.toString() + " & ";
			else 
				output += e.toString() + ".";
		}
		return output;
	}
	
	/*
	 * EXAMPLE:
	 * 
	  	public static void main(String[] args){
		Asset asset = new Asset(3, AssetType.COIN);
		Asset asset2 = new Asset(3, AssetType.FAITHPOINT);
		Effect[] effect = new Effect[2];
		effect[0] = new GainAsset(null, asset);
		effect[1] = new GainAsset(null, asset2);
		MarketCell m1 = new MarketCell(3, effect);
		System.out.print(m1.toString());
	}
	*/
}
