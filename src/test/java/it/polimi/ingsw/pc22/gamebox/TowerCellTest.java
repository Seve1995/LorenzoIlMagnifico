package it.polimi.ingsw.pc22.gamebox;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;


public class TowerCellTest extends TestCase {

    private TowerCell towerCell;

    @Before
    public void setUp()
    {
        towerCell = new TowerCell();

        towerCell.setDevelopmentCard(null);
    }

    @Test
    public void testGetSet()
    {
        assertNull(towerCell.getDevelopmentCard());
    }

}
