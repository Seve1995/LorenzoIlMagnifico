package it.polimi.ingsw.pc22.messages;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by fandroid95 on 20/06/2017.
 */
public class ErrorMessage extends Message implements Serializable
{
    private String message;

    public ErrorMessage(String message)
    {
        this.message = message;
    }
}
