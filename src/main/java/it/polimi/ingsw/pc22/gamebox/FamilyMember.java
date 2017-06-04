package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.player.Player;

public class FamilyMember 
{
	private ColorsEnum color;
	private PlayerColorsEnum playerColor;
	private int familiarValue;
	
	public PlayerColorsEnum getPlayerColor() {
		return playerColor;
	}
	public void setPlayerColor(PlayerColorsEnum playerColor) {
		this.playerColor = playerColor;
	}
	public ColorsEnum getColor() {
		return color;
	}
	public void setColor(ColorsEnum color) {
		this.color = color;
	}
	public int getFamiliarValue() {
		return familiarValue;
	}
	public void setFamiliarValue(int familiarValue) {
		this.familiarValue = familiarValue;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + familiarValue;
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
		if (familiarValue != other.familiarValue)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "FamilyMember [color=" + color + ", value=" + familiarValue + "]";
	}
}
