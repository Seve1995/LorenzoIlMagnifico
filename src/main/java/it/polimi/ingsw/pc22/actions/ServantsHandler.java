package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.player.Player;

/**
 * Created by fandroid95 on 24/05/2017.
 */
public class ServantsHandler extends Action
{
    //COMMENTO AI POSTERI NON C'ENTRA NA MAZZA CON IL PATTERN DECORATOR
    //MA FUNZIONA E CI PERMETTE DI AGGIUNGERE QUESTA FUNZIONALITA' SOLO SE NECESSARIA

    private Action action;

    private int sacrifiedServantsNumber;

    public ServantsHandler(Action action, int sacrifiedServantsNumber)
    {
        super();
        this.action = action;
        this.sacrifiedServantsNumber = sacrifiedServantsNumber;
    }

    @Override
    public boolean isLegal(Player player)
    {

        //TODO DA IMPLEMENTARE
        return false;
    }

    @Override
    public boolean executeAction(Player player)
    {
       int familiarValue =  action.getFamilyMember().getFamiliarValue();

        familiarValue = familiarValue + this.sacrifiedServantsNumber;

       action.getFamilyMember().setFamiliarValue(familiarValue);

       int servantsNumber = player.getServants();

       servantsNumber = servantsNumber - this.sacrifiedServantsNumber;

       player.setServants(servantsNumber);

       return action.executeAction(player);
    }
}
