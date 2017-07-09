package it.polimi.ingsw.pc22.messages;

import it.polimi.ingsw.pc22.player.Player;

/**
 * This message provides the user his/her representation into the model:
 * the player. Among the unused familiars the user should choose one of them to
 * execute an effect.
 *
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
