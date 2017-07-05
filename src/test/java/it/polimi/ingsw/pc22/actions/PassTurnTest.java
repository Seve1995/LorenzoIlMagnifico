package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by fandroid95 on 04/07/2017.
 */
public class PassTurnTest
{
    private Player player;
    private GameBoard gameBoard;

    @Before
    public void setUp()
    {
        player = new Player("Test", "Test", true);

        gameBoard = mock(GameBoard.class);
    }

    @Test
    public void testIsLegal()
    {
        PassTurn pass = new PassTurn();

        assertTrue(pass.isLegal(player, gameBoard));
    }

    @Test
    public void testExecuted()
    {
        PassTurn pass = new PassTurn();

        assertTrue(pass.executeAction(player, gameBoard));

        assertTrue(player.isHasPassed());
    }

    @Test
    public void equals()
    {
        PassTurn pass = new PassTurn();

        PassTurn pass1 = new PassTurn();

        assertEquals(pass, pass1);

        assertTrue(pass.equals(pass));

        assertFalse(pass.equals(new Integer(1)));

        assertFalse(pass.equals(null));
    }
}
