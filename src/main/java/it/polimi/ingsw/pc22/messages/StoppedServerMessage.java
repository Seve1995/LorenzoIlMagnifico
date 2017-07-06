package it.polimi.ingsw.pc22.messages;

/**
 * Created by fandroid95 on 06/07/2017.
 */
public class StoppedServerMessage extends Message
{
    private String message;

    public StoppedServerMessage(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }
}
