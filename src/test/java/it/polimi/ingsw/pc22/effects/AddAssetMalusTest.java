package it.polimi.ingsw.pc22.effects;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.AssetType;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;
import junit.framework.TestCase;

import static org.mockito.Mockito.mock;

public class AddAssetMalusTest extends TestCase{

	private GameBoard gameBoard;

    private Player player;

	private Asset asset;

	@Before
	public void setUp()
	{
		gameBoard = mock(GameBoard.class);
		
		player = mock(Player.class);
		
		asset = new Asset(2, AssetType.COIN);
				
	}

	@Test
	public void testGetSet()
	{
		AddAssetMalus addAssetMalus = new AddAssetMalus();
		
		addAssetMalus.setAsset(asset);
		
		assertEquals(asset, addAssetMalus.getAsset());
	}
	
	@Test
	public void testIsLegal()
	{
		AddAssetMalus addAssetMalus = new AddAssetMalus();
				
		assertTrue(addAssetMalus.isLegal(player, gameBoard));
	}
	
	@Test
	public void testExecuteEffect()
	{
		AddAssetMalus addAssetMalus = new AddAssetMalus();

		player = new Player("Test", "test", true);
		
		Asset military = new Asset(3, AssetType.MILITARYPOINT);
		
		addAssetMalus.setAsset(military);
		
		addAssetMalus.executeEffects(player, gameBoard);
		
		assertTrue(player.isMilitaryPointsMalus());
		
		Asset coin = new Asset(3, AssetType.COIN);
		
		addAssetMalus.setAsset(coin);
		
		addAssetMalus.executeEffects(player, gameBoard);
		
		assertTrue(player.isCoinMalus());
		
		Asset servant = new Asset(3, AssetType.SERVANT);
		
		addAssetMalus.setAsset(servant);
		
		addAssetMalus.executeEffects(player, gameBoard);
		
		assertTrue(player.isServantMalus());

		Asset stone = new Asset(3, AssetType.STONE);
		
		addAssetMalus.setAsset(stone);
		
		addAssetMalus.executeEffects(player, gameBoard);
		
		assertTrue(player.isStoneMalus());

		Asset wood = new Asset(3, AssetType.WOOD);
		
		addAssetMalus.setAsset(wood);
		
		addAssetMalus.executeEffects(player, gameBoard);
		
		assertTrue(player.isWoodMalus());
		
		Asset faithPoint = new Asset(3, AssetType.FAITHPOINT);
		
		addAssetMalus.setAsset(faithPoint);
		
		addAssetMalus.executeEffects(player, gameBoard);
		
		assertSame(player, player);
	}
}
