package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.AssetType;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by fandroid95 on 04/07/2017.
 */
public class ServantsHandlerTest
{
    private Player player;
    private GameBoard gameBoard;

    private Asset servants;

    private Action action;

    @Before
    public void setUp()
    {
        player = mock(Player.class);

        gameBoard = mock(GameBoard.class);

        action = mock(Action.class);

        servants = new Asset(2, AssetType.SERVANT);
    }

    @Test
    public void testIsLegal()
    {
        ServantsHandler handler = new ServantsHandler(action, servants);

        when(player.getServants()).thenReturn(1);

        assertFalse(handler.isLegal(player, gameBoard));

        when(player.getServants()).thenReturn(3);

        assertTrue(handler.isLegal(player, gameBoard));
    }

    @Test
    public void tessExecuteFalse()
    {
        ServantsHandler handler = new ServantsHandler(action, servants);

        when(player.getServants()).thenReturn(1);

        assertFalse(handler.executeAction(player, gameBoard));
    }

    @Test
    public void tessExecuteTrue()
    {
        ServantsHandler handler = new ServantsHandler(action, servants);

        Player player = new Player("Test", "TEst", false);

        player.setServants(3);

        player.setServantMalus(false);

        FamilyMember familyMember = new FamilyMember();

        familyMember.setFamiliarValue(2);

        when(action.getFamilyMember()).thenReturn(familyMember);

        when(action.executeAction(player, gameBoard)).thenReturn(true);

        assertTrue(handler.executeAction(player, gameBoard));

        assertEquals(player.getServants(), (int) (3 - 2 * 0.5 * 2));

        assertEquals(action.getFamilyMember().getFamiliarValue(), (int) (2 + 2/(2*0.5)));
    }

    @Test
    public void tessExecuteTrueMalus()
    {
        ServantsHandler handler = new ServantsHandler(action, servants);

        Player player = new Player("Test", "TEst", false);

        player.setServants(4);

        player.setServantMalus(true);

        FamilyMember familyMember = new FamilyMember();

        familyMember.setFamiliarValue(2);

        when(action.getFamilyMember()).thenReturn(familyMember);

        when(action.executeAction(player, gameBoard)).thenReturn(true);

        assertTrue(handler.executeAction(player, gameBoard));

        assertEquals(player.getServants(), 4 - 2 * 1 * 2);

        assertEquals(action.getFamilyMember().getFamiliarValue(), 2 + 2/(2*1));
    }

    @Test
    public void tessExecuteActionNotExecuted()
    {
        ServantsHandler handler = new ServantsHandler(action, servants);

        Player player = new Player("Test", "TEst", false);

        player.setServants(3);

        player.setServantMalus(false);

        FamilyMember familyMember = new FamilyMember();

        familyMember.setFamiliarValue(2);

        when(action.getFamilyMember()).thenReturn(familyMember);

        when(action.executeAction(player, gameBoard)).thenReturn(false);

        assertFalse(handler.executeAction(player, gameBoard));

        assertEquals(player.getServants(), 3);

        assertEquals(action.getFamilyMember().getFamiliarValue(), (int) (2 + 2/(2*0.5)));
    }
}
