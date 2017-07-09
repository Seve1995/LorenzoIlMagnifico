package it.polimi.ingsw.pc22.messages;

import it.polimi.ingsw.pc22.player.Player;

/**
 * If something goes wrong we send this message.
 * Some details are written in the String attribute.
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
