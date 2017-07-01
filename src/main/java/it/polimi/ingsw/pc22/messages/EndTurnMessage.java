package it.polimi.ingsw.pc22.messages;

/**
 * Created by fandroid95 on 01/07/2017.
 */
public class EndTurnMessage extends Message
{
    private Integer lastTurn;
    private Integer era;

    public EndTurnMessage(Integer lastTurn, Integer era)
    {
        this.lastTurn = lastTurn;
        this.era = era;
    }

    public Integer getLastTurn() {
        return lastTurn;
    }

    public Integer getEra() {
        return era;
    }

}
