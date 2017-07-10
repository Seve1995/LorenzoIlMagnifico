package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.effects.AddAsset;
import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.player.Player;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class CellTest extends TestCase{

	private int requiredDiceValue;
	
	private FamilyMember familyMember;
	
	private List<Effect> effects;

	private Player player;
	
	private GameBoard gameBoard;
	
	@Before
	public void setUp()
	{
		requiredDiceValue = 3;
		
		familyMember = new FamilyMember();
		
		familyMember.setColor(ColorsEnum.BLACK);
		
		effects = new ArrayList<>();
		
		gameBoard = mock(GameBoard.class);
	}
	
	@Test
	public void testGetSetRemoveisEmpty()
	{
		Cell cell = new Cell();
		
		cell.setRequiredDiceValue(requiredDiceValue);
		
		cell.setEffects(effects);
		
		cell.setFamilyMember(familyMember);
		
		assertEquals(requiredDiceValue, cell.getRequiredDiceValue());
		
		assertEquals(effects, cell.getEffects());
		
		assertEquals(familyMember, cell.getFamilyMember());
		
		assertFalse(cell.isEmpty());

		cell.removeFamilyMember(familyMember);
		
		assertEquals(null, cell.getFamilyMember());
		
		assertTrue(cell.isEmpty());
	}
	
	@Test
	public void testExecuteEffect()
	{

		Cell cell = new Cell();
		
		cell.setRequiredDiceValue(requiredDiceValue);
		
		AddAsset addAsset = new AddAsset();
		
		addAsset.setAsset(new Asset(3, AssetType.COIN));
		
		effects.add(addAsset);
		
		cell.setEffects(effects);
		
		cell.setFamilyMember(familyMember);
		
		player = new Player("test", "test", true);
		
		assertTrue(cell.executeEffects(player, gameBoard));
		
		AddAsset addAsset2 = mock(AddAsset.class);
		
		when(addAsset2.executeEffects(player, gameBoard)).thenReturn(false);
		
		effects.add(addAsset2);
		
		cell.setEffects(effects);
		
		assertFalse(cell.executeEffects(player, gameBoard));
	}
	
}
