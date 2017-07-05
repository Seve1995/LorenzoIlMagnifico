package it.polimi.ingsw.pc22.gamebox;

import junit.framework.TestCase;
import org.junit.Test;


public class PlayerColorTest extends TestCase {

    @Test
    public void testGetColorByValueStandard()
    {
        int value = 0;

        assertEquals(PlayerColorsEnum.RED, PlayerColorsEnum.getColorByValue(value));
    }

    @Test
    public void testGetColorByValueNull()
    {
        int value = 100;

        assertEquals(null, PlayerColorsEnum.getColorByValue(value));
    }

}
