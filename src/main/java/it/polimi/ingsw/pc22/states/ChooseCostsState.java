package it.polimi.ingsw.pc22.states;

import it.polimi.ingsw.pc22.client.Client;
import it.polimi.ingsw.pc22.gamebox.Asset;

import java.rmi.RemoteException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fandroid95 on 30/06/2017.
 */
public class ChooseCostsState implements GenericState
{
    private Asset militaryPointsRequired;
    private Asset militaryPointsCost;
    private List<Asset> resourcesCost;

    public ChooseCostsState
        (Asset militaryPointsRequired, Asset militaryPointsCost, List<Asset> resourcesCost)
    {
        this.militaryPointsRequired = militaryPointsRequired;
        this.militaryPointsCost = militaryPointsCost;
        this.resourcesCost = resourcesCost;
    }

    @Override
    public void printState()
    {
        System.out.println("You have to choose one cost between:");

        System.out.println("1) You must have" + militaryPointsRequired.getValue()
                + "and you will pay" + militaryPointsCost.getValue());

        System.out.println("2) Pay these resources:" + resourcesCost.toString());
    }

    @Override
    public boolean validate(String string)
    {
        Pattern costMessage = Pattern.compile("^[1-2]$");

        Matcher matcher = costMessage.matcher(string);

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
            try
            {
                Client.getRmiServerInterface().takeCostsDecision
                        (string, Client.getAssignedID());
            }
            catch (RemoteException e)
            {
                e.printStackTrace();
            }
        }

        if ("socket".equals(Client.getNetworkChoice()))
        {
            Client.socketSend(string);
        }
    }
}
