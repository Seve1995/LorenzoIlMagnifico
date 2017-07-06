package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.connection.GameServer;
import it.polimi.ingsw.pc22.messages.ErrorMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fandroid95 on 30/06/2017.
 */
public class ReceiveCostsDecisionThread implements Runnable
{
    private String gameMatchName;

    public ReceiveCostsDecisionThread(String gameMatchName) {
        this.gameMatchName = gameMatchName;
    }

    @Override
    public void run()
    {
        Long timeout = GameMatch.getTimeout();

        Long timestamp = System.currentTimeMillis();

        GameMatch gameMatch = GameServer.getGameMatchMap().get(gameMatchName);

        IOAdapter adapter = gameMatch.getCurrentPlayer().getAdapter();

        PickTowerCard effect = (PickTowerCard) gameMatch.getCurrEffect();

        while (System.currentTimeMillis() < timestamp + timeout)
        {
            String costDecision = adapter.getMessage();

            Pattern costMessage = Pattern.compile("^[0-1]$");

            Matcher matcher = costMessage.matcher(costDecision);

            if (!matcher.find())
            {
                adapter.printMessage(new ErrorMessage("INVALID INSERTION RETRY"));

                continue;
            }

            Integer choiceInt = Integer.parseInt(costDecision);

            effect.setCostDecision(choiceInt);

            break;
        }
    }
}
