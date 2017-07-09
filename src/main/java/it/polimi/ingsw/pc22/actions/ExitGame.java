package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.adapters.SocketIOAdapter;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.messages.SuspendedMessage;
import it.polimi.ingsw.pc22.player.Player;

/**
 * This class represent the action used to exit from the game,
 * it suspends the player that later can relog to the same game
 * or choose to start a new game
 */
public class ExitGame extends Action
{
    @Override
    public boolean isLegal(Player player, GameBoard gameBoard)
    {
        return true;
    }

    @Override
    public boolean executeAction(Player player, GameBoard gameBoard)
    {
        player.setSuspended(true);

        IOAdapter adapter = player.getAdapter();

        adapter.printMessage(new SuspendedMessage("YOU'VE LEFT THE GAME"));

        if (adapter instanceof SocketIOAdapter)
        {
            ((SocketIOAdapter) adapter).setPlayerName(player.getUsername());

            new Thread((SocketIOAdapter) adapter).start();
        }

        return true;
    }
}
