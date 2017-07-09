package it.polimi.ingsw.pc22.gamebox;

/**
 * This enumeration represents the various types of family members 
 * that the game includes.
 * It also has a method that, from given as input a valid string,
 * will return the corresponding value from the enumeration.
 */

public enum ColorsEnum 
{
	WHITE,
	ORANGE,
	BLACK,
	NEUTER;

	public static ColorsEnum getColorFromString(String string)
	{
		for (ColorsEnum colorsEnum : ColorsEnum.values())
		{
			if (!colorsEnum.toString().equalsIgnoreCase(string))
				continue;

			return colorsEnum;
		}

		return null;
	}
	
}
