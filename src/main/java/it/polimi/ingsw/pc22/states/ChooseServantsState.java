package it.polimi.ingsw.pc22.states;

import it.polimi.ingsw.pc22.client.Client;

/**
 * Created by fandroid95 on 27/06/2017.
 */
public class ChooseServantsState implements GenericState
{
    @Override
    public void printState()
    {
        System.out.println("Would you like to sacrifice up to " + Client.getPlayer().getServants() + "?");
        System.out.println("write a number from  0  to" + Client.getPlayer().getServants());
    }

    @Override
    public boolean validate(String string)
    {
        Integer servantsNumber;

        try
        {
            servantsNumber = Integer.parseInt(string);

        }
            catch (NumberFormatException e)
        {
            return false;
        }

        if (servantsNumber == null)
            return false;

        if (servantsNumber > Client.getPlayer().getServants())
            return false;

        return false;
    }

    @Override
    public void sendToServer(String string)
    {
        if ("rmi".equals(Client.getNetworkChoice()))
        {
            //TODO DA IMPLEMENTARE
        }

        if ("socket".equals(Client.getNetworkChoice()))
        {
            Client.socketSend(string);
        }
    }
}
