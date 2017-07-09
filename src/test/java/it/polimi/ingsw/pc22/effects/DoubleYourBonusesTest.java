package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class DoubleYourBonusesTest extends TestCase{
	
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
		DoubleYourBonuses doubleYourBonuses = new DoubleYourBonuses();
		
		assertTrue(doubleYourBonuses.isLegal(player, gameBoard));
	}
	
	@Test
	public void testExecuteEffect()
	{
		DoubleYourBonuses doubleYourBonuses = new DoubleYourBonuses();
		
		doubleYourBonuses.executeEffects(player, gameBoard);
		
		assertTrue(player.isSantaRita());
		
	}

	
}
