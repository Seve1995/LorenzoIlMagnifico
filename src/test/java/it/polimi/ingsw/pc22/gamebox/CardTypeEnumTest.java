package it.polimi.ingsw.pc22.gamebox;

import junit.framework.TestCase;
import org.junit.Test;

public class CardTypeEnumTest extends TestCase{
	
	@Test
	public void testGetCardTypeFromString() 
	{
		String test = "VENTURE";
		
		assertEquals(CardTypeEnum.VENTURE, CardTypeEnum.getCardTypeFromString(test));
		
		test = "undefined";
		
		assertEquals(null, CardTypeEnum.getCardTypeFromString(test));
	}

}
