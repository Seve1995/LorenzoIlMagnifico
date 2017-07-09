package it.polimi.ingsw.pc22.gamebox;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;


public class ProductionCellTest extends TestCase {

    private ProductionCell productionCell;

    @Before
    public void setUp()
    {
        productionCell = new ProductionCell();
        productionCell.setABlockedCell(true);
        productionCell.setAPenaltyCell(true);
    }

    @Test
    public void testGetSet()
    {
        assertTrue(productionCell.isABlockedCell());
        assertTrue(productionCell.isAPenaltyCell());
    }

}
