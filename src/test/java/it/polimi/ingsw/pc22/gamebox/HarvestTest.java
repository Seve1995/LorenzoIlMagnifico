package it.polimi.ingsw.pc22.gamebox;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by matteo on 04/07/17.
 */
public class HarvestTest extends TestCase {

    private HarvestCell[] harvestCells = new HarvestCell[5];
    private HarvestCell[] harvestCellsFull = new HarvestCell[3];
    private FamilyMember[] familyMembers = new FamilyMember[3];
    private Harvest harvest = new Harvest();

    @Before
    public void setUp()
    {

        for (int j=0; j<5; j++)
        {
            harvestCells[j] = new HarvestCell();

        }

        for (int i=0; i < 3; i++)
        {
            familyMembers[i]= new FamilyMember();
            harvestCellsFull[i]= new HarvestCell();
            harvestCellsFull[i].setFamilyMember(familyMembers[i]);
            harvestCells[i].setFamilyMember(familyMembers[i]);
        }

    }

    @Test
    public void testFirstCellFreeStandard()
    {
        harvest.setHarvestCell(harvestCells);

        assertEquals(3, harvest.firstCellFree());
    }

    @Test
    public void testFirstCellFreeFull()
    {
        harvest.setHarvestCell(harvestCellsFull);

        assertEquals(-1, harvest.firstCellFree());
    }



}
