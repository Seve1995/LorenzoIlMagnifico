package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.connection.GameMatch;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class ChooseAssetTest extends TestCase{
	
	private Integer chosenAssetsToPay;
	
	@Before
	public void setUp()
	{
		chosenAssetsToPay = 2;
	}
	
	@Test
	public void testGetSet()
	{
		ChooseAsset chooseAsset = new ChooseAsset();
		
		chooseAsset.setChosenAssetsToPay(chosenAssetsToPay);
		
		assertEquals(chosenAssetsToPay, chooseAsset.getChosenAssetsToPay());
	}

	@Test
	public void testWaitForResult()
	{
		ChooseAsset chooseAsset = new ChooseAsset();
		
		chooseAsset.setChosenAssetsToPay(chosenAssetsToPay);
			
		GameMatch gameMatch = new GameMatch(500L, 4, 1000L);
								
		assertTrue(chooseAsset.waitForResult());
		
		chooseAsset.setChosenAssetsToPay(null);
		
		assertFalse(chooseAsset.waitForResult());

	}
}
