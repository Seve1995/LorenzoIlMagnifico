package it.polimi.ingsw.pc22.states;

import it.polimi.ingsw.pc22.client.AuxiliaryRMIThread;
import it.polimi.ingsw.pc22.client.Client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * In this state the player can only type a username
 * and a password. Then he/she can only sign up
 * or login with those data.
 */
public class AuthenticationState implements GenericState
{
    @Override
    public void printState()
    {
        System.out.println("Write: <username> <password> l/s");
    }

    @Override
    public boolean validate(String string)
    {
        Pattern loginPattern = Pattern.compile("(^(\\w+) (\\w+) (L|S|l|s)$)");

        Matcher matcher = loginPattern.matcher(string);

        if (!matcher.find())
        {
            System.out.println("Invalid INPUT");

            return false;
        }

        return true;
    }

    @Override
    public void sendToServer(String string)
    {
        if ("rmi".equals(Client.getNetworkChoice()))
        {
            new Thread(new AuxiliaryRMIThread(string, null, null, null)).start();
        }

        if ("socket".equals(Client.getNetworkChoice()))
        {
            Client.socketSend(string);
        }
    }
}
