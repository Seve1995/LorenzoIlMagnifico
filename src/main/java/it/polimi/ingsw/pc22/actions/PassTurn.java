package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

/**
 * Created by fandroid95 on 11/06/2017.
 */
public class PassTurn extends Action
{
    @Override
    public boolean isLegal(Player player, GameBoard gameBoard)
    {
        return true;
    }

    @Override
    public boolean executeAction(Player player, GameBoard gameBoard)
    {
        player.setHasPassed(true);

        return true;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (! (o instanceof PassTurn))
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }
}
