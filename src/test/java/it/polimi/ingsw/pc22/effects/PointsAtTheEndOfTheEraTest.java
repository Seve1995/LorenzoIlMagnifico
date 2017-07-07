package it.polimi.ingsw.pc22.effects;


import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PointsAtTheEndOfTheEraTest extends TestCase {

    private Player player;

    private GameBoard gameBoard;

    @Before
    public void setUp()
    {
        player = mock(Player.class);
    }

    @Test
    public void testIsLegal()
    {
        PointsAtTheEndOfTheEra pointsAtTheEndOfTheEra = new PointsAtTheEndOfTheEra();

        assertEquals(true, pointsAtTheEndOfTheEra.isLegal(player, gameBoard));
    }

    @Test
    public void testExecuteEffect()
    {
        PointsAtTheEndOfTheEra pointsAtTheEndOfTheEra = new PointsAtTheEndOfTheEra();

        when(player.isSistoIV()).thenReturn(true);

        pointsAtTheEndOfTheEra.executeEffects(player, gameBoard);

        assertEquals(true, player.isSistoIV());

        assertEquals(true, pointsAtTheEndOfTheEra.executeEffects(player, gameBoard));
    }

}
