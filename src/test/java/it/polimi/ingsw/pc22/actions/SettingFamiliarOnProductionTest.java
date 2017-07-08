package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.effects.DoProductionAction;
import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.player.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by fandroid95 on 08/07/2017.
 */
public class SettingFamiliarOnProductionTest
{
    private Production production;

    private GameBoard gameBoard;

    private ProductionCell[] productionCells;

    private Player player;

    private FamilyMember familyMember;

    private DoProductionAction action;

    private ProductionCell firstCell;

    private ProductionCell secondCell;

    @Before
    public void setUp()
    {
        action = mock(DoProductionAction.class);

        familyMember = mock(FamilyMember.class);

        gameBoard = mock(GameBoard.class);

        production = mock(Production.class);

        productionCells = new ProductionCell[2];

        firstCell = mock(ProductionCell.class);

        secondCell = mock(ProductionCell.class);

        productionCells[0] = firstCell;

        productionCells[1] = secondCell;

        player = new Player("Test", "Test", true);

        when(production.getProductionCell()).thenReturn(productionCells);

        when(gameBoard.getProduction()).thenReturn(production);
    }

    @Test
    public void testFamiliarValue()
    {
        SettingFamiliarMemberOnProduction settingProduction
                = new SettingFamiliarMemberOnProduction(familyMember);

        settingProduction.setDoProductionAction(action);

        when(familyMember.getValue()).thenReturn(0);

        assertFalse(settingProduction.isLegal(player, gameBoard));
    }

    @Test
    public void testBlocked()
    {
        SettingFamiliarMemberOnProduction settingProduction
                = new SettingFamiliarMemberOnProduction();

        settingProduction.setFamilyMember(familyMember);

        settingProduction.setDoProductionAction(action);

        when(familyMember.getValue()).thenReturn(1);

        when(firstCell.getFamilyMember()).thenReturn(familyMember);

        when(secondCell.isABlockedCell()).thenReturn(true);

        assertFalse(settingProduction.isLegal(player, gameBoard));
    }

    @Test
    public void testIsValid()
    {
        player.setDontCareOccupiedPlaces(false);

        SettingFamiliarMemberOnProduction settingProduction
                = new SettingFamiliarMemberOnProduction();

        settingProduction.setFamilyMember(familyMember);

        settingProduction.setDoProductionAction(action);

        when(familyMember.getValue()).thenReturn(1);

        when(firstCell.getFamilyMember()).thenReturn(null);

        when(secondCell.getFamilyMember()).thenReturn(null);

        when(secondCell.isABlockedCell()).thenReturn(false);

        assertTrue(settingProduction.isLegal(player, gameBoard));

        when(firstCell.getFamilyMember()).thenReturn(familyMember);

        when(familyMember.getColor()).thenReturn(ColorsEnum.NEUTER);

        when(secondCell.getFamilyMember()).thenReturn(null);

        when(secondCell.isABlockedCell()).thenReturn(false);

        assertTrue(settingProduction.isLegal(player, gameBoard));

        when(familyMember.getColor()).thenReturn(ColorsEnum.BLACK);

        assertFalse(settingProduction.isLegal(player, gameBoard));
    }

    @Test
    public void testExecuteFalse()
    {
        SettingFamiliarMemberOnProduction settingProduction
                = new SettingFamiliarMemberOnProduction();

        settingProduction.setFamilyMember(familyMember);

        settingProduction.setDoProductionAction(action);

        player.setDontCareOccupiedPlaces(false);
        player.setFamiliarPositioned(false);

        when(familyMember.getValue()).thenReturn(0);

        assertFalse(settingProduction.executeAction(player, gameBoard));

        when(familyMember.getValue()).thenReturn(1);

        when(firstCell.getFamilyMember()).thenReturn(familyMember);

        when(secondCell.isABlockedCell()).thenReturn(true);

        when(action.executeEffects(player, gameBoard)).thenReturn(false);

        assertFalse(settingProduction.executeAction(player, gameBoard));

        when(production.firstCellFree()).thenReturn(1);

        assertFalse(settingProduction.executeAction(player, gameBoard));

        when(action.executeEffects(player, gameBoard)).thenReturn(true);

        assertFalse(player.isFamiliarPositioned());
    }

    @Test
    public void testExecuteTrue()
    {
        SettingFamiliarMemberOnProduction settingProduction
                = new SettingFamiliarMemberOnProduction();

        settingProduction.setFamilyMember(familyMember);

        settingProduction.setDoProductionAction(action);

        player.setDontCareOccupiedPlaces(false);
        player.setFamiliarPositioned(false);

        when(familyMember.getValue()).thenReturn(1);

        when(firstCell.getFamilyMember()).thenReturn(null);

        when(secondCell.getFamilyMember()).thenReturn(null);

        when(secondCell.isABlockedCell()).thenReturn(false);

        when(production.firstCellFree()).thenReturn(1);

        when(action.executeEffects(player, gameBoard)).thenReturn(true);

        assertTrue(settingProduction.executeAction(player, gameBoard));

        assertTrue(player.isFamiliarPositioned());

        player.setFamiliarPositioned(false);

        assertTrue(settingProduction.executeAction(player, gameBoard));

        assertTrue(player.isFamiliarPositioned());
    }


    @Test
    public void testExecuteDontcare()
    {
        SettingFamiliarMemberOnProduction settingProduction
                = new SettingFamiliarMemberOnProduction();

        settingProduction.setFamilyMember(familyMember);

        settingProduction.setDoProductionAction(action);

        player.setDontCareOccupiedPlaces(true);

        player.setFamiliarPositioned(false);

        when(familyMember.getValue()).thenReturn(1);

        when(firstCell.getFamilyMember()).thenReturn(null);

        when(secondCell.getFamilyMember()).thenReturn(null);

        when(secondCell.isABlockedCell()).thenReturn(false);

        when(action.executeEffects(player, gameBoard)).thenReturn(true);

        assertTrue(settingProduction.executeAction(player, gameBoard));

        assertTrue(player.isFamiliarPositioned());

        when(action.executeEffects(player, gameBoard)).thenReturn(false);

        assertFalse(settingProduction.executeAction(player, gameBoard));
    }
}
