package it.polimi.ingsw.pc22.messages;


/**
 * This message notifies all the player
 * that the server is down, for some reasons.
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

	@Override
	public String toString() {
		return message;
	}
    
}
