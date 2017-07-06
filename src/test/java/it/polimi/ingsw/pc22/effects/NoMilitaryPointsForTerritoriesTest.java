package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by matteo on 05/07/17.
 */
public class NoMilitaryPointsForTerritoriesTest extends TestCase {

    private Player player;
    private GameBoard gameBoard;

    @Before
    public void setUp()
    {
        player = new Player(null, null, false);
    }

    @Test
    public void testExecuteEffect()
    {
        NoMilitaryPointsForTerritories noMilitaryPointsForTerritories = new NoMilitaryPointsForTerritories();
        assertEquals(true, noMilitaryPointsForTerritories.executeEffects(player, gameBoard));
        assertEquals(true, player.isNoMilitaryPointsForTerritories());
    }

    @Test
    public void testIsLegal()
    {
        NoMilitaryPointsForTerritories noMilitaryPointsForTerritories = new NoMilitaryPointsForTerritories();
        assertEquals(true, noMilitaryPointsForTerritories.isLegal(player, gameBoard));

    }
}
