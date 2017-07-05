package it.polimi.ingsw.pc22.states;

import it.polimi.ingsw.pc22.client.AuxiliaryRMIThread;
import it.polimi.ingsw.pc22.client.Client;
import it.polimi.ingsw.pc22.gamebox.Asset;

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

        System.out.println("0) You must have" + militaryPointsRequired.getValue()
                + "and you will pay" + militaryPointsCost.getValue());

        System.out.println("1) Pay these resources:" + resourcesCost.toString());
    }

    @Override
    public boolean validate(String string)
    {
        Pattern costMessage = Pattern.compile("^[0-1]$");

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
            new Thread(new AuxiliaryRMIThread(string, null, null, null)).start();
        }

        if ("socket".equals(Client.getNetworkChoice()))
        {
            Client.socketSend(string);
        }
    }
}
