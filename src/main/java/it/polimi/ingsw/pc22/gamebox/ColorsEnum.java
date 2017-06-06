package it.polimi.ingsw.pc22.gamebox;

public enum ColorsEnum 
{
	WHITE,
	ORANGE,
	BLACK,
	NEUTER;

	@Override
	public String toString()
	{
		return super.toString();
	}

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
