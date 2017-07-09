package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.effects.Effect;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matteo on 09/07/17.
 */
public class TerritoryCardTest extends TestCase {

    private TerritoryCard territoryCard;

    private List<Effect> effects;

    @Before
    public void setUp()
    {
        List<Effect> effects1 = new ArrayList<>();

        List<Effect> effects2 = new ArrayList<>();

        territoryCard = new TerritoryCard("test", 0, effects1, effects2, 0);
    }

    @Test
    public void testGetSet()
    {
        territoryCard.setPermanentEffectActivationCost(0);

        assertEquals(0, territoryCard.getPermanentEffectActivationCost());

        assertEquals(territoryCard, new TerritoryCard("test", 0, effects, effects, 0));
    }


}
