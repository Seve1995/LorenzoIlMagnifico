package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.gamebox.LeaderCard;
import it.polimi.ingsw.pc22.gamebox.PlayerBoard;
import it.polimi.ingsw.pc22.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by fandroid95 on 04/07/2017.
 */
public class ActiveLeaderCardTest
{
    private GameBoard gameBoard;
    private PlayerBoard playerBoard;

    private List<LeaderCard> cards;

    private LeaderCard card;

    private Player player;

    @Before
    public void setUp()
    {
        gameBoard = mock(GameBoard.class);

        player = mock(Player.class);

        playerBoard = mock(PlayerBoard.class);

        cards = new ArrayList<>();

        card = new LeaderCard();

        cards.add(card);

        card.setEffects(new ArrayList<>());
    }

    @Test
    public void isLegalNull()
    {
        ActiveLeaderCard activeLeaderCard = new ActiveLeaderCard();
        activeLeaderCard.setIndex(0);

        when(player.getPlayerBoard()).thenReturn(playerBoard);

        when(playerBoard.getLeaderCards()).thenReturn(null);

        assertFalse(activeLeaderCard.isLegal(player, gameBoard));
    }

    @Test
    public void isLegalNotFaceUp()
    {
        ActiveLeaderCard activeLeaderCard = new ActiveLeaderCard();
        activeLeaderCard.setIndex(0);

        when(player.getPlayerBoard()).thenReturn(playerBoard);

        when(playerBoard.getLeaderCards()).thenReturn(cards);

        card.setFaceUp(false);

        assertFalse(activeLeaderCard.isLegal(player, gameBoard));
    }

    @Test
    public void isLegal()
    {
        ActiveLeaderCard activeLeaderCard = new ActiveLeaderCard();
        activeLeaderCard.setIndex(0);

        when(player.getPlayerBoard()).thenReturn(playerBoard);

        when(playerBoard.getLeaderCards()).thenReturn(cards);

        card.setFaceUp(true);

        assertTrue(activeLeaderCard.isLegal(player, gameBoard));
    }

    @Test
    public void execute()
    {
        ActiveLeaderCard activeLeaderCard = new ActiveLeaderCard();
        activeLeaderCard.setIndex(0);

        when(player.getPlayerBoard()).thenReturn(playerBoard);

        when(playerBoard.getLeaderCards()).thenReturn(cards);

        assertTrue(activeLeaderCard.executeAction(player, gameBoard));

        assertFalse(card.isFaceUp());
    }

    @Test
    public void equals()
    {
        ActiveLeaderCard activeLeaderCard = new ActiveLeaderCard();

        activeLeaderCard.setIndex(0);

        ActiveLeaderCard activeLeaderCard1 = new ActiveLeaderCard();

        activeLeaderCard1.setIndex(0);

        assertEquals(activeLeaderCard, activeLeaderCard1);

        assertEquals(activeLeaderCard, activeLeaderCard);

        assertFalse(activeLeaderCard.equals(new Integer(1)));

        assertFalse(activeLeaderCard.equals(null));
    }
}
