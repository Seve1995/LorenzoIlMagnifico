package it.polimi.ingsw.pc22.gamebox;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;


public class MarketCellTest extends TestCase {

    private MarketCell marketCell;

    @Before
    public void setUp()
    {
        marketCell = new MarketCell(1, null);
        marketCell.setABlockedCell(true);

    }

    @Test
    public void testGetSet()
    {
        assertTrue(marketCell.isABlockedCell());

    }
}
