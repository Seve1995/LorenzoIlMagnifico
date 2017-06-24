package it.polimi.ingsw.pc22.messages;

import java.io.Serializable;

/**
 * Created by fandroid95 on 20/06/2017.
 */
public class CommunicationMessage extends Message implements Serializable
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