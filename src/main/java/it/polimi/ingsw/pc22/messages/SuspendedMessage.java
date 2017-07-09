package it.polimi.ingsw.pc22.messages;

/**
 * This message informs all the player that a certain player
 * is suspended (aka his/her action timeout is finished)
 */

public class SuspendedMessage extends Message
{
    private String message;

    public SuspendedMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString()
    {
        return message;
    }
}
