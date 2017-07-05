package it.polimi.ingsw.pc22.effects;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.AssetType;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;
import junit.framework.TestCase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
public class AddFamilyMembersMalusTest extends TestCase{
	
	private GameBoard gameBoard;

    private Player player;

	private int diceValueMalus;

	@Before
	public void setUp()
	{
		gameBoard = mock(GameBoard.class);
		
		player = mock(Player.class);
		
		diceValueMalus = 3;
				
	}
	
	@Test
	public void testGetSet()
	{
		AddFamilyMembersMalus addFamilyMemberMalus = new AddFamilyMembersMalus();
		
		addFamilyMemberMalus.setDiceValueMalus(diceValueMalus);
		
		assertEquals(diceValueMalus, addFamilyMemberMalus.getDiceValueMalus());
	}
	
	@Test
	public void testIsLegal()
	{
		AddFamilyMembersMalus addFamilyMemberMalus = new AddFamilyMembersMalus();

		assertTrue(addFamilyMemberMalus.isLegal(player, gameBoard));
	}
	
	@Test
	public void testExecuteEffect()
	{
		AddFamilyMembersMalus addFamilyMemberMalus = new AddFamilyMembersMalus();

		addFamilyMemberMalus.setDiceValueMalus(diceValueMalus);
		
		List<FamilyMember> familyMembers = new ArrayList<>();
		
		familyMembers.add(new FamilyMember());
		
		when(player.getFamilyMembers()).thenReturn(familyMembers);
		
		addFamilyMemberMalus.executeEffects(player, gameBoard);
		
		assertEquals(diceValueMalus, player.getFamilyMembers().get(0).getValueModifier());
	}
	
}
