package it.polimi.ingsw.pc22.states;

import it.polimi.ingsw.pc22.client.Client;
import it.polimi.ingsw.pc22.gamebox.Asset;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by fandroid95 on 27/06/2017.
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
            System.out.println(j+1 + ") " + assetList.get(j).toString());
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
            try
            {
                Client.getRmiServerInterface().takeAssetDecision
                        (string, Client.getAssignedID(), assetList);
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
