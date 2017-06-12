package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

/**
 * Created by fandroid95 on 11/06/2017.
 */
public class PassTurn extends Action
{
    @Override
    protected boolean isLegal(Player player, GameBoard gameBoard)
    {
        return true;
    }

    @Override
    public boolean executeAction(Player player, GameBoard gameBoard)
    {
        player.setHasPassed(true);

        return true;
    }
}
