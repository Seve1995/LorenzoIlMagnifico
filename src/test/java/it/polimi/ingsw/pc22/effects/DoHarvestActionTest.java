package it.polimi.ingsw.pc22.effects;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.connection.GameServer;
import it.polimi.ingsw.pc22.gamebox.BonusTile;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.gamebox.PlayerBoard;
import it.polimi.ingsw.pc22.player.Player;
import junit.framework.TestCase;

public class DoHarvestActionTest extends TestCase{
	
	private GameBoard gameBoard;

    private Player player;
    
    private PlayerBoard playerBoard;
    
    private BonusTile bonusTile;

    private int value;

    @Before
    public void setUp()
    {
        player = new Player("Test", "test", true);
        
        gameBoard = mock(GameBoard.class);
                
        playerBoard = new PlayerBoard();
        
        bonusTile = new BonusTile();
    }

    @Test
    public void testGetSet()
    {
    	DoHarvestAction doHarvestAction = new DoHarvestAction();
    	
    	doHarvestAction.setValue(value);
    	
    	assertEquals(value, doHarvestAction.getValue());
    	
    }
    
    @Test
    public void testIsLegal()
    {
    	DoHarvestAction doHarvestAction = new DoHarvestAction();
    	
    	doHarvestAction.setValue(value);
    	
    	bonusTile.setHarvestActivationValue(1);
    	
    	playerBoard.setBonusTile(bonusTile);
    	
    	player.setPlayerBoard(playerBoard);
    	
    	value = 0;
    	
    	assertFalse(doHarvestAction.isLegal(player, gameBoard));
    	
    	value = 1;
    	
    	doHarvestAction.setValue(value);

    	assertTrue(doHarvestAction.isLegal(player, gameBoard));
    }
    /*
    @Test
    public void testExecuteEffect()
    {
    	DoHarvestAction doHarvestAction = new DoHarvestAction();
    	
    	doHarvestAction.setValue(value);
    	
    	bonusTile.setHarvestActivationValue(1);
    	
    	playerBoard.setBonusTile(bonusTile);
    	
    	player.setPlayerBoard(playerBoard);

    	value = 1;
    	
    	GameMatch gameMatch = new GameMatch(500L, 4);
    	
    	when(gameBoard.getGameMatchName()).thenReturn("Test");
    	
    	GameServer.getGameMatchMap().put("Test", gameMatch);
    	
    	ServantsAction servantAction = mock(ServantsAction.class);
    	
    	when(servantAction.waitForResult()).thenReturn(false);
    	
    	assertFalse(doHarvestAction.executeEffects(player, gameBoard));
    }
    */
}
