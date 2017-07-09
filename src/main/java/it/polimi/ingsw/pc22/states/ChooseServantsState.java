package it.polimi.ingsw.pc22.states;

import it.polimi.ingsw.pc22.client.AuxiliaryRMIThread;
import it.polimi.ingsw.pc22.client.Client;

/**
 * In this state the player can only type a number of servants (less than his/her servants)
 * to increment de value of a production/harvest action.
 */
public class ChooseServantsState implements GenericState
{
    @Override
    public void printState()
    {
        System.out.println("Would you like to sacrifice up to " + Client.getPlayer().getServants() + "?");
        System.out.println("write a number from  0  to " + Client.getPlayer().getServants());
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
            System.out.println("Invalid Input");

            return false;
        }

        if (servantsNumber == null)
        {
            System.out.println("Invalid Input");

            return false;
        }


        if (servantsNumber > Client.getPlayer().getServants())
        {
            System.out.println("Invalid Input");

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
