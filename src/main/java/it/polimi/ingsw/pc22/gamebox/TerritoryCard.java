package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.effects.Effect;

import java.util.List;

/**
 * This class represents the Territory-type cards.
 * This type of cards has no cost. It has immediate and permanent effects, activable
 * by doing an harvest action.
 */

public class TerritoryCard extends DevelopmentCard
{
	private int permanentEffectActivationCost;

	public TerritoryCard
	(
		String name, int roundNumber, List<Effect> immediateEffects,
		List<Effect> permanentEffects, int permanentEffectActivationCost,
		int cardNumber
	)
	{
		super(name, roundNumber, immediateEffects, permanentEffects, cardNumber);
		this.permanentEffectActivationCost = permanentEffectActivationCost;
	}

	public TerritoryCard
	(
			String name, int roundNumber, List<Effect> immediateEffects,
			List<Effect> permanentEffects, int cardNumber
	)
	{
		super(name, roundNumber, immediateEffects, permanentEffects, cardNumber);
	}

	public int getPermanentEffectActivationCost() {
		return permanentEffectActivationCost;
	}

	public void setPermanentEffectActivationCost(int permanentEffectActivationCost) {
		this.permanentEffectActivationCost = permanentEffectActivationCost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + permanentEffectActivationCost;
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof TerritoryCard))
			return false;
		TerritoryCard other = (TerritoryCard) obj;
		if (permanentEffectActivationCost != other.permanentEffectActivationCost)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TerritoryCard{" +
				"permanentEffectActivationCost=" + permanentEffectActivationCost +
				'}';
	}
}
