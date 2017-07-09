package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.connection.GameMatch;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class will receive the decision of the player
 * in case he/she has to make a choice between a certain
 * number of asset 
 */

public class ChooseAsset
{
    private Integer chosenAssetsToPay = null;

    private static final Logger LOGGER = Logger.getLogger(ChooseAsset.class.getName());

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
                LOGGER.log(Level.WARNING, "Interrupted!", e);
                Thread.currentThread().interrupt();
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
