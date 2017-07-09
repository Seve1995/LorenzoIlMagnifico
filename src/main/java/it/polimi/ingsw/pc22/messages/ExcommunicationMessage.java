package it.polimi.ingsw.pc22.messages;

import it.polimi.ingsw.pc22.gamebox.ExcommunicationCard;

/**
 * This message is sent only when a player has enough faith points
 * for this era. It asks, in practice, if he/she wants the malus
 * of the excommunication.
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
