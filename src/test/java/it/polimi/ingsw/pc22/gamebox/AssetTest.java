package it.polimi.ingsw.pc22.gamebox;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class AssetTest extends TestCase{

	private int value;
	
	private AssetType assetType;
	
	@Before
	public void setUp()
	{
		value = 3;
		
		assetType = AssetType.MILITARYPOINT;
	}
	
	@Test
	public void testGetSet()
	{
		Asset asset = new Asset(value, assetType);
		
		value++;
		
		assetType = AssetType.COIN;
		
		asset.setValue(value);
		
		asset.setType(assetType);
		
		assertEquals(value, asset.getValue());
		
		assertEquals(assetType, asset.getType());
	}
	
	@Test
	public void testToString()
	{
		Asset asset = new Asset(value, assetType);
		
		String expectedString = "3 MILITARYPOINT";
		
		assertEquals(expectedString, asset.toString());
	}
	
	@Test
	public void testEquals()
	{
		Asset asset1 = new Asset(value, assetType);
		
		Asset asset2 = new Asset(value, assetType);
		
		assertTrue(asset1.equals(asset2));
	}
}
