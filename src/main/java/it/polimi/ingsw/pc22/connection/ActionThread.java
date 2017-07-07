package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.actions.Action;
import it.polimi.ingsw.pc22.actions.ActionFactory;
import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.messages.ErrorMessage;
import it.polimi.ingsw.pc22.messages.GameStatusMessage;

/**
 * Created by fandroid95 on 25/06/2017.
 */
public class ActionThread implements Runnable
{
    private String gameMatchName;

    public ActionThread(String gameMatchName)
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
            //TODO CHECKARE SE IL PLAYER È SUSPENDED

            String actionMessage = adapter.getMessage();

            if (actionMessage == null)
            {
                adapter.printMessage(new ErrorMessage("Action Not received"));

                continue;
            }

            Action action = ActionFactory.createAction(actionMessage, gameMatch.getCurrentPlayer());

            if (action == null)
            {
                adapter.printMessage(new ErrorMessage("Action Not Valid"));

                continue;
            }

            boolean executed = action.executeAction
                    (gameMatch.getCurrentPlayer(), gameMatch.getCurrentGameBoard());

            System.out.println(executed + " - " +  gameMatch.getCurrentPlayer().isHasPassed());

            if (!executed)
            {
                adapter.printMessage(new ErrorMessage("Action not Performed"));

                continue;
            }

            adapter.printMessage(new ErrorMessage("Action Performed"));

            if (gameMatch.getCurrentPlayer().isFamiliarPositioned())
            {
                break;
            }

            if (!(gameMatch.getCurrentPlayer().isFamiliarPositioned()))
            {
                adapter.printMessage(new GameStatusMessage(gameMatch.getCurrentGameBoard(), gameMatch.getCurrentPlayer(), "pick privilege"));
            }

            if (gameMatch.getCurrentPlayer().isHasPassed()
                    || gameMatch.getCurrentPlayer().isSuspended())
                break;
        }

        System.out.println("TURN FINISHED");
    }
}
