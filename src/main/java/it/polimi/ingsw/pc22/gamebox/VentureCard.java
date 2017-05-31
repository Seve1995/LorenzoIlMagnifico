package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

public class VentureCard extends DevelopmentCard
{
	private boolean requiredCostChoice = false;
	private Asset militaryPointsRequired;
	private Asset militaryPointsCost;
	private List<Asset> resourcesCost; 
	
	
	public VentureCard
	(
			String name, int roundNumber, List<Effect> immediateEffects, List<Effect> permanentEffects, 
			Asset militaryPointsRequired, Asset militaryPointsCost, List<Asset> resourcesCost, boolean requiredCostChoice
	) 
	{
		super(name, roundNumber, immediateEffects, permanentEffects);
		this.militaryPointsRequired = militaryPointsRequired;
		this.militaryPointsCost = militaryPointsCost;
		this.resourcesCost = resourcesCost;
		this.requiredCostChoice = requiredCostChoice;
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
	public void useImmediateEffect(Player player) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void usePermanentEffect(Player player) {
		// TODO Auto-generated method stub
		
	}
	
	//TODO: Rifare con la nuova logica implementata
	@Override
	public String toString() {
		
		String output = this.getName() + " is a Venture Card.\n Its activation cost are ";
		
		for (Asset a: this.resourcesCost){ 
			 output += a.toString() + "\n";
			 if (this.requiredCostChoice && !(a.equals(resourcesCost.get(resourcesCost.size()-1))))
			 {
				 output += "or";
			 }
		}
		
		output += super.toString();
		
		return output;
	}
}
