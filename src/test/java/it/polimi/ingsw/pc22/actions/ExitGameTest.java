package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.adapters.RMIIOAdapter;
import it.polimi.ingsw.pc22.adapters.SocketIOAdapter;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Created by fandroid95 on 08/07/2017.
 */
public class ExitGameTest
{
    private Player player;
    private GameBoard gameBoard;

    private IOAdapter adapter;

    @Before
    public void setUp()
    {
        player = new Player("Test", "Test", true);

        gameBoard = mock(GameBoard.class);


    }

    @Test
    public void testIsLegal()
    {
        ExitGame exit = new ExitGame();

        assertTrue(exit.isLegal(player, gameBoard));
    }

    @Test
    public void testExecuted()
    {
        ExitGame exit = new ExitGame();

        adapter = mock(RMIIOAdapter.class);

        player.setAdapter(adapter);

        assertTrue(exit.executeAction(player, gameBoard));

        assertTrue(player.isSuspended());
    }

    @Test
    public void testExecutedSocket()
    {
        ExitGame exit = new ExitGame();

        adapter = mock(SocketIOAdapter.class);

        player.setAdapter(adapter);

        assertTrue(exit.executeAction(player, gameBoard));

        assertTrue(player.isSuspended());
    }
}
