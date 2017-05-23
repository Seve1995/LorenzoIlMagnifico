package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.player.Player;

public class TerritoryCard extends DevelopmentCard
{
	private int permanentEffectActivationCost;
	
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
		TerritoryCard other = (TerritoryCard) obj;
		if (permanentEffectActivationCost != other.permanentEffectActivationCost)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		
			String output = super.toString();
			
			return output;
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
