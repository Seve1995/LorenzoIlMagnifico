package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.messages.GameStatusMessage;
import it.polimi.ingsw.pc22.player.Player;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is used to model the choice of the bonus given by council privilege.
 */
public class PickCouncilPrivilege
{
    private List<Asset> chosenAssets = null;

    private static final Logger LOGGER = Logger.getLogger(ChooseAsset.class.getName());

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
                LOGGER.log(Level.WARNING, "Interrupted!", e);
                Thread.currentThread().interrupt();
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
