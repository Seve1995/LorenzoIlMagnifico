package it.polimi.ingsw.pc22.effects;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;
import junit.framework.TestCase;

public class AddServantsHandlerMalusTest extends TestCase{
	private GameBoard gameBoard;

    private Player player;

	@Before
	public void setUp()
	{
		gameBoard = mock(GameBoard.class);
		
		player = mock(Player.class);
	}
	
	@Test
	public void testIsLegal()
	{
		AddServantsHandlerMalus addServantsHandlerMalus = new AddServantsHandlerMalus();

		assertTrue(addServantsHandlerMalus.isLegal(player, gameBoard));
	}
	
	@Test
	public void testExecuteEffect()
	{
		AddServantsHandlerMalus addServantsHandlerMalus = new AddServantsHandlerMalus();
						
		player = new Player("Test", "test", true);
		
		player.setServantMalus(false);
		
		addServantsHandlerMalus.executeEffects(player, gameBoard);

		assertTrue(player.isServantMalus());
	}

}
