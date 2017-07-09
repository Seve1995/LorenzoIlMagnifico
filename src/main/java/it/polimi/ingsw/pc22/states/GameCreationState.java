package it.polimi.ingsw.pc22.states;

import it.polimi.ingsw.pc22.client.AuxiliaryRMIThread;
import it.polimi.ingsw.pc22.client.Client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * In this state the player can only type a game match name
 * and a letter. In so doing, a player can create a game match
 * with that name, or join a game match with that name (if it already exists).
 * Typing "r" he/she can join a random match.
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
