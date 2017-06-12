package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

public class TowerCell extends Cell
{
	private DevelopmentCard developmentCard;

	public TowerCell() {}

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
	public void executeEffects(Player player)
	{

	}

	@Override
	public String toString()
	{

		if (super.getEffects() == null && this.developmentCard != null)

			return "(Required Value: " + this.getRequiredDiceValue() + ") " + this.developmentCard.getName();

		if (super.getEffects() == null && this.developmentCard == null)

			return "(Required Value: " + this.getRequiredDiceValue() + ")";

		StringBuilder output = new StringBuilder();

		for (int i=0; i<super.getEffects().size(); i++)
		{
			if (this.developmentCard == null)
				output.append("(Required Value: " + this.getRequiredDiceValue() + " + " + "Bonus: " + super.getEffects().get(i).toString() + ") ");

			else
				output.append("Required Value: " + this.getRequiredDiceValue() + " + " + "Bonus: " + super.getEffects().get(i).toString() + ") " + this.developmentCard.getName());

		}
			
		return output.toString();
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
