package it.polimi.ingsw.pc22.states;

import it.polimi.ingsw.pc22.client.Client;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.utils.ParseEnum;
import it.polimi.ingsw.pc22.utils.PositionUtils;

import java.rmi.RemoteException;

/**
 * Created by fandroid95 on 20/06/2017.
 */
public class PlayState implements GenericState
{
    @Override
    public void printState()
    {
        System.out.println("Choose an action to execute:");

        if (!Client.getPlayer().isFamiliarPositioned())
        {
            System.out.println("Available servants: " + Client.getPlayer().getServants());

            StringBuilder availableFamiliarsString = new StringBuilder("Available familiars: ");

            for (FamilyMember f : Client.getPlayer().getUnusedFamiliarMembers())
                availableFamiliarsString.append(f.toString() + " ");

           System.out.println(availableFamiliarsString.toString());

            String actions = PositionUtils.getActionAvailableString(Client.getGameBoard());

            System.out.println(actions);
        }

        System.out.println
        ("- play card <index>" + '\n' +
        "- discard card <index>" + '\n' +
        "- activate card <index>" + '\n' +
        "- pass" + '\n' +
        "- end game / exit game" + '\n'+
        "- show board - player board"); //questa in realtà si può sempre fare
    }

    @Override
    public boolean validate(String string)
    {
        String actionName = ParseEnum.parseAction(string);

        System.out.println("String action name: " + actionName);

        if (actionName == null) return false;

        return true;
    }

    @Override
    public void sendToServer(String string)
    {
        if ("show board".equals(string))
        {
            System.out.println(Client.getPlayer().getPlayerBoard().toString());

            return;
        }

        if ("rmi".equals(Client.getNetworkChoice()))
        {
            try
            {
                Client.getRmiServerInterface().doAction(string, Client.getAssignedID());
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
