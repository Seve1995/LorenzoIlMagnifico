package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.adapters.SocketIOAdapter;
import it.polimi.ingsw.pc22.gamebox.ColorsEnum;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by matteo on 05/07/17.
 */
public class FamilyMemberModifierTest extends TestCase {

    private FamilyMember familyMember;

    private GameBoard gameBoard;

    private Player player;


    @Before
    public void setUp()
    {
        familyMember = new FamilyMember();

        gameBoard = new GameBoard();

        player = new Player(null, null, false);

        familyMember = mock(FamilyMember.class);
    }

    @Test
    public void testIsLegal()
    {
        FamilyMemberModifier familyMemberModifier = new FamilyMemberModifier();

        when(familyMember.getColor()).thenReturn(ColorsEnum.NEUTER);

        familyMemberModifier.setFamilyMemberColor(familyMember.getColor());

        assertEquals(false, familyMemberModifier.isLegal(player, gameBoard));

        when(familyMember.getColor()).thenReturn(ColorsEnum.BLACK);

        familyMemberModifier.setFamilyMemberColor(familyMember.getColor());

        assertEquals(true, familyMemberModifier.isLegal(player, gameBoard));

    }

    @Test
    public void testExecuteEffectFirstBranch()
    {
        FamilyMemberModifier familyMemberModifier = new FamilyMemberModifier();

        familyMemberModifier.setToColoured(false);

        familyMemberModifier.setABonus(true);

        familyMemberModifier.setDiceValueBonus(6);

        familyMemberModifier.setFamilyMemberColor(ColorsEnum.BLACK);

        player.getFamilyMembers().add(new FamilyMember());

        player.getFamilyMembers().get(0).setColor(ColorsEnum.NEUTER);

        familyMemberModifier.executeEffects(player, gameBoard);

        assertEquals(6, player.getFamilyMembers().get(0).getValueModifier());


    }


    @Test
    public void testExecuteEffectSecondBranch() throws IOException {

        FamilyMemberModifier familyMemberModifier = new FamilyMemberModifier();

        familyMemberModifier.setToColoured(true);

        familyMemberModifier.setABonus(false);

        familyMemberModifier.setToAll(true);

        familyMemberModifier.setFamilyMemberColor(ColorsEnum.BLACK);

        familyMemberModifier.setDiceValueSet(6);

        player.getFamilyMembers().add(new FamilyMember());

        player.getFamilyMembers().get(0).setColor(ColorsEnum.BLACK);

        familyMemberModifier.executeEffects(player, gameBoard);

        assertEquals(6, player.getFamilyMembers().get(0).getValue());

        familyMemberModifier.setABonus(true);

        familyMemberModifier.setDiceValueBonus(6);

        familyMemberModifier.executeEffects(player, gameBoard);

        assertEquals(6, player.getFamilyMembers().get(0).getValueModifier());

        IOAdapter adapter = new SocketIOAdapter();



    }






}
