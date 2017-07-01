package it.polimi.ingsw.pc22.messages;

/**
 * Created by fandroid95 on 01/07/2017.
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
