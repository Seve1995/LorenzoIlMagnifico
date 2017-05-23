package it.polimi.ingsw.pc22.gamebox;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.effects.GainAsset;
import it.polimi.ingsw.pc22.player.Player;

public class TowerCell extends Cell
{
	private DevelopmentCard developmentCard;
	
	public TowerCell(int requiredDiceValue, List<Effect> effects)
	{
		super(requiredDiceValue, effects);
		this.developmentCard = null;
	}

	public DevelopmentCard getDevelopmentCard() {
		return developmentCard;
	}

	public void setDevelopmentCard(DevelopmentCard developmentCard) {
		this.developmentCard = developmentCard;
	}
	
	@Override
	public void executeEffect(Player player) {
	}
	
	public String toString() {
		String output="";
		for (int i=0; i<super.getEffects().size(); i++){
			output = "(Required Value: " + this.getRequiredDiceValue() + " + " + "Bonus: " + super.getEffects().get(i).toString() + ") " + this.developmentCard.getName();
		}
			
		return output;
	}
	
	/*public static void main(String[] args) {
		List<Effect> effects = new ArrayList<Effect>();
		effects.add(new GainAsset(new Asset(3, AssetType.COIN)));
		DevelopmentCard developmentCard = new VentureCard("Morgana", 1, effects, null, null, null, 0);
		TowerCell c = new TowerCell(3, effects);
		c.setDevelopmentCard(developmentCard);
		System.out.println(c.toString());
	}*/
	
}
