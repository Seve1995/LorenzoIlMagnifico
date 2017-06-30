package it.polimi.ingsw.pc22.states;

import it.polimi.ingsw.pc22.client.Client;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fandroid95 on 30/06/2017.
 */
public class ChooseCardState implements GenericState
{
    private CardTypeEnum cardType;

    public ChooseCardState(CardTypeEnum cardType)
    {
        this.cardType = cardType;
    }

    @Override
    public void printState()
    {
        if (cardType.equals(CardTypeEnum.ANY))
        {
            System.out.println("Select a Card Type to pick (BUILDING | VENTURE | CHARACTER | TERRITORY)");
        }

        System.out.println("Select a tower floor");

        System.out.println("Write <type> <0-3>");
    }

    @Override
    public boolean validate(String string)
    {
        Pattern cardPattern;

        if (!cardType.equals(CardTypeEnum.ANY))
        {
            cardPattern = Pattern.compile("^[0-3]$");
        }
        else
        {
             cardPattern = Pattern.compile("^(BUILDING|VENTURE|CHARACTER|TERRITORY) [0-3]$");
        }

        Matcher matcher = cardPattern.matcher(string);

        if (!matcher.find())
        {
            System.out.println("INVALID INSERTION RETRY");

            return false;
        }

        return true;
    }

    @Override
    public void sendToServer(String string)
    {
        if ("socket".equals(Client.getNetworkChoice()))
        {
            Client.socketSend(string);
        }
    }
}
