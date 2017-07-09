package it.polimi.ingsw.pc22.effects;


import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.AssetType;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FromAssetToAssetTest extends TestCase{

    private Player player;

    private GameBoard gameboard;

    private GameMatch gameMatch;

    @Before
    public void setUp()
    {

      player = new Player(null, null, false);

      player.setCoins(0);

      gameboard = new GameBoard();

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

        assertEquals(effect, fromAssetToAssetOrEffect.getGainedEffect());

        assertEquals(assets, fromAssetToAssetOrEffect.getGainedAssets());

        assertEquals(assets, fromAssetToAssetOrEffect.getPaiedAssets());

        assertEquals(true, fromAssetToAssetOrEffect.isOnlyOneAsset());

    }

    @Test
    public void testIsLegal()
    {

        FromAssetToAssetOrEffect fromAssetToAssetOrEffect = new FromAssetToAssetOrEffect();

        fromAssetToAssetOrEffect.setChosenAssetsToPay(null);

        fromAssetToAssetOrEffect.setPaiedAssets(new ArrayList<>());

        assertTrue(fromAssetToAssetOrEffect.isLegal(player, gameboard));

        fromAssetToAssetOrEffect.setChosenAssetsToPay(0);

        List<Asset> assets = new ArrayList<>();

        assets.add(new Asset(1, AssetType.COIN));

        fromAssetToAssetOrEffect.setPaiedAssets(assets);

        assertFalse(fromAssetToAssetOrEffect.isLegal(player, gameboard));

        player.setCoins(5);

        assertTrue(fromAssetToAssetOrEffect.isLegal(player, gameboard));

        fromAssetToAssetOrEffect.setChosenAssetsToPay(null);

        player.setCoins(0);

        assertFalse(fromAssetToAssetOrEffect.isLegal(player, gameboard));

    }

}
