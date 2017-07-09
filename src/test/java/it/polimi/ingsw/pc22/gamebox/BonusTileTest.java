package it.polimi.ingsw.pc22.gamebox;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class BonusTileTest extends TestCase{
	
	private int number;
	private Asset productionServantBonus;
	private Asset productionCoinsBonus;
	private Asset productionMilitaryPointsBonus;
	private int productionActivationValue;
	private Asset harvestServantBonus;
	private Asset harvestCoinsBonus;
	private Asset harvestMilitaryPointsBonus;
	private Asset harvestWoodsBonus;
	private Asset harvestStonesBonus;
	private int harvestActivationValue;

	@Before
	public void setUp() 
	{
		number = 0;
		productionServantBonus = new Asset(3, AssetType.SERVANT);
		productionCoinsBonus = new Asset(2, AssetType.COIN);
		productionMilitaryPointsBonus = new Asset(1, AssetType.MILITARYPOINT);
		productionActivationValue = 1;
		harvestServantBonus = new Asset(3, AssetType.SERVANT);
		harvestCoinsBonus = new Asset(2, AssetType.COIN);
		harvestMilitaryPointsBonus = new Asset(1, AssetType.MILITARYPOINT);
		harvestWoodsBonus = new Asset(2, AssetType.WOOD);
		harvestStonesBonus = new Asset(5, AssetType.STONE);
		harvestActivationValue = 1;
	}
	
	@Test
	public void testGetSet()
	{
		BonusTile bonusTile = new BonusTile();
		bonusTile.setNumber(number);
		bonusTile.setProductionServantBonus(productionServantBonus);
		bonusTile.setProductionCoinsBonus(productionCoinsBonus);
		bonusTile.setProductionMilitaryPointsBonus(productionMilitaryPointsBonus);
		bonusTile.setProductionActivationValue(productionActivationValue);
		bonusTile.setHarvestServantBonus(harvestServantBonus);
		bonusTile.setHarvestCoinsBonus(harvestCoinsBonus);
		bonusTile.setHarvestMilitaryPointsBonus(harvestMilitaryPointsBonus);
		bonusTile.setHarvestWoodsBonus(harvestWoodsBonus);
		bonusTile.setHarvestStonesBonus(harvestStonesBonus);
		bonusTile.setHarvestActivationValue(harvestActivationValue);
		
		assertEquals(number, bonusTile.getNumber());
		assertEquals(productionServantBonus, bonusTile.getProductionServantBonus());
		assertEquals(productionCoinsBonus, bonusTile.getProductionCoinsBonus());
		assertEquals(productionMilitaryPointsBonus, bonusTile.getProductionMilitaryPointsBonus());
		assertEquals(productionActivationValue, bonusTile.getProductionActivationValue());
		assertEquals(harvestServantBonus, bonusTile.getProductionServantBonus());
		assertEquals(harvestCoinsBonus, bonusTile.getHarvestCoinsBonus());
		assertEquals(harvestMilitaryPointsBonus, bonusTile.getHarvestMilitaryPointsBonus());
		assertEquals(harvestWoodsBonus, bonusTile.getHarvestWoodsBonus());
		assertEquals(harvestActivationValue, bonusTile.getHarvestActivationValue());
	}
	
	@Test
	public void testEquals()
	{
		BonusTile bonusTile = new BonusTile();
		bonusTile.setNumber(number);
		bonusTile.setProductionServantBonus(productionServantBonus);
		bonusTile.setProductionCoinsBonus(productionCoinsBonus);
		bonusTile.setProductionMilitaryPointsBonus(productionMilitaryPointsBonus);
		bonusTile.setProductionActivationValue(productionActivationValue);
		bonusTile.setHarvestServantBonus(harvestServantBonus);
		bonusTile.setHarvestCoinsBonus(harvestCoinsBonus);
		bonusTile.setHarvestMilitaryPointsBonus(harvestMilitaryPointsBonus);
		bonusTile.setHarvestWoodsBonus(harvestWoodsBonus);
		bonusTile.setHarvestStonesBonus(harvestStonesBonus);
		bonusTile.setHarvestActivationValue(harvestActivationValue);

		BonusTile bonusTile2 = new BonusTile();
		bonusTile2.setNumber(number);
		bonusTile2.setProductionServantBonus(productionServantBonus);
		bonusTile2.setProductionCoinsBonus(productionCoinsBonus);
		bonusTile2.setProductionMilitaryPointsBonus(productionMilitaryPointsBonus);
		bonusTile2.setProductionActivationValue(productionActivationValue);
		bonusTile2.setHarvestServantBonus(harvestServantBonus);
		bonusTile2.setHarvestCoinsBonus(harvestCoinsBonus);
		bonusTile2.setHarvestMilitaryPointsBonus(harvestMilitaryPointsBonus);
		bonusTile2.setHarvestWoodsBonus(harvestWoodsBonus);
		bonusTile2.setHarvestStonesBonus(harvestStonesBonus);
		bonusTile2.setHarvestActivationValue(harvestActivationValue);

		assertTrue(bonusTile.equals(bonusTile2));
	}
	
	@Test
	public void testToString()
	{
		BonusTile bonusTile = new BonusTile();
		bonusTile.setNumber(number);
		bonusTile.setProductionServantBonus(productionServantBonus);
		bonusTile.setProductionCoinsBonus(productionCoinsBonus);
		bonusTile.setProductionMilitaryPointsBonus(productionMilitaryPointsBonus);
		bonusTile.setProductionActivationValue(productionActivationValue);
		bonusTile.setHarvestServantBonus(harvestServantBonus);
		bonusTile.setHarvestCoinsBonus(harvestCoinsBonus);
		bonusTile.setHarvestMilitaryPointsBonus(harvestMilitaryPointsBonus);
		bonusTile.setHarvestWoodsBonus(harvestWoodsBonus);
		bonusTile.setHarvestStonesBonus(harvestStonesBonus);
		bonusTile.setHarvestActivationValue(harvestActivationValue);
		
		String expectedString = "BonusTile:\n" +
				"[PROD.]ServantBonus:" + "3" +
				"|CoinsBonus:" + "2" +
				"|MilPointsBonus:" + "1" + "\n" +
				"[HARV.]ServantBonus:" + "3" +
				"|CoinsBonus:" + "2" +
				"|MilPointsBonus:" + "1" +
				"|WoodsBonus:" + "2" +
				"|StonesBonus:" + "5";
		
		assertEquals(expectedString, bonusTile.toString());
	}

}
