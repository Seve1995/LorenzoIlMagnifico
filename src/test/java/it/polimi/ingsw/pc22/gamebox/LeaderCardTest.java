package it.polimi.ingsw.pc22.gamebox;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;


public class LeaderCardTest extends TestCase {

    private LeaderCard leaderCard = new LeaderCard();

    @Before
    public void setUp()
    {
        leaderCard.setName("test");
        leaderCard.setNumber(0);
        leaderCard.setFaceUp(true);
        leaderCard.setPlayed(true);
        leaderCard.setEffects(null);
        leaderCard.setRequiredAssets(null);
        leaderCard.setRequiredCards(null);
    }

    @Test
    public void testGetSet()
    {
        assertEquals("test", leaderCard.getName());
        assertEquals(0, leaderCard.getNumber());
        assertEquals(true, leaderCard.isPlayed());
        assertEquals(true, leaderCard.isFaceUp());
        assertNull(leaderCard.getEffects());
        assertNull(leaderCard.getRequiredAssets());
        assertNull(leaderCard.getRequiredCards());

    }
}
