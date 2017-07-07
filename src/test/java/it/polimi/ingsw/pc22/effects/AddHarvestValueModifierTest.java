package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class AddHarvestValueModifierTest extends TestCase{
	
	private GameBoard gameBoard;

    private Player player;

	private int harvestValue;

	@Before
	public void setUp()
	{
		gameBoard = mock(GameBoard.class);
		
		player = mock(Player.class);
		
		harvestValue = 3;
	}
	
	@Test
	public void testGetSet()
	{
		AddHarvestValueModifier addHarvestValueModifier = new AddHarvestValueModifier();
		
		addHarvestValueModifier.setValue(harvestValue);
		
		assertEquals(harvestValue, addHarvestValueModifier.getValue());
	}

	@Test
	public void testIsLegal()
	{
		AddHarvestValueModifier addHarvestValueModifier = new AddHarvestValueModifier();

		assertTrue(addHarvestValueModifier.isLegal(player, gameBoard));
	}
	
	@Test
	public void testExecuteEffect()
	{
		AddHarvestValueModifier addHarvestValueModifier = new AddHarvestValueModifier();
		
		addHarvestValueModifier.setValue(harvestValue);
				
		player = new Player("Test", "test", true);
		
		player.setHarvestValueModifier(0);
		
		addHarvestValueModifier.executeEffects(player, gameBoard);

		assertEquals(harvestValue, player.getHarvestValueModifier());
	}
	
	
}
