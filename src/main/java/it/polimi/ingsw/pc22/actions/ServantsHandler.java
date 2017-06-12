package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

/**
 * Created by fandroid95 on 24/05/2017.
 */
public class ServantsHandler extends Action
{
    //COMMENTO AI POSTERI NON C'ENTRA NA MAZZA CON IL PATTERN DECORATOR
    //MA FUNZIONA E CI PERMETTE DI AGGIUNGERE QUESTA FUNZIONALITA' SOLO SE NECESSARIA

    private Action action;

    private Asset servants;

    public ServantsHandler(Action action, Asset servants)
    {
        super();
        this.action = action;
        this.servants = servants;
    }

    @Override
    public boolean isLegal(Player player, GameBoard gameBoard)
    {
    	int servantsNumber = servants.getValue();

    	if(servantsNumber > player.getServants())
			return false;

        return true;
    }

    @Override
    public boolean executeAction(Player player, GameBoard gameBoard)
    {
		int servantsNumber = servants.getValue();

		double multiplier;
    	
    	if (player.isServantMalus())
    		multiplier=1;
    	
    	else
    		multiplier=0.5;
    	
    	if (!isLegal(player, gameBoard)) return false;

		int familiarValue = action.getFamilyMember().getFamiliarValue();

		familiarValue = (int) (familiarValue + servantsNumber/(2*multiplier));

		action.getFamilyMember().setFamiliarValue(familiarValue);

		int currServants = player.getServants();

		servantsNumber = (int) (currServants - 2 * multiplier * servantsNumber);

		player.setServants(servantsNumber);

		return action.executeAction(player, gameBoard);
    }
}
