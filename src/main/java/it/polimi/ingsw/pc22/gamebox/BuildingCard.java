package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.effects.Effect;

import java.util.List;

/**
 * This kind of card is characterized by different type of costs:
 * -military points
 * -other resources
 *
 * It has immediate and permanent effects. The latter is activable
 * by doing a production action.
 */

public class BuildingCard extends DevelopmentCard
{
	private List<Asset> costs; 
	private int permanentEffectActivationCost;

	public BuildingCard
	(
		String name, int roundNumber, List<Effect> immediateEffects,
		List<Effect> permanentEffects, List<Asset> costs,
		int permanentEffectActivationCost, int cardNumber
	)
	{
		super(name, roundNumber, immediateEffects, permanentEffects, cardNumber);
		this.costs = costs;
		this.permanentEffectActivationCost = permanentEffectActivationCost;
	}

	public BuildingCard
	(
		String name, int roundNumber, List<Effect> immediateEffects,
		List<Effect> permanentEffects, int cardNumber
	)
	{
		super(name, roundNumber, immediateEffects, permanentEffects, cardNumber);
	}

	public List<Asset> getCosts() {
		return costs;
	}

	public void setCosts(List<Asset> costs) {
		this.costs = costs;
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
		result = prime * result + ((costs == null) ? 0 : costs.hashCode());
		result = prime * result + permanentEffectActivationCost;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof BuildingCard))
			return false;
		BuildingCard other = (BuildingCard) obj;
		if (costs == null) {
			if (other.costs != null)
				return false;
		} else if (!costs.equals(other.costs))
			return false;
		if (permanentEffectActivationCost != other.permanentEffectActivationCost)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BuildingCard{" +
				"costs=" + costs +
				", permanentEffectActivationCost=" + permanentEffectActivationCost +
				'}';
	}
}
