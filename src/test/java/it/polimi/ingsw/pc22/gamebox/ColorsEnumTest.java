package it.polimi.ingsw.pc22.gamebox;

import org.junit.Test;

import junit.framework.TestCase;

public class ColorsEnumTest extends TestCase{
	
	@Test
	public void testGetColorFromString()
	{
		String test = "BLACK";
		
		assertEquals(ColorsEnum.BLACK, ColorsEnum.getColorFromString(test));
		
		test = "undefined";
		
		assertEquals(null, ColorsEnum.getColorFromString(test));
	}
}
