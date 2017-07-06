package it.polimi.ingsw.pc22.utils;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.connection.GameServer;
import it.polimi.ingsw.pc22.messages.ErrorMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fandroid95 on 01/07/2017.
 */
public class ReceiveExcommunicationDecisionThread implements Runnable
{
    private String gameMatchName;

    public ReceiveExcommunicationDecisionThread(String gameMatchName)
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
            String excommunicationMessage = adapter.getMessage();

            if (excommunicationMessage == null)
            {
                adapter.printMessage(new ErrorMessage("INVALID INSERTION RETRY"));

                continue;
            }

            Pattern costMessage = Pattern.compile("^[1-2]$");

            Matcher matcher = costMessage.matcher(excommunicationMessage);

            if (!matcher.find())
            {
                adapter.printMessage(new ErrorMessage("INVALID INSERTION RETRY"));

                continue;
            }

            Integer choiceInt = Integer.parseInt(excommunicationMessage);

            gameMatch.getCurrentPlayer().setExcommunicationChoice(choiceInt);

            break;
        }
    }
}
