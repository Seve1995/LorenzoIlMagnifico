package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.connection.GameServer;
import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.AssetType;
import it.polimi.ingsw.pc22.messages.ErrorMessage;


/**
 * Thread used to handle the choice about the number of servants to sacrifice.
 */


public class ReceiveServantsDecisionThread implements Runnable
{
    private String gameMatchName;

    public ReceiveServantsDecisionThread(String gameMatchName)
    {
        this.gameMatchName = gameMatchName;
    }

    @Override
    public void run()
    {
        Long timeout = GameMatch.getTimeout();

        Long timestamp = System.currentTimeMillis();

        GameMatch gameMatch = GameServer.getGameMatchMap().get(gameMatchName);

        IOAdapter adapter = gameMatch.getCurrentPlayer().getAdapter();

        while (System.currentTimeMillis() < timestamp + timeout)
        {
            String servantsMessage = adapter.getMessage();

            Integer servantNumber;

            try
            {
                servantNumber = Integer.parseInt(servantsMessage);
            }
            catch (NumberFormatException e)
            {
                adapter.printMessage(new ErrorMessage("ERROR! You must enter a valid input"));

                continue;
            }

            if (servantNumber > gameMatch.getCurrentPlayer().getServants())
            {
                adapter.printMessage(new ErrorMessage("You haven't so much servants"));

                continue;
            }

            Asset servants =  new Asset(servantNumber, AssetType.SERVANT);

            ServantsAction effect = (ServantsAction) gameMatch.getCurrEffect();

            effect.setServants(servants);

            break;
        }

    }
}
