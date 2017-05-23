package it.polimi.ingsw.pc22.gamebox;

import java.util.List;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.player.Player;

public class VentureCard extends DevelopmentCard
{
	private List<Asset> resourceCost; 
	private Asset militaryCost;
	private int militaryPointsGain;
	private boolean requiredCostChoice = false;
	
	
	public VentureCard(String name, int roundNumber, List<Effect> immediateEffects, List<Effect> permanentEffects, List<Asset> resourceCost, Asset militaryCost, int militaryPointsGain) 
	{
		super(name, roundNumber, immediateEffects, permanentEffects);
		this.resourceCost = resourceCost;
		this.militaryCost = militaryCost;
	}
	
	public List<Asset> getResourceCost() {
		return resourceCost;
	}
	public void setResourceCost(List<Asset> resourceCost) {
		this.resourceCost = resourceCost;
	}
	public Asset getMilitaryCost() {
		return militaryCost;
	}
	public void setMilitaryCost(Asset militaryCost) {
		this.militaryCost = militaryCost;
	}
	public int getMilitaryPointsGain() {
		return militaryPointsGain;
	}
	public void setMilitaryPointsGain(int militaryPointsGain) {
		this.militaryPointsGain = militaryPointsGain;
	}

	@Override
	public void useImmediateEffect(Player player) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void usePermanentEffect(Player player) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String toString() {
		
		String output = this.getName() + " is a Venture Card.\n Its activation cost are ";
		
		for (Asset a: this.resourceCost){ 
			 output += a.toString() + "\n";
			 if (this.requiredCostChoice && !(a.equals(resourceCost.get(resourceCost.size()-1))))
			 {
				 output += "or";
			 }
		}
		
		output += super.toString();
		
		return output;
	}
}
