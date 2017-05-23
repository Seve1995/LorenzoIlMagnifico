package it.polimi.ingsw.pc22.gamebox;

import java.util.List;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.player.Player;

public abstract class DevelopmentCard 
{
	private String name;
	private int roundNumber;
	private boolean permanentEffectChoiche = false;

	
	private List<Effect> immediateEffects;
	private List<Effect> permanentEffects;
	
	public DevelopmentCard(String name, int roundNumber, List<Effect> immediateEffects, List<Effect> permanentEffects) {
		this.name = name;
		this.roundNumber = roundNumber;
		this.immediateEffects = immediateEffects;
		this.permanentEffects = permanentEffects;
	}
	
	public abstract void useImmediateEffect(Player player);
	public abstract void usePermanentEffect(Player player);
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	public boolean isPermanentEffectChoiche() {
		return permanentEffectChoiche;
	}

	public void setPermanentEffectChoiche(boolean permanentEffectChoiche) {
		this.permanentEffectChoiche = permanentEffectChoiche;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((immediateEffects == null) ? 0 : immediateEffects.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (permanentEffectChoiche ? 1231 : 1237);
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
		if (getClass() != obj.getClass())
			return false;
		DevelopmentCard other = (DevelopmentCard) obj;
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
		if (permanentEffectChoiche != other.permanentEffectChoiche)
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
	
	@Override
	
	public String toString() {
		
		String output="";
		
		if (!((this.getImmediateEffects())==null)){
			output += "It has the following Immediate Effects ";
			for (Effect e: this.immediateEffects){
				output += e.toString() + "\n";
			}
		}
		
		if (!((this.getPermanentEffects())==null))
		{
			output += "It has the following Permanent Effects " ;
			for (Effect e: this.permanentEffects){ 
				 output += e.toString() + "\n";
				 if (permanentEffectChoiche && !(e.equals(permanentEffects.get(permanentEffects.size()-1))))
				 {
					 output += "or";
				 }
			}
		}
		
		return output;
	}
	
	

}
