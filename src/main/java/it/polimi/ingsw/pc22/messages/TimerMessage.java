package it.polimi.ingsw.pc22.messages;

/**
 * Created by fandroid95 on 21/06/2017.
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
