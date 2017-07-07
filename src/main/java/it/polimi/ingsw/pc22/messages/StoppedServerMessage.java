package it.polimi.ingsw.pc22.messages;



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
