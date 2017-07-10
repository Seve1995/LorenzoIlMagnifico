package it.polimi.ingsw.pc22.effects;

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

}
