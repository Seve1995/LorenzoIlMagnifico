package it.polimi.ingsw.pc22.gamebox;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by matteo on 04/07/17.
 */
public class ProductionTest extends TestCase {

    private ProductionCell[] productionCells = new ProductionCell[5];
    private ProductionCell[] productionCellsFull = new ProductionCell[3];
    private FamilyMember[] familyMembers = new FamilyMember[3];
    private Production production = new Production();

    @Before
    public void setUp()
    {

        for (int j=0; j<5; j++)
        {
            productionCells[j] = new ProductionCell();

        }

        for (int i=0; i < 3; i++)
        {
            familyMembers[i]= new FamilyMember();
            productionCellsFull[i]= new ProductionCell();
            productionCellsFull[i].setFamilyMember(familyMembers[i]);
            productionCells[i].setFamilyMember(familyMembers[i]);
        }

    }

    @Test
    public void testFirstCellFreeStandard()
    {
        production.setProductionCell(productionCells);

        assertEquals(3, production.firstCellFree());
    }

    @Test
    public void testFirstCellFreeFull()
    {
        production.setProductionCell(productionCellsFull);

        assertEquals(-1, production.firstCellFree());
    }



}





