package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class AddProductionValueModifierTest extends TestCase{
	
	private GameBoard gameBoard;

    private Player player;

	private int value;

	@Before
	public void setUp()
	{
		gameBoard = mock(GameBoard.class);
		
		player = mock(Player.class);
		
		value = 3;
	}
	
	@Test
	public void testGetSet()
	{
		AddProductionValueModifier addProductionValueModifier = new AddProductionValueModifier();
		
		addProductionValueModifier.setValue(value);
		
		assertEquals(value, addProductionValueModifier.getValue());
	}

	@Test
	public void testIsLegal()
	{
		AddProductionValueModifier addProductionValueModifier = new AddProductionValueModifier();

		assertTrue(addProductionValueModifier.isLegal(player, gameBoard));
	}
	
	@Test
	public void testExecuteEffect()
	{
		AddProductionValueModifier addProductionValueModifier = new AddProductionValueModifier();
		
		addProductionValueModifier.setValue(value);
				
		player = new Player("Test", "test", true);
		
		player.setProductionValueModifier(0);
		
		addProductionValueModifier.executeEffects(player, gameBoard);

		assertEquals(value, player.getProductionValueModifier());
	}
	
	
}
