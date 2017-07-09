package it.polimi.ingsw.pc22.gamebox;

/**
 * This enumeration represents the various types of cards 
 * that the game includes.
 * It also has a method that, from given as input a valid string,
 * will return the corresponding value from the enumeration.
 */

public enum CardTypeEnum {
	TERRITORY,
	VENTURE,
	BUILDING,
	CHARACTER,
	ANY;

	public static CardTypeEnum getCardTypeFromString(String string)
	{
		for (CardTypeEnum colorsEnum : CardTypeEnum.values())
		{
			if (!colorsEnum.toString().equalsIgnoreCase(string))
				continue;

			return colorsEnum;
		}

		return null;
	}
}
