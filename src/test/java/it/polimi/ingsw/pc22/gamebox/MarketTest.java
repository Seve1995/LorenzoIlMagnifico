package it.polimi.ingsw.pc22.gamebox;


import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class MarketTest extends TestCase{

    private Market market;

    @Before
    public void setUp()
    {
        market = new Market(null);
    }

    @Test
    public void testGetSet()
    {
        assertNull(market.getMarketCells());

    }



}
