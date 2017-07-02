package it.polimi.ingsw.pc22.states;

import it.polimi.ingsw.pc22.client.AuxiliaryRMIThread;
import it.polimi.ingsw.pc22.client.Client;
import it.polimi.ingsw.pc22.gamebox.ExcommunicationCard;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fandroid95 on 01/07/2017.
 */
public class ExcommunicateState implements GenericState
{
    private ExcommunicationCard excommunicationCard;

    @Override
    public void printState()
    {
        System.out.println("Choose if you want to sacrifice your faith point or be excommunicated");
        System.out.println("write 1) sacrifice");
        System.out.println("write 2) excommunicate");
    }

    @Override
    public boolean validate(String string)
    {
        Pattern excommunicate = Pattern.compile("^[1-2]$");

        Matcher matcher = excommunicate.matcher(string);

        if (!matcher.find())
        {
            System.out.println("Invalid number please Retry");

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
