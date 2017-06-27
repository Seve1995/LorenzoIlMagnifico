package it.polimi.ingsw.pc22.messages;

/**
 * Created by fandroid95 on 20/06/2017.
 */
public class ErrorMessage extends Message
{
    private String message;

    public ErrorMessage(String message)
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
