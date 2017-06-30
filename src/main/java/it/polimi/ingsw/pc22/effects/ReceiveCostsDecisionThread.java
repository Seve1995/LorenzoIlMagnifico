package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.messages.ErrorMessage;
import it.polimi.ingsw.pc22.messages.TimerMessage;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fandroid95 on 30/06/2017.
 */
public class ReceiveCostsDecisionThread implements Runnable
{
    @Override
    public void run()
    {
        Long timeout = GameMatch.getTimeout();

        Long timestamp = System.currentTimeMillis();

        IOAdapter adapter = GameMatch.getCurrentPlayer().getAdapter();

        PickTowerCard effect =
                (PickTowerCard) GameMatch.getCurrentGameBoard().getCurreEffect();

        while (System.currentTimeMillis() < timestamp + timeout)
        {
            String costDecision = adapter.getMessage();

            Pattern costMessage = Pattern.compile("^[1-2]$");

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
