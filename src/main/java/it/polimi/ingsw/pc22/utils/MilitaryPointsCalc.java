package it.polimi.ingsw.pc22.utils;

public enum MilitaryPointsCalc {
	
	ZERO(0,0),
	TWO(2,3),
	THREE(3,7),
	FOUR(4,12),
	FIVE(5,18);
	
	 private int value;
	 private int militaryPoints;
		
    MilitaryPointsCalc(int value, int militaryPoints)
    {
        this.value = value;
        this.militaryPoints = militaryPoints;
    }
    
    public int getMilitaryPoints()
    {
        return militaryPoints;
    }

    public static MilitaryPointsCalc getMilitaryPointsCalcByValue(int value)
    {
    	
    	if (value < 2) return MilitaryPointsCalc.ZERO;
    	
        for (MilitaryPointsCalc militaryPointsCalc : MilitaryPointsCalc.values())
        {
            if (value == militaryPointsCalc.value) return militaryPointsCalc;
        }

        return null;
    }
	
	

}
