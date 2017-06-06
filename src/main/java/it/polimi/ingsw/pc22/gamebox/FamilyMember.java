package it.polimi.ingsw.pc22.gamebox;

public class FamilyMember 
{
	private ColorsEnum color;
	private PlayerColorsEnum playerColor;
	private int familiarValue;
	private boolean isPlayed; //TRUE = Il familyMember è stato giocato del player nel turno corrente
	private int valueModifier; //Tiene traccia dei bonus/malus
	
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
	public boolean isPlayed() {
		return isPlayed;
	}
	public void setPlayed(boolean isPlayed) {
		this.isPlayed = isPlayed;
	}
	public int getValueModifier() {
		return valueModifier;
	}
	public void setValueModifier(int valueModifier) {
		this.valueModifier = valueModifier;
	}
	
	public int getValue()
	{
		if (familiarValue + valueModifier < 0 )
			return 0;
		
		return familiarValue + valueModifier;
		
	}
	@Override
	public String toString() 
	{
		return "PlayerColor: " + playerColor.toString() + '\n'
				+ "FamiliarColor: " + color.toString() + '\n'
				+ "Value: " + familiarValue;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + familiarValue;
		result = prime * result + (isPlayed ? 1231 : 1237);
		result = prime * result + ((playerColor == null) ? 0 : playerColor.hashCode());
		result = prime * result + valueModifier;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof FamilyMember))
			return false;
		FamilyMember other = (FamilyMember) obj;
		if (color != other.color)
			return false;
		if (familiarValue != other.familiarValue)
			return false;
		if (isPlayed != other.isPlayed)
			return false;
		if (playerColor != other.playerColor)
			return false;
		if (valueModifier != other.valueModifier)
			return false;
		return true;
	}
	
}
