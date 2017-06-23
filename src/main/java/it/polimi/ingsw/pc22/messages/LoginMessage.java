package it.polimi.ingsw.pc22.messages;

import java.io.Serializable;

/**
 * Created by fandroid95 on 20/06/2017.
 */
public class LoginMessage extends Message implements Serializable
{
    private boolean userLogged;
    private boolean matchStarted;

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
    
    
}
