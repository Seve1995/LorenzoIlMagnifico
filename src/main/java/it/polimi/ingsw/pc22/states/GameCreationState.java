package it.polimi.ingsw.pc22.states;

import it.polimi.ingsw.pc22.client.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fandroid95 on 13/06/2017.
 */
public class GameCreationState implements GenericState
{
    @Override
    public void printState()
    {
        System.out.println("Write: <gameName> C|J|R");
        System.out.println
        (
            "C to create a new Game" + '\n' +
            "J to join an existing Game" + '\n' +
            "R to join a random Game"
        );
}

    @Override
    public boolean validate(String string)
    {
        Pattern gameMatcher = Pattern.compile("(^(\\w+) (C|c|J|j|R|r)$)");

        Matcher matcher = gameMatcher.matcher(string);

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
        string = Client.getPlayer().getName() + ":" + string;

        System.out.println(string);

        if ("rmi".equals(Client.getNetworkChoice()))
        {
            try
            {
                Client.getRmiServerInterface().matchHandling(string, Client.getAssignedID());
            }
            catch (RemoteException e)
            {
                e.printStackTrace();
            }
        }

        if ("socket".equals(Client.getNetworkChoice()))
        {
            try
            {
                PrintWriter outSocket = new PrintWriter(Client.getSocket().getOutputStream(), true);

                outSocket.println(string);

            } catch (IOException e)

            {
                e.printStackTrace();
            }
        }
    }
}
