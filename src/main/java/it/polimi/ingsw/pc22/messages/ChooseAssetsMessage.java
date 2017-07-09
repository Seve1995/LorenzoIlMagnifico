package it.polimi.ingsw.pc22.messages;

import it.polimi.ingsw.pc22.gamebox.Asset;

import java.util.List;

/**
 *
 * This message provides the player a list of resources
 * he can choose from.
 *
 */
public class ChooseAssetsMessage extends Message
{
    private List<Asset> assets;

    public ChooseAssetsMessage(List<Asset> assets)
    {
        this.assets = assets;
    }

    public List<Asset> getAssets() {
        return assets;
    }
}
