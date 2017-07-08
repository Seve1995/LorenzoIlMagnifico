package it.polimi.ingsw.pc22.exceptions;

/**
 * Simple exception to manage possible errors alongside the code.
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
