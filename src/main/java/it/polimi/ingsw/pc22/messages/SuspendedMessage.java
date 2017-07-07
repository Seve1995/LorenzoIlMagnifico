package it.polimi.ingsw.pc22.messages;


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
