package it.polimi.ingsw.pc22.gamebox;

import java.util.List;

import it.polimi.ingsw.pc22.player.Player;

public class VentureCard extends DevelopmentCard
{
	private List<Asset> resourceCost; 
	private Asset militaryCost;
	private int militaryPointsGain;
	private boolean justOneAsset;
	
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
	public boolean isJustOneAsset() {
		return justOneAsset;
	}
	public void setJustOneAsset(boolean justOneAsset) {
		this.justOneAsset = justOneAsset;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (justOneAsset ? 1231 : 1237);
		result = prime * result + ((militaryCost == null) ? 0 : militaryCost.hashCode());
		result = prime * result + militaryPointsGain;
		result = prime * result + ((resourceCost == null) ? 0 : resourceCost.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VentureCard other = (VentureCard) obj;
		if (justOneAsset != other.justOneAsset)
			return false;
		if (militaryCost == null) {
			if (other.militaryCost != null)
				return false;
		} else if (!militaryCost.equals(other.militaryCost))
			return false;
		if (militaryPointsGain != other.militaryPointsGain)
			return false;
		if (resourceCost == null) {
			if (other.resourceCost != null)
				return false;
		} else if (!resourceCost.equals(other.resourceCost))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "VentureCard [resourceCost=" + resourceCost + ", militaryCost=" + militaryCost + ", militaryPointsGain="
				+ militaryPointsGain + ", justOneAsset=" + justOneAsset + "]";
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
