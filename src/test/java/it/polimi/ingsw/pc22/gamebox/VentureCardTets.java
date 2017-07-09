package it.polimi.ingsw.pc22.gamebox;


import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class VentureCardTets extends TestCase {

    private VentureCard ventureCard;

    @Before
    public void setUp()
    {
        ventureCard = new VentureCard(null, 0, null, null, 0);
    }

    @Test()
    public void testGetSet()
    {
        ventureCard.setRequiredCostChoice(true);
        Asset asset = new Asset(1, AssetType.MILITARYPOINT);
        Asset asset1 = new Asset(2, AssetType.MILITARYPOINT);
        List<Asset> assets = new ArrayList<>();
        assets.add(asset);
        ventureCard.setMilitaryPointsCost(asset);
        ventureCard.setResourcesCost(assets);
        ventureCard.setMilitaryPointsRequired(asset1);

        assertEquals(asset, ventureCard.getMilitaryPointsCost());
        assertEquals(asset1, ventureCard.getMilitaryPointsRequired());
        assertEquals(assets, ventureCard.getResourcesCost());
        assertTrue(ventureCard.isRequiredCostChoice());

    }
}
