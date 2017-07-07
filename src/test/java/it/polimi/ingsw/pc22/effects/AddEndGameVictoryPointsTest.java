package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.AssetType;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class AddEndGameVictoryPointsTest extends TestCase{

	private GameBoard gameBoard;

    private Player player;

	private Asset asset;

	@Before
	public void setUp()
	{
		gameBoard = mock(GameBoard.class);
		
		player = mock(Player.class);
		
		asset = new Asset(3, AssetType.VICTORYPOINT);
				
	}
	
	@Test
	public void testGetSet()
	{
		AddEndGameVictoryPoints addEndGameVictoryPoints = new AddEndGameVictoryPoints();
		
		addEndGameVictoryPoints.setAsset(asset);
		
		assertEquals(asset, addEndGameVictoryPoints.getAsset());
	}
	
	@Test
	public void testIsLegal()
	{
		AddEndGameVictoryPoints addEndGameVictoryPoints = new AddEndGameVictoryPoints();

		assertTrue(addEndGameVictoryPoints.isLegal(player, gameBoard));
	}
	
	@Test
	public void testExecuteEffect()
	{
		AddEndGameVictoryPoints addEndGameVictoryPoints = new AddEndGameVictoryPoints();

		addEndGameVictoryPoints.setAsset(asset);
				
		player = new Player("Test", "test", true);
		
		player.setEndGameVictoryPoints(0);
		
		addEndGameVictoryPoints.executeEffects(player, gameBoard);
		
		assertEquals(asset.getValue(), player.getEndGameVictoryPoints());
		
	}
}

