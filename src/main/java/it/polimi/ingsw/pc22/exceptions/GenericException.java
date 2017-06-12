package it.polimi.ingsw.pc22.exceptions;

/**
 * Created by fandroid95 on 12/06/2017.
 */
public class GenericException extends RuntimeException
{
    public GenericException() {
        super();
    }

    public GenericException(String message) {
        super(message);
    }

    public GenericException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public GenericException(Throwable cause) {
        super(cause);
    }
}
