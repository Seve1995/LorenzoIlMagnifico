package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

/**
 * Created by fandroid95 on 30/06/2017.
 */
public class ChooseAsset
{
    private Integer chosenAssetsToPay = null;

    public boolean waitForResult()
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

            if (chosenAssetsToPay != null)
            {
                return true;
            }
        }

        return false;
    }

    public void setChosenAssetsToPay(Integer chosenAssetsToPay)
    {
        this.chosenAssetsToPay = chosenAssetsToPay;
    }

    public Integer getChosenAssetsToPay() {
        return chosenAssetsToPay;
    }
}
