package it.polimi.ingsw.pc22.utils;

import it.polimi.ingsw.pc22.effects.AddAsset;
import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.AssetType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fandroid95 on 31/05/2017.
 */
public class GenericLoader
{
    private static String EFFECT_PATH = "it.polimi.ingsw.pc22.effects.";

    protected static List<Effect> loadEffectList(JSONArray jsonEffects)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        List<Effect> effects = new ArrayList<>();

        for(int i = 0; i < jsonEffects.length(); i++)
        {
            JSONObject jsonEffect = jsonEffects.getJSONObject(i);

            String name = jsonEffect.getString("name");

            String className = EFFECT_PATH + name;

            Class effectClass = Class.forName(className);

            Effect effect = (Effect) effectClass.newInstance();

            if (effect == null) continue;

            effects.add(effect);

            if (jsonEffect.isNull("asset")) continue;

            JSONObject jsonAsset = jsonEffect.getJSONObject("asset");

            addAssetToGenericEffect(jsonAsset, effect);
        }

        return effects;
    }

    private static void addAssetToGenericEffect(JSONObject jsonAsset, Effect effect)
    {
        if (effect instanceof AddAsset)
        {
            Asset asset = loadAsset(jsonAsset);

            ((AddAsset) effect).setAsset(asset);
        }
    }

    protected static Asset loadAsset(JSONObject jsonAsset) throws JSONException
    {
        String type = jsonAsset.getString("type");

        int value = jsonAsset.getInt("value");

        AssetType assetType = AssetType.valueOf(type);

        return new Asset(value, assetType);
    }

    protected static List<Asset> loadAssetList(JSONArray jsonAssets)
            throws JSONException
    {
        List<Asset> assets = new ArrayList<>();

        for (int i = 0; i < jsonAssets.length(); i++)
        {
            JSONObject jsonAsset = jsonAssets.getJSONObject(i);

            Asset asset = loadAsset(jsonAsset);

            assets.add(asset);
        }

        return assets;
    }
}
