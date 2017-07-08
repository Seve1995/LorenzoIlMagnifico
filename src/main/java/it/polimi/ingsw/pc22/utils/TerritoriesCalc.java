package it.polimi.ingsw.pc22.utils;

/**
 * It is useful to calculate the victory point to
 * assign to the player, on the basis of his/her
 * military points.
 */
public enum TerritoriesCalc
{
    ZERO(0,0),
    ONE(3,1),
    FOUR(4,4),
    TEN(5,10),
    TWENTY(6,20);

    private int value;
    private int victoryPoints;

    TerritoriesCalc(int value, int victoryPoints)
    {
        this.value = value;
        this.victoryPoints = victoryPoints;
    }

    public int getVictoryPoints()
    {
        return victoryPoints;
    }

    public static TerritoriesCalc getTerritoryCalcByValue(int value)
    {
        if (value < 3) return TerritoriesCalc.ZERO;

        for (TerritoriesCalc territoriesCalc : TerritoriesCalc.values())
        {
            if (value == territoriesCalc.value) return territoriesCalc;
        }

        return null;
    }
}
