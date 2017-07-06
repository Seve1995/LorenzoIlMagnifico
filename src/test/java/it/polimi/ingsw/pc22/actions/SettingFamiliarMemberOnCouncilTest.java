package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.CouncilPalace;
import it.polimi.ingsw.pc22.gamebox.CouncilPalaceCell;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by fandroid95 on 04/07/2017.
 */
public class SettingFamiliarMemberOnCouncilTest extends TestCase
{
    private GameBoard gameBoard;
    private FamilyMember familyMember;

    private CouncilPalace palace;
    private CouncilPalaceCell cell;

    private CouncilPalaceCell[] cells;

    private Player player;

    @Before
    public void setUp()
    {
        palace = mock(CouncilPalace.class);

        cell = mock(CouncilPalaceCell.class);

        cells = new CouncilPalaceCell[1];

        cells[0] = cell;

        gameBoard = new GameBoard();

        gameBoard.setCouncilPalace(palace);

        familyMember = mock(FamilyMember.class);

        player = mock(Player.class);
    }

    @Test
    public void testIsLegal()
    {
        SettingFamiliarMemberOnCouncilPalace settingFamiliar
                = new SettingFamiliarMemberOnCouncilPalace(familyMember);

        when(familyMember.getValue()).thenReturn(1);

        assertTrue(settingFamiliar.isLegal(player,gameBoard));

        when(familyMember.getValue()).thenReturn(0);

        assertFalse(settingFamiliar.isLegal(player,gameBoard));
    }

    @Test
    public void testExecuteNotLegal()
    {
        SettingFamiliarMemberOnCouncilPalace settingFamiliar
                = new SettingFamiliarMemberOnCouncilPalace();

        settingFamiliar.setFamilyMember(familyMember);

        when(familyMember.getValue()).thenReturn(0);

        assertFalse(settingFamiliar.executeAction(player, gameBoard));
    }

    @Test
    public void testExecuteNotExecuted()
    {
        SettingFamiliarMemberOnCouncilPalace settingFamiliar
                = new SettingFamiliarMemberOnCouncilPalace();

        settingFamiliar.setFamilyMember(familyMember);

        when(familyMember.getValue()).thenReturn(1);

        List<Player> players = new ArrayList<>();

        players.add(player);

        when(palace.firstCellFree()).thenReturn(0);

        when(palace.getCouncilPalaceCells()).thenReturn(cells);

        when(cell.executeEffects(player, gameBoard)).thenReturn(false);

        assertFalse(settingFamiliar.executeAction(player, gameBoard));
    }

    @Test
    public void testExecute()
    {
        FamilyMember familyMember = new FamilyMember();

        familyMember.setFamiliarValue(1);

        SettingFamiliarMemberOnCouncilPalace settingFamiliar
                = new SettingFamiliarMemberOnCouncilPalace();

        settingFamiliar.setFamilyMember(familyMember);

        Player player = new Player("Test", "Test", false);

        when(palace.firstCellFree()).thenReturn(0);

        when(palace.getCouncilPalaceCells()).thenReturn(cells);

        when(cell.executeEffects(player, gameBoard)).thenReturn(true);

        assertTrue(settingFamiliar.executeAction(player, gameBoard));

        assertTrue(familyMember.isPlayed());

        assertTrue(player.isFamiliarPositioned());
    }

}
