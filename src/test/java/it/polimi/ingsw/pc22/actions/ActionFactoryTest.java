package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.ColorsEnum;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by fandroid95 on 04/07/2017.
 */
public class ActionFactoryTest
{
    private Player player;

    private List<FamilyMember> memberList;

    @Before
    public void setUp()
    {
        player = mock(Player.class);

        FamilyMember familyMember = new FamilyMember();

        familyMember.setColor(ColorsEnum.BLACK);

        familyMember.setFamiliarValue(4);

        memberList = new ArrayList<>();

        memberList.add(familyMember);
    }

    @Test
    public void testNull()
    {
        String nullString = "null";

        assertNull(ActionFactory.createAction(nullString, player));
    }

    @Test
    public void testNewPassAction()
    {
        String passString = "pass";

        PassTurn pass = new PassTurn();

        assertEquals(ActionFactory.createAction(passString, player), pass);

        assertFalse(ActionFactory.createAction(passString, player).isFamiliarNeeded());

        assertNull(ActionFactory.createAction(passString, player).getFamilyMember());
    }

    @Test
    public void testNewActiveCardAction()
    {
        String passString = "activate card 0";

        ActiveLeaderCard activeLeaderCard = new ActiveLeaderCard();

        activeLeaderCard.setIndex(0);

        ActiveLeaderCard  action = (ActiveLeaderCard)
                ActionFactory.createAction(passString, player);

        assertEquals(action, activeLeaderCard);

        assertFalse(action.isFamiliarNeeded());

        assertNull(action.getFamilyMember());
    }

    @Test
    public void testNewDiscardCardAction()
    {
        String passString = "discard card 0";

        DiscardLeaderCard discardLeaderCard = new DiscardLeaderCard();

        discardLeaderCard.setIndex(0);

        DiscardLeaderCard  action = (DiscardLeaderCard)
                ActionFactory.createAction(passString, player);

        assertEquals(action, discardLeaderCard);

        assertFalse(action.isFamiliarNeeded());

        assertNull(action.getFamilyMember());
    }

    @Test
    public void testNewPlayCardAction()
    {
        String passString = "play card 0";

        PlayLeaderCard playLeaderCard = new PlayLeaderCard();

        playLeaderCard.setIndex(0);

        PlayLeaderCard  action = (PlayLeaderCard)
                ActionFactory.createAction(passString, player);

        assertEquals(action, playLeaderCard);

        assertFalse(action.isFamiliarNeeded());

        assertNull(action.getFamilyMember());
    }

    @Test
    public void testNewCouncilAction()
    {
        String string = "set council BLACK 0";

        Action action = ActionFactory.createAction(string, player);

        assertNull(action);

        when(player.getUnusedFamilyMemberByColor(ColorsEnum.BLACK))
                .thenReturn(memberList.get(0));

        action = ActionFactory.createAction(string, player);

        assertTrue(action instanceof SettingFamiliarMemberOnCouncilPalace);

        FamilyMember familyMember = action.getFamilyMember();

        assertEquals(familyMember, memberList.get(0));
    }

    @Test
    public void testNewTowerAction()
    {
        String string = "set tower BLACK 0 TERRITORY 0";

        Action action = ActionFactory.createAction(string, player);

        assertNull(action);

        when(player.getUnusedFamilyMemberByColor(ColorsEnum.BLACK))
                .thenReturn(memberList.get(0));

        action = ActionFactory.createAction(string, player);

        assertTrue(action instanceof SettingFamiliarMemberOnTower);

        FamilyMember familyMember = action.getFamilyMember();

        assertEquals(familyMember, memberList.get(0));
    }


    @Test
    public void testNewSettingMarketAction()
    {
        String string = "set market BLACK 0 0";

        Action action = ActionFactory.createAction(string, player);

        assertNull(action);

        when(player.getUnusedFamilyMemberByColor(ColorsEnum.BLACK))
                .thenReturn(memberList.get(0));

        action = ActionFactory.createAction(string, player);

        assertTrue(action instanceof SettingFamiliarMemberOnMarket);

        FamilyMember familyMember = action.getFamilyMember();

        assertEquals(familyMember, memberList.get(0));
    }

    @Test
    public void testNewHarvestAction()
    {
        String string = "set harvest BLACK 0";

        Action action = ActionFactory.createAction(string, player);

        assertNull(action);

        when(player.getUnusedFamilyMemberByColor(ColorsEnum.BLACK))
                .thenReturn(memberList.get(0));

        action = ActionFactory.createAction(string, player);

        assertTrue(action instanceof SettingFamiliarMemberOnHarvest);

        FamilyMember familyMember = action.getFamilyMember();

        assertEquals(familyMember, memberList.get(0));

        when(player.getServants()).thenReturn(0);

        String stringServants = "set harvest BLACK 1";

        action = ActionFactory.createAction(stringServants, player);

        assertTrue(action instanceof SettingFamiliarMemberOnHarvest);

        when(player.getServants()).thenReturn(3);

        action = ActionFactory.createAction(stringServants, player);

        assertTrue(action instanceof ServantsHandler);
    }

    @Test
    public void testNewProductionAction()
    {
        String string = "set production BLACK 0";

        Action action = ActionFactory.createAction(string, player);

        assertNull(action);

        when(player.getUnusedFamilyMemberByColor(ColorsEnum.BLACK))
                .thenReturn(memberList.get(0));

        action = ActionFactory.createAction(string, player);

        assertTrue(action instanceof SettingFamiliarMemberOnProduction);

        FamilyMember familyMember = action.getFamilyMember();

        assertEquals(familyMember, memberList.get(0));
    }

}
