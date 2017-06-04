package it.polimi.ingsw.pc22.actions;

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

    private int sacrifiedServantsNumber;

    public ServantsHandler(Action action, int sacrifiedServantsNumber)
    {
        super();
        this.action = action;
        this.sacrifiedServantsNumber = sacrifiedServantsNumber;
    }

    @Override
    public boolean isLegal(Player player, GameBoard gameBoard)
    {
    	if(this.sacrifiedServantsNumber > player.getServants()){

    		return false;

    	}

        return true;
    }

    @Override
    public boolean executeAction(Player player, GameBoard gameBoard)
    {
    	
    	double multiplier;
    	
    	if (player.isServantMalus())
    		multiplier=1;
    	
    	else
    		multiplier=0.5;
    	
    	if (isLegal(player, gameBoard)) {
    		
    		 int familiarValue =  action.getFamilyMember().getFamiliarValue();

    	       familiarValue = (int) (familiarValue + this.sacrifiedServantsNumber/(2*multiplier));

    	       action.getFamilyMember().setFamiliarValue(familiarValue);

    	       int servantsNumber = player.getServants();

    	       servantsNumber = (int) (servantsNumber - 2*multiplier*this.sacrifiedServantsNumber);

    	       player.setServants(servantsNumber);

    	       return true;
    	}

    	return false;

    }
}
