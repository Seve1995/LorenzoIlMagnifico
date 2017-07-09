package it.polimi.ingsw.pc22.messages;

import it.polimi.ingsw.pc22.gamebox.Asset;

import java.util.List;

/**
 * Please refer to "chooseAssetMessage"; instead of resources as bonus
 * here we have resources as costs.
 */
public class ChooseCostsMessage extends Message
{
    private Asset militaryPointsRequired;
    private Asset militaryPointsCost;
    private List<Asset> resourcesCost;

    public ChooseCostsMessage
        (Asset militaryPointsRequired, Asset militaryPointsCost, List<Asset> resourcesCost)
    {
        this.militaryPointsRequired = militaryPointsRequired;
        this.militaryPointsCost = militaryPointsCost;
        this.resourcesCost = resourcesCost;
    }

    public Asset getMilitaryPointsRequired() {
        return militaryPointsRequired;
    }

    public Asset getMilitaryPointsCost() {
        return militaryPointsCost;
    }

    public List<Asset> getResourcesCost() {
        return resourcesCost;
    }
}
