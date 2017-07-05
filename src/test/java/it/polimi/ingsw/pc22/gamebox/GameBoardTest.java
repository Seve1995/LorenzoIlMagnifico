package it.polimi.ingsw.pc22.gamebox;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;


public class GameBoardTest extends TestCase {

    private Tower[] towers = new Tower[4];
    private GameBoard gameBoard = new GameBoard();

    @Before
    public void setUp()
    {
        towers[0] = new Tower(CardTypeEnum.VENTURE);
        towers[1] = new Tower(CardTypeEnum.BUILDING);
        towers[2] = new Tower(CardTypeEnum.CHARACTER);
        towers[3] = new Tower(CardTypeEnum.TERRITORY);

        gameBoard.setTowers(towers);
    }

    @Test
    public void testGetTowerByNameNull()
    {


        assertEquals(null, gameBoard.getTowerByType(CardTypeEnum.ANY));

    }

    @Test
    public void testGetTowerByNameStandard()
    {
        assertEquals(towers[1], gameBoard.getTowerByType(CardTypeEnum.BUILDING));
    }
}


