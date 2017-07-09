package it.polimi.ingsw.pc22.gamebox;


import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TowerTest extends TestCase
{
    private Tower tower;

    @Before
    public void setUp()
    {
        tower = new Tower(CardTypeEnum.VENTURE);
        tower.setTowerCells(null);
        tower.setTowerType(CardTypeEnum.BUILDING);
        tower.setListPlayers(null);
    }

    @Test
    public void testGetSet()
    {
        assertEquals(null, tower.getTowerCells());
        assertEquals(CardTypeEnum.BUILDING, tower.getTowerType());
        assertEquals(null, tower.getListPlayers());
    }

    @Test
    public void testGetAvailableCells()
    {
        List<TowerCell> towerCells = new ArrayList<>();
        List<TowerCell> towerCellsTotal = new ArrayList<>();

        TowerCell cell1 = new TowerCell();
        TowerCell cell2 = new TowerCell();
        TowerCell cell3 = new TowerCell();
        TowerCell cell4 = new TowerCell();
        cell1 = mock(TowerCell.class);
        cell2 = mock(TowerCell.class);
        cell3 = mock(TowerCell.class);
        cell4 = mock(TowerCell.class);

        when(cell1.isEmpty()).thenReturn(true);
        when(cell3.isEmpty()).thenReturn(true);

        when(cell2.isEmpty()).thenReturn(false);
        when(cell4.isEmpty()).thenReturn(false);

        towerCells.add(cell1);
        towerCells.add(cell3);

        towerCellsTotal.add(cell1);
        towerCellsTotal.add(cell2);
        towerCellsTotal.add(cell3);
        towerCellsTotal.add(cell4);

        tower.setTowerCells(towerCellsTotal);


        assertEquals(towerCells, tower.getAvailableCells());

    }

}
