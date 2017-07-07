package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.effects.Effect;

import java.util.List;

/**
 * This is the single tower cell. Some cells are characterized by some immediate effects,
 * for example adding some resources, activable only by placing in that cell a familiar.
 * Every cell, before every action is associated to a development card.
 *
 */

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
	
}
