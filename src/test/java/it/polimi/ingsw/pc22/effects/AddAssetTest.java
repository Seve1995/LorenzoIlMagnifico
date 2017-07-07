package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.AssetType;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class AddAssetTest extends TestCase
{
    private GameBoard gameBoard;

    private Player player;
    
    private Asset asset;

	@Before
    public void setUp()
    {
		gameBoard = new GameBoard();

        player = mock(Player.class);
        
        asset = new Asset(2, AssetType.COIN);
    }
	
	@Test
	public void testGetSetAsset()
	{
		
		AddAsset addAsset = new AddAsset();
		
		addAsset.setAsset(asset);
		
		assertEquals(asset, addAsset.getAsset());
	}
	
	@Test
	public void testToString()
	{
		
		AddAsset addAsset = new AddAsset();
		
		addAsset.setAsset(asset);
		
		assertEquals(asset.toString(), addAsset.toString());
	}
	
    @Test
    public void testIsLegal()
    {
    	AddAsset addAsset = new AddAsset();
    	
    	assertTrue(addAsset.isLegal(player, gameBoard));
    }
    
    @Test
    public void testExecuteEffect()
    {
    	AddAsset addAsset = new AddAsset();

    	assertTrue(addAsset.executeEffects(player, gameBoard));
    	
    }
    
}