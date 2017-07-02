package it.polimi.ingsw.pc22.messages;

import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

/**
 * Created by fandroid95 on 01/07/2017.
 */
public class EndMatchMessage extends Message
{
    //CLASSIFICA DI TUTTI I GIOCATORI ISCRITTI AL SERVER
    private List<Player> standing;

    private String winnerName;

    public EndMatchMessage(List<Player> standing, String winnerName)
    {
        this.standing = standing;
        this.winnerName = winnerName;
    }

    public List<Player> getStanding()
    {
        return standing;
    }

    public String getWinnerName()
    {
        return winnerName;
    }
}
