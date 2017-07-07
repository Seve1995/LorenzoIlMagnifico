package it.polimi.ingsw.pc22.gamebox;


/**
 * This enumeration lists the colors of the players.
 * It is useful to recognise players during the game.
 */
public enum PlayerColorsEnum
{
	RED(0),
	BLUE(1),
	YELLOW(2),
	GREEN(3),
	BROWN(4);

	private int value;

	PlayerColorsEnum(int value)
	{
		this.value = value;
	}

	private int getValue()
	{
		return value;
	}

	public static PlayerColorsEnum getColorByValue(int value)
	{
		for (PlayerColorsEnum color : PlayerColorsEnum.values())
		{
			if (color.getValue() == value)
				return color;
		}

		return null;
	}
	
}
