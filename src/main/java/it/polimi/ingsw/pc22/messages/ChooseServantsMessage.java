package it.polimi.ingsw.pc22.messages;

import it.polimi.ingsw.pc22.player.Player;

/**
 * Created by fandroid95 on 27/06/2017.
 */
public class ChooseServantsMessage extends Message
{
    private Player player;

    public ChooseServantsMessage(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
