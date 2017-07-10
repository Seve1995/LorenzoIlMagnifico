package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.effects.Effect;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DevelopmentCardTest extends TestCase{
	
	private String name;
	private int cardNumber;
	private int roundNumber;
	private boolean permanentEffectChoice;

	private List<Effect> immediateEffects;
	private List<Effect> permanentEffects;

	@Before
	public void setUp()
	{		
		name = "TestCard";
		
		cardNumber = 3;
		
		roundNumber = 2;
		
		permanentEffectChoice = false;
		
		immediateEffects = new ArrayList<>();
		
		permanentEffects = new ArrayList<>();
	}
	
	@Test
	public void testGetSet()
	{
		DevelopmentCard developmentCard = new DevelopmentCard();
		
		developmentCard.setName(name);
		developmentCard.setCardNumber(cardNumber);
		developmentCard.setRoundNumber(roundNumber);
		developmentCard.setPermanentEffectChoiche(permanentEffectChoice);
		developmentCard.setImmediateEffects(immediateEffects);
		developmentCard.setPermanentEffects(permanentEffects);
		
		assertEquals(name, developmentCard.getName());
		assertEquals(cardNumber, developmentCard.getCardNumber());
		assertEquals(roundNumber, developmentCard.getRoundNumber());
		assertEquals(permanentEffectChoice, developmentCard.isPermanentEffectChoice());
		assertEquals(immediateEffects, developmentCard.getImmediateEffects());
		assertEquals(permanentEffects, developmentCard.getPermanentEffects());
	}
	
	@Test
	public void testEquals()
	{
		DevelopmentCard developmentCard = new DevelopmentCard();
		
		developmentCard.setName(name);
		developmentCard.setCardNumber(cardNumber);
		developmentCard.setRoundNumber(roundNumber);
		developmentCard.setPermanentEffectChoiche(permanentEffectChoice);
		developmentCard.setImmediateEffects(immediateEffects);
		developmentCard.setPermanentEffects(permanentEffects);

		DevelopmentCard developmentCard2 = new DevelopmentCard();
		
		developmentCard2.setName(name);
		developmentCard2.setCardNumber(cardNumber);
		developmentCard2.setRoundNumber(roundNumber);
		developmentCard2.setPermanentEffectChoiche(permanentEffectChoice);
		developmentCard2.setImmediateEffects(immediateEffects);
		developmentCard2.setPermanentEffects(permanentEffects);

		assertTrue(developmentCard.equals(developmentCard2));
	}
 
}
