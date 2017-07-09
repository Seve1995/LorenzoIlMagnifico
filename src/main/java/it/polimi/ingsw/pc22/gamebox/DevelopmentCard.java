package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.effects.Effect;

import java.io.Serializable;
import java.util.List;

/**
 * This class represents the standard card that is on the tower.
 * Its parameters are loaded from the cards.json when the game match starts.
 * name = The name of the card
 * cardNumber = A unique identifier for the card
 * roundNumber = Identifies the era in which the card can be played
 * permanentEffectChoice = True if the card has multiple permanent effect and requires a user's choice
 * immediateEffects = The list of immediate effects of the card
 * permanentEffects = The list of permanent effects of the card
 */

public class DevelopmentCard implements Serializable
{
	private String name;
	private int cardNumber;
	private int roundNumber;
	private boolean permanentEffectChoice = false;

	private transient List<Effect> immediateEffects;
	private transient List<Effect> permanentEffects;

	public DevelopmentCard(){}

	public DevelopmentCard
	(
		String name, int roundNumber, List<Effect> immediateEffects,
		List<Effect> permanentEffects, int cardNumber
	)
	{
		this.name = name;
		this.roundNumber = roundNumber;
		this.cardNumber = cardNumber;
		this.immediateEffects = immediateEffects;
		this.permanentEffects = permanentEffects;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(int cardNumber) {
		this.cardNumber = cardNumber;
	}

	public int getRoundNumber() {
		return roundNumber;
	}
	public void setRoundNumber(int roundNumber) {
		this.roundNumber = roundNumber;
	}
	public List<Effect> getImmediateEffects() {
		return immediateEffects;
	}
	public void setImmediateEffects(List<Effect> immediateEffects) {
		this.immediateEffects = immediateEffects;
	}
	public List<Effect> getPermanentEffects() {
		return permanentEffects;
	}
	public void setPermanentEffects(List<Effect> permanentEffects) {
		this.permanentEffects = permanentEffects;
	}
	
	public boolean isPermanentEffectChoice() {
		return permanentEffectChoice;
	}

	public void setPermanentEffectChoiche(boolean permanentEffectChoice) {
		this.permanentEffectChoice = permanentEffectChoice;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cardNumber;
		result = prime * result + ((immediateEffects == null) ? 0 : immediateEffects.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (permanentEffectChoice ? 1231 : 1237);
		result = prime * result + ((permanentEffects == null) ? 0 : permanentEffects.hashCode());
		result = prime * result + roundNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DevelopmentCard))
			return false;
		DevelopmentCard other = (DevelopmentCard) obj;
		if (cardNumber != other.cardNumber)
			return false;
		if (immediateEffects == null) {
			if (other.immediateEffects != null)
				return false;
		} else if (!immediateEffects.equals(other.immediateEffects))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (permanentEffectChoice != other.permanentEffectChoice)
			return false;
		if (permanentEffects == null) {
			if (other.permanentEffects != null)
				return false;
		} else if (!permanentEffects.equals(other.permanentEffects))
			return false;
		if (roundNumber != other.roundNumber)
			return false;
		return true;
	}
	
}
