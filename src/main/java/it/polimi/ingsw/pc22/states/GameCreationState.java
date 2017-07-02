package it.polimi.ingsw.pc22.states;

import it.polimi.ingsw.pc22.client.AuxiliaryRMIThread;
import it.polimi.ingsw.pc22.client.Client;

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
        String message = Client.getPlayer().getUsername() + ":" + string;

        if ("rmi".equals(Client.getNetworkChoice()))
        {
            new Thread(new AuxiliaryRMIThread(message, null, null, null)).start();
        }

        if ("socket".equals(Client.getNetworkChoice()))
        {
            Client.socketSend(message);
        }
    }
}
