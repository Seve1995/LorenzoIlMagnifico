package it.polimi.ingsw.pc22.messages;

/**
 * Send a notify to a player, to make him/her aware
 * of the execution of an action.
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

