package it.polimi.ingsw.pc22.states;

import it.polimi.ingsw.pc22.client.AuxiliaryRMIThread;
import it.polimi.ingsw.pc22.client.Client;
import it.polimi.ingsw.pc22.gamebox.Asset;

import java.util.List;

/**
 * When a player is in this state can only
 * type 0/1 to choose a resource in a certain moment
 * of the game.
 */
public class ChooseAssetsState implements GenericState
{
    private List<Asset> assetList;

    public ChooseAssetsState(List<Asset> assetList)
    {
        this.assetList = assetList;
    }

    @Override
    public void printState()
    {
        System.out.println("Choose one asset:");

        for (int j=0; j < assetList.size(); j++)
        {
            System.out.println(j  + ") " + assetList.get(j).toString());
        }
    }

    @Override
    public boolean validate(String string)
    {
        Integer assetNumber;

        try
        {
            assetNumber = Integer.parseInt(string);

        }
        catch (NumberFormatException e)
        {
            return false;
        }

        if (assetNumber == null)
        {
            System.out.println("Invalid Value retry");

            return false;
        }

        if (assetNumber > assetList.size() || assetNumber < 0)
        {
            System.out.println("Invalid Value retry");

            return false;
        }


        return true;
    }

    @Override
    public void sendToServer(String string)
    {
        if ("rmi".equals(Client.getNetworkChoice()))
        {
            new Thread(new AuxiliaryRMIThread(string, assetList, null, null)).start();
        }

        if ("socket".equals(Client.getNetworkChoice()))
        {
            Client.socketSend(string);
        }
    }
}
