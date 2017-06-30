package it.polimi.ingsw.pc22.messages;

/**
 * Created by matteo on 24/06/17.
 */
public class ExecutedAction extends Message
{
    private String message;

    public ExecutedAction(String message)
    {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString()
    {
        return "ExecutedAction - " + message;
    }
}

