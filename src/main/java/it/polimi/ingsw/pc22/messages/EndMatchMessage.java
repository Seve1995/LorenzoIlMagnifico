package it.polimi.ingsw.pc22.messages;

import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

/**
 * Created by fandroid95 on 01/07/2017.
 */
public class EndMatchMessage extends Message
{
    private List<Player> standing;

    public EndMatchMessage(List<Player> standing)
    {
        this.standing = standing;
    }

    public List<Player> getStanding()
    {
        return standing;
    }
}
