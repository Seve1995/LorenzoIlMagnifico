package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.effects.PickOneCouncilPrivilege;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.gamebox.LeaderCard;
import it.polimi.ingsw.pc22.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

;

/**
 * Created by fandroid95 on 04/07/2017.
 */
public class DiscardLeaderCardTest
{
    private GameBoard gameBoard;

    private List<LeaderCard> cards;

    private LeaderCard card;

    private Player player;

    @Before
    public void setUp()
    {
        gameBoard = new GameBoard();

        player = mock(Player.class);

        cards = new ArrayList<>();

        card = new LeaderCard();

        cards.add(card);

        card.setEffects(new ArrayList<>());
    }

    @Test
    public void isLegalVoid()
    {
        DiscardLeaderCard discardLeaderCard = new DiscardLeaderCard();
        discardLeaderCard.setIndex(0);

        when(player.getLeaderCards()).thenReturn(new ArrayList<>());

        assertFalse(discardLeaderCard.isLegal(player, gameBoard));
    }

    @Test
    public void isLegalSizeWrong()
    {
        DiscardLeaderCard discardLeaderCard = new DiscardLeaderCard();
        discardLeaderCard.setIndex(1);

        when(player.getLeaderCards()).thenReturn(cards);

        assertFalse(discardLeaderCard.isLegal(player, gameBoard));
    }

    @Test
    public void isLegal()
    {
        DiscardLeaderCard discardLeaderCard = new DiscardLeaderCard();
        discardLeaderCard.setIndex(2);

        when(player.getLeaderCards()).thenReturn(cards);

        assertTrue(discardLeaderCard.isLegal(player, gameBoard));
    }

    @Test
    public void execute()
    {
        DiscardLeaderCard discardLeaderCard = new DiscardLeaderCard();
        discardLeaderCard.setIndex(0);

        PickOneCouncilPrivilege pick = mock(PickOneCouncilPrivilege.class);

        discardLeaderCard.setPickPrivilege(pick);

        when(player.getLeaderCards()).thenReturn(cards);

        when(pick.executeEffects(player, gameBoard)).thenReturn(true);

        discardLeaderCard.executeAction(player, gameBoard);

        assertNull(cards.get(0));

        //assertTrue(discardLeaderCard.executeAction(player, gameBoard));
    }
}
