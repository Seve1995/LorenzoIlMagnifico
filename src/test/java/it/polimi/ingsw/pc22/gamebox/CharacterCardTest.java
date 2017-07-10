package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.effects.Effect;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CharacterCardTest extends TestCase{
	
	private Asset coinsCost;
	private String name;
	private int cardNumber;
	private int roundNumber;

	private List<Effect> immediateEffects;
	private List<Effect> permanentEffects;

	@Before
	public void setUp()
	{		
		coinsCost = new Asset(3, AssetType.COIN);
		
		name = "TestCharacterCard";
		
		cardNumber = 3;
		
		roundNumber = 2;
				
		immediateEffects = new ArrayList<>();
		
		permanentEffects = new ArrayList<>();
	}
	
	@Test
	public void testGetSet()
	{
		CharacterCard characterCard = new CharacterCard(name, roundNumber, immediateEffects, permanentEffects, cardNumber);
		
		characterCard.setCoinsCost(coinsCost);
		
		assertEquals(coinsCost, characterCard.getCoinsCost());
	}

	@Test
	public void testEquals()
	{
		CharacterCard characterCard1 = new CharacterCard(name, roundNumber, immediateEffects, permanentEffects, cardNumber);
		
		characterCard1.setCoinsCost(coinsCost);
		
		CharacterCard characterCard2 = new CharacterCard(name, roundNumber, immediateEffects, permanentEffects, cardNumber);
		
		characterCard2.setCoinsCost(coinsCost);

		assertTrue(characterCard1.equals(characterCard2));
	}
}
