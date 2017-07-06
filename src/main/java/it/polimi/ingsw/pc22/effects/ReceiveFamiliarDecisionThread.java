package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.connection.GameServer;
import it.polimi.ingsw.pc22.gamebox.ColorsEnum;
import it.polimi.ingsw.pc22.messages.ErrorMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fandroid95 on 03/07/2017.
 */
public class ReceiveFamiliarDecisionThread implements Runnable
{
    private String gameMatchName;

    public ReceiveFamiliarDecisionThread(String gameMatchName) {
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
            String familiarMessage = adapter.getMessage();

            if (familiarMessage == null)
            {
                adapter.printMessage(new ErrorMessage("Invalid Choice retry"));

                continue;
            }

            Pattern familiarPattern =
                    Pattern.compile("^(BLACK|ORANGE|WHITE|NEUTER)$");

            Matcher matcher = familiarPattern.matcher(familiarMessage);

            if (!matcher.find())
            {
                adapter.printMessage(new ErrorMessage("Invalid Choice retry"));

                continue;
            }

            ColorsEnum color = ColorsEnum.getColorFromString(familiarMessage);


            FamilyMemberModifier effect = (FamilyMemberModifier)
                    gameMatch.getCurrEffect();

            effect.setFamilyMemberColor(color);

            break;
        }
    }
}
