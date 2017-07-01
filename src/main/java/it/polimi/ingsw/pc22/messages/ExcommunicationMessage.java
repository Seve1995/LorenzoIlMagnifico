package it.polimi.ingsw.pc22.messages;

import it.polimi.ingsw.pc22.gamebox.ExcommunicationCard;

/**
 * Created by fandroid95 on 01/07/2017.
 */
public class ExcommunicationMessage extends Message
{
    private ExcommunicationCard excommunicationCard;

    public ExcommunicationMessage(ExcommunicationCard excommunicationCard)
    {
        this.excommunicationCard = excommunicationCard;
    }

    public ExcommunicationCard getExcommunicationCard()
    {
        return excommunicationCard;
    }
}
