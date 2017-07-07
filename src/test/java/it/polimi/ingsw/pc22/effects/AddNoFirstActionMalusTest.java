package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class AddNoFirstActionMalusTest extends TestCase{

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
		AddNoFirstActionMalus addNoFirstActionMalus = new AddNoFirstActionMalus();

		assertTrue(addNoFirstActionMalus.isLegal(player, gameBoard));
	}
	
	@Test
	public void testExecuteEffect()
	{
		AddNoFirstActionMalus addNoFirstActionMalus = new AddNoFirstActionMalus();
						
		player = new Player("Test", "test", true);
		
		player.setNoFirstAction(false);
		
		addNoFirstActionMalus.executeEffects(player, gameBoard);

		assertTrue(player.isNoFirstAction());
	}
}
