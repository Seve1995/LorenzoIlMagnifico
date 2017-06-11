package it.polimi.ingsw.pc22.gamebox;

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
