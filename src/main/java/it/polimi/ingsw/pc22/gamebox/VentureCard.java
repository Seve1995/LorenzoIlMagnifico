package it.polimi.ingsw.pc22.gamebox;

import java.util.List;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.player.Player;

public class VentureCard extends DevelopmentCard
{
	private List<Asset> resourceCost; 
	private Asset militaryCost;
	private int militaryPointsGain;
	
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
}
