package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.AssetType;
import it.polimi.ingsw.pc22.messages.ErrorMessage;

/**
 * Created by fandroid95 on 29/06/2017.
 */
public class ReceiveServantsDecisionThread implements Runnable
{
    @Override
    public void run()
    {
        Long timeout = GameMatch.getTimeout();

        Long timestamp = System.currentTimeMillis();

        IOAdapter adapter = GameMatch.getCurrentPlayer().getAdapter();

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

            if (servantNumber > GameMatch.getCurrentPlayer().getServants())
            {
                adapter.printMessage(new ErrorMessage("You haven't so much servants"));

                continue;
            }

            Asset servants =  new Asset(servantNumber, AssetType.SERVANT);

            ServantsAction effect =
                    (ServantsAction) GameMatch.getCurrentGameBoard().getCurreEffect();

            effect.setServants(servants);

            break;
        }

        System.out.println("TURN FINISHED");
    }
}
