package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.actions.Action;
import it.polimi.ingsw.pc22.actions.ActionFactory;
import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.messages.ErrorMessage;
import it.polimi.ingsw.pc22.messages.ExecutedAction;

/**
 * Created by fandroid95 on 25/06/2017.
 */
public class ActionThread implements Runnable
{
    @Override
    public void run()
    {
        Long timeout = GameMatch.getTimeout();

        Long timestamp = System.currentTimeMillis();

        IOAdapter adapter = GameMatch.getCurrentPlayer().getAdapter();

        while (System.currentTimeMillis() < timestamp + timeout)
        {
            String actionMessage = adapter.getMessage();

            if (actionMessage == null)
            {
                adapter.printMessage(new ErrorMessage("Action Not received"));

                continue;
            }

            Action action = ActionFactory.createAction(actionMessage, GameMatch.getCurrentPlayer());

            System.out.println("Action: " + action);

            if (action == null)
            {
                adapter.printMessage(new ErrorMessage("Action Not Valid"));

                continue;
            }

            boolean executed = action.executeAction
                    (GameMatch.getCurrentPlayer(), GameMatch.getCurrentGameBoard());

            System.out.println(executed + " - " +  GameMatch.getCurrentPlayer().isHasPassed());

            if (!executed)
            {

                adapter.printMessage(new ErrorMessage("Action not Performed"));

                continue;
            }

            if (!GameMatch.getCurrentPlayer().isFamiliarPositioned())
            {
                adapter.printMessage(new ErrorMessage("Action not Performed"));

                continue;
            }

            if (GameMatch.getCurrentPlayer().isFamiliarPositioned())
            {
                adapter.printMessage(new ExecutedAction("Action Performed"));

                break;
            }

            if (GameMatch.getCurrentPlayer().isHasPassed())
                break;
        }

        System.out.println("TURN FINISHED");
    }
}
