package it.polimi.ingsw.pc22.messages;

/**
 * This message inform a player that is timeout is finished.
 */
public class TimerMessage extends Message
{
    private String message;

    public TimerMessage(String message)
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
