package it.polimi.ingsw.pc22.gamebox;

import junit.framework.TestCase;
import org.junit.Test;

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
