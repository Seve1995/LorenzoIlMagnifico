package it.polimi.ingsw.pc22.messages;

import it.polimi.ingsw.pc22.gamebox.Asset;

import java.util.List;

/**
 * Created by fandroid95 on 27/06/2017.
 */
public class ChooseAssetsMessage extends Message
{
    private List<Asset> assets;
    private int numberOfAssets;

    public ChooseAssetsMessage(List<Asset> assets, int numberOfAssets)
    {
        this.assets = assets;
        this.numberOfAssets = numberOfAssets;
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public int getNumberOfAssets() {
        return numberOfAssets;
    }
}
