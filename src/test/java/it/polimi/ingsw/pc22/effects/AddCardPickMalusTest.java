package it.polimi.ingsw.pc22.effects;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.CardModifier;
import it.polimi.ingsw.pc22.player.Player;
import junit.framework.TestCase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

public class AddCardPickMalusTest extends TestCase{

	private GameBoard gameBoard;

    private Player player;

	private CardTypeEnum cardTypeEnum;
	
	private int malusValue;

	@Before
	public void setUp()
	{
		gameBoard = mock(GameBoard.class);
		
		player = mock(Player.class);
		
		cardTypeEnum = CardTypeEnum.BUILDING;
		
		malusValue = 4;
				
	}
	
	@Test
	public void testGetSet()
	{
		AddCardPickMalus addCardPickMalus = new AddCardPickMalus();
		
		addCardPickMalus.setCardType(cardTypeEnum);
		
		addCardPickMalus.setMalusValue(malusValue);
		
		assertEquals(cardTypeEnum, addCardPickMalus.getCardType());
		
		assertEquals(malusValue, addCardPickMalus.getMalusValue());

	}
	
	@Test
	public void testIsLegal()
	{
		AddCardPickMalus addCardPickMalus = new AddCardPickMalus();

		assertTrue(addCardPickMalus.isLegal(player, gameBoard));
	}
	
	@Test
	public void testExecuteEffect()
	{
		AddCardPickMalus addCardPickMalus = new AddCardPickMalus();

		addCardPickMalus.setCardType(cardTypeEnum);
		
		addCardPickMalus.setMalusValue(malusValue);

		List<CardModifier> cardModifiers = new ArrayList();
				
		when(player.getCardModifiers()).thenReturn(cardModifiers);
						
		addCardPickMalus.executeEffects(player, gameBoard);
		
		assertTrue(player.getCardModifiers().get(0).getCardType().equals(cardTypeEnum));
		
		assertTrue(player.getCardModifiers().get(0).getValueModifier()==malusValue);
		
	}
}
