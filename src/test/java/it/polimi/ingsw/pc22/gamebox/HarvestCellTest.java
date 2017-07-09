package it.polimi.ingsw.pc22.gamebox;


import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class HarvestCellTest extends TestCase{

    private HarvestCell harvestCell;

    @Before
    public void setUp()
    {
        harvestCell = new HarvestCell();

        harvestCell.setABlockedCell(false);
        harvestCell.setAPenaltyCell(false);

    }

    @Test
    public void testGetSet()
    {
        assertFalse(harvestCell.isABlockedCell());
        assertFalse(harvestCell.isAPenaltyCell());
    }


}
