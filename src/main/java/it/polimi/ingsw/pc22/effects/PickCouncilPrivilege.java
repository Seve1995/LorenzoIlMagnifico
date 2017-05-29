package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.AssetType;
import it.polimi.ingsw.pc22.player.Player;

public class PickCouncilPrivilege  implements   Effect
{
    @Override
    public boolean isLegal(Player player) {
        return false;
    }

    @Override
    public void executeEffect(Player player) {
    	
    	//choosen asset
    	Asset a = new Asset(5, AssetType.COIN);
    	
    	player.addAsset(a);

    }
}
