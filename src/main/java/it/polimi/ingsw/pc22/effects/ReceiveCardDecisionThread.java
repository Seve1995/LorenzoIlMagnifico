package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.messages.ErrorMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fandroid95 on 30/06/2017.
 */
public class ReceiveCardDecisionThread implements Runnable
{
    @Override
    public void run()
    {
        Long timeout = GameMatch.getTimeout();

        Long timestamp = System.currentTimeMillis();

        IOAdapter adapter = GameMatch.getCurrentPlayer().getAdapter();

        PickTowerCard effect =
                (PickTowerCard) GameMatch.getCurrentGameBoard().getCurreEffect();

        CardTypeEnum currCardType = effect.getCardType();

        while (System.currentTimeMillis() < timestamp + timeout)
        {
            String cardMessage = adapter.getMessage();

            Pattern cardPattern;

            if (!currCardType.equals(CardTypeEnum.ANY))
            {
                cardPattern = Pattern.compile("^[0-3]$");
            }
            else
            {
                cardPattern = Pattern.compile("^(BUILDING|VENTURE|CHARACTER|TERRITORY) [0-3]$");
            }

            Matcher matcher = cardPattern.matcher(cardMessage);

            if (!matcher.find())
            {
                adapter.printMessage(new ErrorMessage("INVALID INSERTION RETRY"));

                continue;
            }

            String[] choices = cardMessage.split(" ");

            Integer floor;

            if (!currCardType.equals(CardTypeEnum.ANY))
            {
                floor = Integer.parseInt(choices[0]);
            }
            else
            {
                floor = Integer.parseInt(choices[1]);

                CardTypeEnum type = CardTypeEnum.valueOf(choices[0]);

                effect.setCardType(type);
            }

            effect.setFloor(floor);

            break;
        }
    }
}
