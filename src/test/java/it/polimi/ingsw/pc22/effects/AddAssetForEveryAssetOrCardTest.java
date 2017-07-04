package it.polimi.ingsw.pc22.effects;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.AssetType;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;
import junit.framework.TestCase;

public class AddAssetForEveryAssetOrCardTest extends TestCase {
	
	private GameBoard gameBoard;

    private Player player;

	private Asset paidAsset;
	
	private CardTypeEnum paidCardType;
	
	private Asset gainedAsset;
	
	@Before
	public void setUp()
	{
		gameBoard = mock(GameBoard.class);
		
		player = mock(Player.class);
		
		paidAsset = new Asset(2, AssetType.COIN);
		
		gainedAsset = new Asset(4, AssetType.SERVANT);
		
	}
	
	@Test
	public void testGetSet()
	{
		AddAssetForEveryAssetOrCard addAssetForEveryAssetOrCard = new AddAssetForEveryAssetOrCard();
		
		addAssetForEveryAssetOrCard.setGainedAsset(gainedAsset);
		
		addAssetForEveryAssetOrCard.setPaidAsset(paidAsset);
		
		addAssetForEveryAssetOrCard.setPaidCardType(paidCardType);

		assertEquals(gainedAsset, addAssetForEveryAssetOrCard.getGainedAsset());
		
		assertEquals(paidAsset, addAssetForEveryAssetOrCard.getPaidAsset());
		
		assertEquals(paidCardType, addAssetForEveryAssetOrCard.getPaidCardType());
	}
	
    @Test
    public void testIsLegal()
    {
		AddAssetForEveryAssetOrCard addAssetForEveryAssetOrCard = new AddAssetForEveryAssetOrCard();
		
    	assertTrue(addAssetForEveryAssetOrCard.isLegal(player, gameBoard));
    }
    
    @Test
    public void testExecuteEffectsNullPaidCardTypeAndGainedAsset()
    {
		AddAssetForEveryAssetOrCard addAssetForEveryAssetOrCard = new AddAssetForEveryAssetOrCard();
		
    	paidCardType = null;
    	
    	addAssetForEveryAssetOrCard.setPaidCardType(paidCardType);
    	
    	gainedAsset = null;
    	
    	addAssetForEveryAssetOrCard.setGainedAsset(gainedAsset);
    	
    	assertEquals(addAssetForEveryAssetOrCard.executeEffects(player, gameBoard), false);
    }
}
