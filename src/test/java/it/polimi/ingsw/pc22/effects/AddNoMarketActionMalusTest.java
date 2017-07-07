package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class AddNoMarketActionMalusTest {
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
		AddNoMarketActionMalus addNoMarketActionMalus = new AddNoMarketActionMalus();

		assertTrue(addNoMarketActionMalus.isLegal(player, gameBoard));
	}
	
	@Test
	public void testExecuteEffect()
	{
		AddNoMarketActionMalus addNoMarketActionMalus = new AddNoMarketActionMalus();
						
		player = new Player("Test", "test", true);
		
		player.setDisableMarket(false);
		
		addNoMarketActionMalus.executeEffects(player, gameBoard);

		assertTrue(player.isDisableMarket());
	}
}
