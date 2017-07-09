package it.polimi.ingsw.pc22.effects;


import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.AssetType;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FromAssetToAssetTest extends TestCase{


    @Before
    public void setUp()
    {

    }

    @Test
    public void testGetSet()
    {
        FromAssetToAssetOrEffect fromAssetToAssetOrEffect = new FromAssetToAssetOrEffect();

        fromAssetToAssetOrEffect.setOnlyOneAsset(true);

        List<Asset> assets = new ArrayList<>();

        assets.add(new Asset(1, AssetType.COIN));

        fromAssetToAssetOrEffect.setGainedAssets(assets);

        fromAssetToAssetOrEffect.setPaiedAssets(assets);

        Effect effect = new PickTowerCard();

        fromAssetToAssetOrEffect.setGainedEffect(effect);

       /* assertEquals();

        assertEquals();

        assertEquals();

        assertEquals();
        */
    }

    @Test
    public void testIsLegal()
    {


    }

}
