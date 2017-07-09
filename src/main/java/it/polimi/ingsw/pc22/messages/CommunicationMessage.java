package it.polimi.ingsw.pc22.messages;

/**
 * Communication message, just to notify the player
 * about something, contained in the string.
 */
public class CommunicationMessage extends Message
{
    private String message;

    public CommunicationMessage(String message)
    {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    
	@Override
	public String toString() {
		return message;
	}
}
