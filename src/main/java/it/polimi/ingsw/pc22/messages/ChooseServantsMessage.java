package it.polimi.ingsw.pc22.messages;

import it.polimi.ingsw.pc22.player.Player;

/**
 * This message provides the user his/her representation into the model:
 * the player. The user should choose a servant number (lower than his/her actual servants)
 * to increment the value of an action.
 *
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
