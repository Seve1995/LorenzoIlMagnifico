package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.AssetType;
import it.polimi.ingsw.pc22.player.Player;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class PickCouncilPrivilegeTest extends TestCase {

    private Player player;


    @Before
    public void setUp()
    {
        player = mock(Player.class);

        new GameMatch(2);
    }

    @Test
    public void testWaitForResult()
    {

        PickCouncilPrivilege pickCouncilPrivilege = new PickCouncilPrivilege();

        List<Asset> assets = new ArrayList<>();

        assets.add(new Asset(1, AssetType.COIN));

        pickCouncilPrivilege.setChosenAssets(assets);

        assertEquals(true, pickCouncilPrivilege.waitForResult(player));

        pickCouncilPrivilege.setChosenAssets(null);

        assertEquals(false, pickCouncilPrivilege.waitForResult(player));

    }




}
