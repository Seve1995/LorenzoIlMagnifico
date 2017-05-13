package it.polimi.ingsw.pc22.gamebox;

public class FamilyMember 
{
	private ColorsEnum color;
	private int value;
	
	public ColorsEnum getColor() {
		return color;
	}
	public void setColor(ColorsEnum color) {
		this.color = color;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + value;
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
		FamilyMember other = (FamilyMember) obj;
		if (color != other.color)
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	
	
}
