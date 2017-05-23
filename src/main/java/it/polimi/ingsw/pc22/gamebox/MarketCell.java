package it.polimi.ingsw.pc22.gamebox;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.effects.GainAsset;
import it.polimi.ingsw.pc22.player.Player;

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
	public void executeEffect(Player player) {
		
	}

	@Override
	public String toString() {
		String output = "You will gain ";

		List<Effect> effects = super.getEffects();

		for (Effect e : effects) {
			if (!e.equals(effects.get(effects.size()-1)))
				output += e.toString() + " & ";
			else 
				output += e.toString() + ".";
		}
		return output;
	}
	
	/*
	 * EXAMPLE:
		public static void main(String[] args){
			Asset asset = new Asset(3, AssetType.COIN);
			Asset asset2 = new Asset(3, AssetType.FAITHPOINT);
			List<Effect> effects = new ArrayList<Effect>();
			effects.add(new GainAsset(asset));
			effects.add(new GainAsset(asset2));
			MarketCell m1 = new MarketCell(3, effects);
			System.out.print(m1.toString());
	}
	*/
}
