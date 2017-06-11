package it.polimi.ingsw.pc22.utils;

public enum CharactersCalc 
{
	ZERO(0,0),
	ONE(1,1),
	TWO(2,3),
	THREE(3,6),
	FOUR(4,10),
	FIVE(5,15),
	SIX(6,21);
	
    private int value;
    private int victoryPoints;
	
    CharactersCalc(int value, int victoryPoints)
    {
        this.value = value;
        this.victoryPoints = victoryPoints;
    }
    
    public int getVictoryPoints()
    {
        return victoryPoints;
    }

    public static CharactersCalc getCharacterCalcByValue(int value)
    {
    	
        for (CharactersCalc charactersCalc : CharactersCalc.values())
        {
            if (value == charactersCalc.value) return charactersCalc;
        }

        return null;
    }
	

}
