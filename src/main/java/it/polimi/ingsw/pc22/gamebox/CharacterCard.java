package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

public class CharacterCard extends DevelopmentCard
{
	private Asset coinsCost;

	public CharacterCard
	(
		String name, int roundNumber, List<Effect> immediateEffects,
		List<Effect> permanentEffects, Asset coinsCost
	)
	{
		super(name, roundNumber, immediateEffects, permanentEffects);
		this.coinsCost = coinsCost;
	}

	public Asset getCoinsCost() {
		return coinsCost;
	}

	public void setCoinsCost(Asset coinsCost) {
		this.coinsCost = coinsCost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coinsCost == null) ? 0 : coinsCost.hashCode());
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
		CharacterCard other = (CharacterCard) obj;
		if (coinsCost == null) {
			if (other.coinsCost != null)
				return false;
		} else if (!coinsCost.equals(other.coinsCost))
			return false;
		return true;
	}

	@Override
	public String toString() {
		
		String output = this.getName() + " is a Character Card.\n Its activation cost is ";
		
		output += coinsCost.toString();
		
		output += super.toString();
		
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
