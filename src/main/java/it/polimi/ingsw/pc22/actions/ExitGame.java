package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.adapters.SocketIOAdapter;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.messages.SuspendedMessage;
import it.polimi.ingsw.pc22.player.Player;

/**
 * Created by fandroid95 on 06/07/2017.
 */
public class ExitGame extends Action
{
    @Override
    protected boolean isLegal(Player player, GameBoard gameBoard)
    {
        return true;
    }

    @Override
    public boolean executeAction(Player player, GameBoard gameBoard)
    {
        player.setSuspended(true);

        IOAdapter adapter = player.getAdapter();

        adapter.printMessage(new SuspendedMessage("SEI USCITO DAL GIOCO"));

        if (adapter instanceof SocketIOAdapter)
        {
            ((SocketIOAdapter) adapter).setPlayerName(player.getUsername());

            new Thread((SocketIOAdapter) adapter).start();
        }

        return true;
    }
}
