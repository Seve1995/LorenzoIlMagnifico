package it.polimi.ingsw.pc22.gamebox;

import java.util.List;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.player.Player;

public abstract class DevelopmentCard 
{
	private String name;
	private CardTypeEnum type;
	private int roundNumber;
	private List<Effect> immediateEffect;
	private List<Effect> permanentEffect;
	
	public abstract void useImmediateEffect(Player player);
	public abstract void usePermanentEffect(Player player);
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CardTypeEnum getType() {
		return type;
	}
	public void setType(CardTypeEnum type) {
		this.type = type;
	}
	public int getRoundNumber() {
		return roundNumber;
	}
	public void setRoundNumber(int roundNumber) {
		this.roundNumber = roundNumber;
	}
	public List<Effect> getImmediateEffect() {
		return immediateEffect;
	}
	public void setImmediateEffect(List<Effect> immediateEffect) {
		this.immediateEffect = immediateEffect;
	}
	public List<Effect> getPermanentEffect() {
		return permanentEffect;
	}
	public void setPermanentEffect(List<Effect> permanentEffect) {
		this.permanentEffect = permanentEffect;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((immediateEffect == null) ? 0 : immediateEffect.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((permanentEffect == null) ? 0 : permanentEffect.hashCode());
		result = prime * result + roundNumber;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		DevelopmentCard other = (DevelopmentCard) obj;
		if (immediateEffect == null) {
			if (other.immediateEffect != null)
				return false;
		} else if (!immediateEffect.equals(other.immediateEffect))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (permanentEffect == null) {
			if (other.permanentEffect != null)
				return false;
		} else if (!permanentEffect.equals(other.permanentEffect))
			return false;
		if (roundNumber != other.roundNumber)
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "DevelopmentCard [name=" + name + ", type=" + type + ", roundNumber=" + roundNumber
				+ ", immediateEffect=" + immediateEffect + ", permanentEffect=" + permanentEffect + "]";
	}
}
