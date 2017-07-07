package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.effects.Effect;

import java.util.List;

/**
 * This kind of card is characterized by different type of costs:
 * -military points
 * -other resources
 *
 * If the cost is quantified by military points we have two values.
 * Military points required just to take the card and military points
 * actually spent.
 * Some cards make the gamer able to choose between the two types of cost
 */

public class VentureCard extends DevelopmentCard
{
	private boolean requiredCostChoice = false;
	private Asset militaryPointsRequired;
	private Asset militaryPointsCost;
	private List<Asset> resourcesCost; 
	
	
	public VentureCard
	(
		String name, int roundNumber, List<Effect> immediateEffects,
		List<Effect> permanentEffects, Asset militaryPointsRequired,
		Asset militaryPointsCost, List<Asset> resourcesCost,
		boolean requiredCostChoice, int cardNumber
	) 
	{
		super(name, roundNumber, immediateEffects, permanentEffects, cardNumber);
		this.militaryPointsRequired = militaryPointsRequired;
		this.militaryPointsCost = militaryPointsCost;
		this.resourcesCost = resourcesCost;
		this.requiredCostChoice = requiredCostChoice;
	}

	public VentureCard
	(
		String name, int roundNumber, List<Effect> immediateEffects,
		List<Effect> permanentEffects, int cardNumber
	)
	{
		super(name, roundNumber, immediateEffects, permanentEffects, cardNumber);
	}

	public Asset getMilitaryPointsRequired() {
		return militaryPointsRequired;
	}



	public void setMilitaryPointsRequired(Asset militaryPointsRequired) {
		this.militaryPointsRequired = militaryPointsRequired;
	}



	public Asset getMilitaryPointsCost() {
		return militaryPointsCost;
	}



	public void setMilitaryPointsCost(Asset militaryPointsCost) {
		this.militaryPointsCost = militaryPointsCost;
	}



	public List<Asset> getResourcesCost() {
		return resourcesCost;
	}



	public void setResourcesCost(List<Asset> resourcesCost) {
		this.resourcesCost = resourcesCost;
	}



	public boolean isRequiredCostChoice() {
		return requiredCostChoice;
	}



	public void setRequiredCostChoice(boolean requiredCostChoice) {
		this.requiredCostChoice = requiredCostChoice;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((militaryPointsCost == null) ? 0 : militaryPointsCost.hashCode());
		result = prime * result + ((militaryPointsRequired == null) ? 0 : militaryPointsRequired.hashCode());
		result = prime * result + (requiredCostChoice ? 1231 : 1237);
		result = prime * result + ((resourcesCost == null) ? 0 : resourcesCost.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof VentureCard))
			return false;
		VentureCard other = (VentureCard) obj;
		if (militaryPointsCost == null) {
			if (other.militaryPointsCost != null)
				return false;
		} else if (!militaryPointsCost.equals(other.militaryPointsCost))
			return false;
		if (militaryPointsRequired == null) {
			if (other.militaryPointsRequired != null)
				return false;
		} else if (!militaryPointsRequired.equals(other.militaryPointsRequired))
			return false;
		if (requiredCostChoice != other.requiredCostChoice)
			return false;
		if (resourcesCost == null) {
			if (other.resourcesCost != null)
				return false;
		} else if (!resourcesCost.equals(other.resourcesCost))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VentureCard{" +
				"requiredCostChoice=" + requiredCostChoice +
				", militaryPointsRequired=" + militaryPointsRequired +
				", militaryPointsCost=" + militaryPointsCost +
				", resourcesCost=" + resourcesCost +
				'}';
	}
}
