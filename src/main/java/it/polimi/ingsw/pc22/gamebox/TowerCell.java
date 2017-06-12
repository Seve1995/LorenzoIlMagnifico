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

			return "(Req. Value:" + this.getRequiredDiceValue() + ") " + this.developmentCard.getName() + "\n";

		if (super.getEffects() == null && this.developmentCard == null)

			return "(Req. Value:" + this.getRequiredDiceValue() + ")\n";

		if (this.developmentCard == null)
				return "(Req. Value:" + this.getRequiredDiceValue() + "|" + "Bonus: " + super.getEffects().get(0).toString() + ")\n";

		return "(Req. Value:" + this.getRequiredDiceValue() + "|" + "Bonus:" + super.getEffects().get(0).toString() + ") " + this.developmentCard.getName() + "\n";	
		
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
