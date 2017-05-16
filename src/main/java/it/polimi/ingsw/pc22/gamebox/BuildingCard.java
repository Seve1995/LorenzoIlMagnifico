package it.polimi.ingsw.pc22.gamebox;

import java.util.List;

import it.polimi.ingsw.pc22.player.Player;

public class BuildingCard extends DevelopmentCard
{
	private List<Asset> costs; 
	private int permanentEffectActivationCost;
	
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
		int result = 1;
		result = prime * result + ((costs == null) ? 0 : costs.hashCode());
		result = prime * result + permanentEffectActivationCost;
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
		return "BuildingCard [costs=" + costs + ", permanentEffectActivationCost=" + permanentEffectActivationCost
				+ "]";
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