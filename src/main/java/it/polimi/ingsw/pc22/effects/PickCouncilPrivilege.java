package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

/**
 * Created by fandroid95 on 29/06/2017.
 */
public class PickCouncilPrivilege
{
    private List<Asset> chosenAssets = null;

    public boolean waitForResult(Player player)
    {
        Long timestamp = System.currentTimeMillis();

        Long timeout = GameMatch.getTimeout();

        while (System.currentTimeMillis() < timestamp + timeout)
        {
            try
            {
                Thread.sleep(100L);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            if (chosenAssets != null)
            {
                for (Asset asset : chosenAssets)
                    player.addAsset(asset);

                return true;
            }
        }

        return false;
    }

    public void setChosenAssets(List<Asset> chosenAssets)
    {
        this.chosenAssets = chosenAssets;
    }
}
