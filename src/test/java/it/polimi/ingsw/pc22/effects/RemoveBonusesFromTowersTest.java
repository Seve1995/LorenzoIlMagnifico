package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by matteo on 06/07/17.
 */
public class RemoveBonusesFromTowersTest extends TestCase {

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
        RemoveBonusesFromTowers removeBonusesFromTowers = new RemoveBonusesFromTowers();

        assertEquals(true, removeBonusesFromTowers.isLegal(player, gameBoard));
    }

    @Test
    public void testExecuteEffect()
    {
        RemoveBonusesFromTowers removeBonusesFromTowers = new RemoveBonusesFromTowers();

        when(player.isRemoveTowerBonus()).thenReturn(true);

        removeBonusesFromTowers.executeEffects(player, gameBoard);

        assertEquals(true, player.isRemoveTowerBonus());

        assertEquals(true, removeBonusesFromTowers.executeEffects(player, gameBoard));
    }
}
