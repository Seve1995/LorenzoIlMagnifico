package it.polimi.ingsw.pc22.messages;

/**
 * Created by fandroid95 on 20/06/2017.
 */
public class LoginMessage extends Message
{
    private boolean userLogged;
    private boolean matchStarted;

    private static final String LOGGED_MESSAGE = "User successfully logged";
    private static final String STARTED = "Match is starting. Please wait...";

    public LoginMessage(boolean userLogged, boolean matchStarted)
    {
        this.userLogged = userLogged;
        this.matchStarted = matchStarted;
    }

    public boolean isUserLogged() {
        return userLogged;
    }

    public boolean isMatchStarted() {
        return matchStarted;
    }

    public static String getLoggedMessage() {
        return LOGGED_MESSAGE;
    }

    public static String getStarded() {
        return STARTED;
    }
}
