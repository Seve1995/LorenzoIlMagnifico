package it.polimi.ingsw.pc22.utils;

/**
 * On the basis of the number of turn,
 * with this class we can infer in which era we are
 * (Useful for excommunications and some other effects)
 */

public enum EraCalc
{
    FIRSTERAFIVEPLAYERSZERO(5,0,1),
    FIRSTERAFIVEPLAYERSTHREE(5,3,1),
    SECONDFIVEPLAYERSIX(5,6,2),
    SECONDFIVEPLAYERSNINE(5,9,2),
    THIRDFIVEPLAYERSTWELVE(5,12,3),
    THIRDFIVEPLAYERSTFIFTEEN(5,15,3),
    
    FIRSTERAFOURPLAYERSZERO(4,0,1),
    FIRSTERAFOURPLAYERSFOUR(4,4,1),
    SECONDFOURPLAYERSEIGHT(4,8,2),
    SECONDFOURPLAYERSTWELVE(4,12,2),
    THIRDFOURPLAYERSSIXTEEN(4,16,3),
    THIRDFOURPLAYERSTWENTY(4,20,3);

    EraCalc(int playerCounter, int round, int era)
    {
        this.playerCounter = playerCounter;
        this.round = round;
        this.era = era;
    }

    private int playerCounter;
    private int round;
    private int era;

    public static int getEraNumber(int playerCounter, int round)
    {
    	int counter = 4;
    	if (playerCounter==5) 
    		counter = 5;
        for (EraCalc eraCalc : EraCalc.values())
        {
            if (eraCalc.playerCounter == counter && eraCalc.round>=round)
                return eraCalc.era;
        }

        return -1;
    }
}
