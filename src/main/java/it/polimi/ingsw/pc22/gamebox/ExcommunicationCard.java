package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.effects.Effect;

import java.util.List;

public class ExcommunicationCard {
	
	private int age;
	private int number;
	private List<Effect> effects;
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public List<Effect> getEffects() {
		return effects;
	}
	public void setEffects(List<Effect> effects) {
		this.effects = effects;
	}
	
}


/*carta
 nome
 age
 effects[
  {
	  nome null
	  
	  
	  
  }
  ]
*/