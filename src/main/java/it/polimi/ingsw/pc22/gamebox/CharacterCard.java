package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.effects.Effect;

import java.util.List;

/**
 * This kind of card is characterized by ony one type of cost,
 * a coins cost.
 * It has immediate and permanent effects.
 */

public class CharacterCard extends DevelopmentCard
{
	private Asset coinsCost;

	public CharacterCard
	(
		String name, int roundNumber, List<Effect> immediateEffects,
		List<Effect> permanentEffects, Asset coinsCost, int cardNumber
	)
	{
		super(name, roundNumber, immediateEffects, permanentEffects, cardNumber);
		this.coinsCost = coinsCost;
	}

	public CharacterCard
	(
		String name, int roundNumber, List<Effect> immediateEffects,
		List<Effect> permanentEffects, int cardNumber
	)
	{
		super(name, roundNumber, immediateEffects, permanentEffects, cardNumber);
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
		int result = super.hashCode();
		result = prime * result + ((coinsCost == null) ? 0 : coinsCost.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof CharacterCard))
			return false;
		CharacterCard other = (CharacterCard) obj;
		if (coinsCost == null) {
			if (other.coinsCost != null)
				return false;
		} else if (!coinsCost.equals(other.coinsCost))
			return false;
		return true;
	}
}
