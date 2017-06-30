package it.polimi.ingsw.pc22.messages;

import it.polimi.ingsw.pc22.gamebox.Asset;

import java.util.List;

/**
 * Created by fandroid95 on 27/06/2017.
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
