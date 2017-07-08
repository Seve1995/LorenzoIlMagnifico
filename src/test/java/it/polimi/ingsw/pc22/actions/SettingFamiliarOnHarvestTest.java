package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.effects.DoHarvestAction;
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
public class SettingFamiliarOnHarvestTest
{
    private Harvest harvest;

    private GameBoard gameBoard;

    private HarvestCell[] harvestCells;

    private Player player;

    private FamilyMember familyMember;

    private DoHarvestAction action;

    private HarvestCell firstCell;

    private HarvestCell secondCell;

    @Before
    public void setUp()
    {
        action = mock(DoHarvestAction.class);

        familyMember = mock(FamilyMember.class);

        gameBoard = mock(GameBoard.class);

        harvest = mock(Harvest.class);

        harvestCells = new HarvestCell[2];

        firstCell = mock(HarvestCell.class);

        secondCell = mock(HarvestCell.class);

        harvestCells[0] = firstCell;

        harvestCells[1] = secondCell;

        player = new Player("Test", "Test", true);

        when(harvest.getHarvestCell()).thenReturn(harvestCells);

        when(gameBoard.getHarvest()).thenReturn(harvest);
    }

    @Test
    public void testFamiliarValue()
    {
        SettingFamiliarMemberOnHarvest settingHarvest
                = new SettingFamiliarMemberOnHarvest(familyMember);

        settingHarvest.setDoHarvestAction(action);

        when(familyMember.getValue()).thenReturn(0);

        assertFalse(settingHarvest.isLegal(player, gameBoard));
    }

    @Test
    public void testBlocked()
    {
        SettingFamiliarMemberOnHarvest settingHarvest
                = new SettingFamiliarMemberOnHarvest();

        settingHarvest.setFamilyMember(familyMember);

        settingHarvest.setDoHarvestAction(action);

        when(familyMember.getValue()).thenReturn(1);

        when(firstCell.getFamilyMember()).thenReturn(familyMember);

        when(secondCell.isABlockedCell()).thenReturn(true);

        assertFalse(settingHarvest.isLegal(player, gameBoard));
    }

    @Test
    public void testIsValid()
    {
        player.setDontCareOccupiedPlaces(false);

        SettingFamiliarMemberOnHarvest settingHarvest
                = new SettingFamiliarMemberOnHarvest();

        settingHarvest.setFamilyMember(familyMember);

        settingHarvest.setDoHarvestAction(action);

        when(familyMember.getValue()).thenReturn(1);

        when(firstCell.getFamilyMember()).thenReturn(null);

        when(secondCell.getFamilyMember()).thenReturn(null);

        when(secondCell.isABlockedCell()).thenReturn(false);

        assertTrue(settingHarvest.isLegal(player, gameBoard));

        when(firstCell.getFamilyMember()).thenReturn(familyMember);

        when(familyMember.getColor()).thenReturn(ColorsEnum.NEUTER);

        when(secondCell.getFamilyMember()).thenReturn(null);

        when(secondCell.isABlockedCell()).thenReturn(false);

        assertTrue(settingHarvest.isLegal(player, gameBoard));

        when(familyMember.getColor()).thenReturn(ColorsEnum.BLACK);

        assertFalse(settingHarvest.isLegal(player, gameBoard));
    }

    @Test
    public void testExecuteFalse()
    {
        SettingFamiliarMemberOnHarvest settingHarvest
                = new SettingFamiliarMemberOnHarvest();

        settingHarvest.setFamilyMember(familyMember);

        settingHarvest.setDoHarvestAction(action);

        player.setDontCareOccupiedPlaces(false);
        player.setFamiliarPositioned(false);

        when(familyMember.getValue()).thenReturn(0);

        assertFalse(settingHarvest.executeAction(player, gameBoard));

        when(familyMember.getValue()).thenReturn(1);

        when(firstCell.getFamilyMember()).thenReturn(familyMember);

        when(secondCell.isABlockedCell()).thenReturn(true);

        when(action.executeEffects(player, gameBoard)).thenReturn(false);

        assertFalse(settingHarvest.executeAction(player, gameBoard));

        when(harvest.firstCellFree()).thenReturn(1);

        assertFalse(settingHarvest.executeAction(player, gameBoard));

        when(action.executeEffects(player, gameBoard)).thenReturn(true);

        assertFalse(player.isFamiliarPositioned());
    }

    @Test
    public void testExecuteTrue()
    {
        SettingFamiliarMemberOnHarvest settingHarvest
                = new SettingFamiliarMemberOnHarvest();

        settingHarvest.setFamilyMember(familyMember);

        settingHarvest.setDoHarvestAction(action);

        player.setDontCareOccupiedPlaces(false);
        player.setFamiliarPositioned(false);

        when(familyMember.getValue()).thenReturn(1);

        when(firstCell.getFamilyMember()).thenReturn(null);

        when(secondCell.getFamilyMember()).thenReturn(null);

        when(secondCell.isABlockedCell()).thenReturn(false);

        when(harvest.firstCellFree()).thenReturn(1);

        when(action.executeEffects(player, gameBoard)).thenReturn(true);

        assertTrue(settingHarvest.executeAction(player, gameBoard));

        assertTrue(player.isFamiliarPositioned());

        player.setFamiliarPositioned(false);

        assertTrue(settingHarvest.executeAction(player, gameBoard));

        assertTrue(player.isFamiliarPositioned());
    }


    @Test
    public void testExecuteDontcare()
    {
        SettingFamiliarMemberOnHarvest settingHarvest
                = new SettingFamiliarMemberOnHarvest();

        settingHarvest.setFamilyMember(familyMember);

        settingHarvest.setDoHarvestAction(action);

        player.setDontCareOccupiedPlaces(true);

        player.setFamiliarPositioned(false);

        when(familyMember.getValue()).thenReturn(1);

        when(firstCell.getFamilyMember()).thenReturn(null);

        when(secondCell.getFamilyMember()).thenReturn(null);

        when(secondCell.isABlockedCell()).thenReturn(false);

        when(action.executeEffects(player, gameBoard)).thenReturn(true);

        assertTrue(settingHarvest.executeAction(player, gameBoard));

        assertTrue(player.isFamiliarPositioned());

        when(action.executeEffects(player, gameBoard)).thenReturn(false);

        assertFalse(settingHarvest.executeAction(player, gameBoard));
    }
}
