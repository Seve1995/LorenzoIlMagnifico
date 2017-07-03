package it.polimi.ingsw.pc22.messages;

import it.polimi.ingsw.pc22.player.Player;

/**
 * Created by fandroid95 on 03/07/2017.
 */
public class ChooseFamiliarMessage extends Message
{
    private Player player;

    public ChooseFamiliarMessage(Player player)
    {
        this.player = player;
    }

    public Player getPlayer()
    {
        return player;
    }
}
