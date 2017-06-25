package it.polimi.ingsw.pc22.utils;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.BonusTile;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fandroid95 on 04/06/2017.
 */
public class BonusTileLoader extends GenericLoader
{
    public static List<BonusTile> loadBonusTiles
            (JSONObject jsonCardsObject)
    {
        List<BonusTile> cards = new ArrayList<>();

        JSONArray jsonTiles = jsonCardsObject.getJSONArray("bonusTiles");

        for(int i  = 0; i < jsonTiles.length(); i++)
        {
            JSONObject jsonTile = jsonTiles.getJSONObject(i);

            BonusTile card = loadBonusTile(jsonTile);

            cards.add(card);
        }

        return cards;
    }

    private static BonusTile loadBonusTile(JSONObject jsonCard)
    {
        int number = jsonCard.getInt("number");
        Asset productionServantBonus =
                loadAsset(jsonCard.getJSONObject("productionServantBonus"));

        Asset productionCoinsBonus =
                loadAsset(jsonCard.getJSONObject("productionCoinsBonus"));

        Asset productionMilitaryPointsBonus =
                loadAsset(jsonCard.getJSONObject("productionMilitaryPointsBonus"));

        Asset harvestServantBonus =
                loadAsset(jsonCard.getJSONObject("harvestServantBonus"));

        Asset harvestCoinsBonus =
                loadAsset(jsonCard.getJSONObject("harvestCoinsBonus"));

        Asset harvestStoneBonus =
                loadAsset(jsonCard.getJSONObject("harvestStoneBonus"));

        Asset harvestWoodBonus =
                loadAsset(jsonCard.getJSONObject("harvestWoodBonus"));

        Asset harvestMilitaryPointsBonus =
                loadAsset(jsonCard.getJSONObject("harvestMilitaryPointsBonus"));

        int productionActivationValue =
                jsonCard.getInt("productionActivationValue");

        int harvestActivationValue = jsonCard.getInt("harvestActivationValue");


        BonusTile tile = new BonusTile();

        tile.setProductionServantBonus(productionServantBonus);
        tile.setProductionCoinsBonus(productionCoinsBonus);
        tile.setProductionMilitaryPointsBonus(productionMilitaryPointsBonus);
        tile.setHarvestServantBonus(harvestServantBonus);
        tile.setHarvestCoinsBonus(harvestCoinsBonus);
        tile.setHarvestStonesBonus(harvestStoneBonus);
        tile.setHarvestWoodsBonus(harvestWoodBonus);
        tile.setHarvestMilitaryPointsBonus(harvestMilitaryPointsBonus);
        tile.setNumber(number);

        tile.setHarvestActivationValue(harvestActivationValue);
        tile.setProductionActivationValue(productionActivationValue);

        return tile;
    }
}
