package it.polimi.ingsw.pc22.utils;

import it.polimi.ingsw.pc22.effects.*;
import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.AssetType;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic loader is used to load some attributes that
 * are common along side the match, such as effects,
 * assets, or required cards.
 */

public class GenericLoader
{
    private static String EFFECT_PATH = "it.polimi.ingsw.pc22.effects.";

    protected static Effect loadEffect(JSONObject jsonEffect)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        String name = jsonEffect.getString("name");

        String className = EFFECT_PATH + name;

        Class effectClass = Class.forName(className);

        Effect effect = (Effect) effectClass.newInstance();

        if (effect == null) return null;

        loadEffectsParameters(jsonEffect, effect);

        return effect;
    }


    protected static List<Effect> loadEffectList(JSONArray jsonEffects)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        List<Effect> effects = new ArrayList<>();

        for(int i = 0; i < jsonEffects.length(); i++)
        {
            JSONObject jsonEffect = jsonEffects.getJSONObject(i);

            Effect effect = loadEffect(jsonEffect);

            effects.add(effect);
        }

        return effects;
    }

    private static void loadEffectsParameters(JSONObject jsonEffect, Effect effect)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        if (effect instanceof AddAsset)
        {
            AddAsset addAsset = (((AddAsset) effect));

            JSONObject jsonAsset = jsonEffect.getJSONObject("asset");

            Asset asset = loadAsset(jsonAsset);

            addAsset.setAsset(asset);
        }

        if (effect instanceof AddAssetForEveryAssetOrCard)
        {
            AddAssetForEveryAssetOrCard addAssetForEveryAssetOrCard =
                    ((AddAssetForEveryAssetOrCard) effect);

            JSONObject gainedAsset = jsonEffect.getJSONObject("gainedAsset");

            Asset asset = loadAsset(gainedAsset);

            addAssetForEveryAssetOrCard.setGainedAsset(asset);

            if (!jsonEffect.isNull("paidCardType"))
            {
                String paidCardTypeValue = jsonEffect.getString("paidCardType");

                CardTypeEnum type = CardTypeEnum.valueOf(paidCardTypeValue);

                addAssetForEveryAssetOrCard.setPaidCardType(type);
            }

            if (jsonEffect.isNull("paidAsset")) return;

            JSONObject payedAssetJson = jsonEffect.getJSONObject("paidAsset");

            Asset payedAsset = loadAsset(payedAssetJson);

            addAssetForEveryAssetOrCard.setPaidAsset(payedAsset);
        }

        if (effect instanceof FromAssetToAssetOrEffect)
        {
            FromAssetToAssetOrEffect fromAssetToAssetOrEffect =
                    ((FromAssetToAssetOrEffect) effect);

            JSONArray paidAssetsJson = jsonEffect.getJSONArray("paidAssets");

            List<Asset> paidAssets = loadAssetList(paidAssetsJson);

            fromAssetToAssetOrEffect.setPaiedAssets(paidAssets);

            if (!jsonEffect.isNull("gainedAssets"))
            {
                JSONArray gainedAssetsJson = jsonEffect.getJSONArray("gainedAssets");

                List<Asset> gainAssets = loadAssetList(gainedAssetsJson);

                fromAssetToAssetOrEffect.setGainedAssets(gainAssets);
            }

            boolean onlyOneAsset = jsonEffect.getBoolean("onlyOneAsset");

            fromAssetToAssetOrEffect.setOnlyOneAsset(onlyOneAsset);

            if (jsonEffect.isNull("gainedEffect")) return;

            Effect gainedEffect = loadEffect(jsonEffect.getJSONObject("gainedEffect"));

            fromAssetToAssetOrEffect.setGainedEffect(gainedEffect);
        }

        if (effect instanceof AddEndGameVictoryPoints)
        {
            JSONObject victoryPoints = jsonEffect.getJSONObject("asset");

            Asset asset = loadAsset(victoryPoints);

            ((AddEndGameVictoryPoints) effect).setAsset(asset);
        }

        if (effect instanceof DoProductionAction)
        {
            int value = jsonEffect.getInt("value");

            ((DoProductionAction) effect).setValue(value);
        }

        if (effect instanceof DoHarvestAction)
        {
            int value = jsonEffect.getInt("value");

            ((DoHarvestAction) effect).setValue(value);
        }

        if (effect instanceof PickTowerCard)
        {
            PickTowerCard pickTowerCard = ((PickTowerCard) effect);

            String cardTypeValue = jsonEffect.getString("cardType");

            CardTypeEnum type = CardTypeEnum.valueOf(cardTypeValue);

            pickTowerCard.setCardType(type);

            int diceValue = jsonEffect.getInt("diceValue");

            pickTowerCard.setDiceValue(diceValue);

            int floor = jsonEffect.getInt("floor");

            pickTowerCard.setFloor(floor);

            if (jsonEffect.isNull("cardCostDiscount")) return;

            JSONArray cardCostDiscountJson =
                    jsonEffect.getJSONArray("cardCostDiscount");

            List<Asset> discounts = loadAssetList(cardCostDiscountJson);

            pickTowerCard.setAssetsDiscount(discounts);
        }

        if (effect instanceof AddTowerCardDiscount)
        {
            AddTowerCardDiscount addTowerCardDiscount = ((AddTowerCardDiscount) effect);

            String cardTypeValue = jsonEffect.getString("cardType");

            CardTypeEnum type = CardTypeEnum.valueOf(cardTypeValue);

            addTowerCardDiscount.setCardType(type);

            boolean onlyOneAsset = jsonEffect.getBoolean("onlyOneAsset");

            addTowerCardDiscount.setOnlyOneAsset(onlyOneAsset);

            int diceValueDiscount = jsonEffect.getInt("diceValueDiscount");

            addTowerCardDiscount.setDiceValueDiscount(diceValueDiscount);

            if (jsonEffect.isNull("assetDiscounts")) return;

            List<Asset> assets =
                    loadAssetList(jsonEffect.getJSONArray("assetDiscounts"));

            addTowerCardDiscount.setDiscounts(assets);
        }

        if (effect instanceof AddHarvestValueModifier)
        {
            int value = jsonEffect.getInt("value");

            ((AddHarvestValueModifier) effect).setValue(value);
        }

        if (effect instanceof AddProductionValueModifier)
        {
            int value = jsonEffect.getInt("value");

            ((AddProductionValueModifier) effect).setValue(value);
        }

        if (effect instanceof AddAssetMalus)
        {
            JSONObject assetJson = jsonEffect.getJSONObject("asset");

            Asset asset = loadAsset(assetJson);

            ((AddAssetMalus) effect).setAsset(asset);
        }

        if (effect instanceof AddHarvestValueModifier)
        {
            int value = jsonEffect.getInt("value");

            ((AddHarvestValueModifier) effect).setValue(value);
        }

        if (effect instanceof  AddProductionValueModifier)
        {
            int value = jsonEffect.getInt("value");

            ((AddProductionValueModifier) effect).setValue(value);
        }

        if (effect instanceof AddFamilyMembersMalus)
        {
            int value = jsonEffect.getInt("value");

            ((AddFamilyMembersMalus) effect).setDiceValueMalus(value);
        }

        if (effect instanceof AddCardPickMalus)
        {
            AddCardPickMalus addCardPickMalus = ((AddCardPickMalus) effect);

            int value = jsonEffect.getInt("value");

            String cardTypeValue = jsonEffect.getString("cardType");

            CardTypeEnum type = CardTypeEnum.valueOf(cardTypeValue);

            addCardPickMalus.setMalusValue(value);

            addCardPickMalus.setCardType(type);
        }

        if (effect instanceof EndGameMalus)
        {
            EndGameMalus endGameMalus = ((EndGameMalus) effect);

            boolean loseOneVictoryPointEveryFiveVictoryPoints =
                    jsonEffect.getBoolean("loseOneVictoryPointEveryFiveVictoryPoints");

            endGameMalus.setLoseOneVictoryPointEveryFiveVictoryPoints
                    (loseOneVictoryPointEveryFiveVictoryPoints);

            boolean buildingCardsMalus = jsonEffect.getBoolean("buildingCardsMalus");

            endGameMalus.setBuildingCardsMalus(buildingCardsMalus);

            if (!jsonEffect.isNull("noCardVictoryPoint"))
            {
                String cardTypeValue = jsonEffect.getString("noCardVictoryPoint");

                CardTypeEnum type = CardTypeEnum.valueOf(cardTypeValue);

                endGameMalus.setNoCardVictoryPoint(type);
            }
                        
            if (!jsonEffect.isNull("assetsMalus"))
            {
                List<Asset> assets =
                        loadAssetList(jsonEffect.getJSONArray("assetsMalus"));

                endGameMalus.setAssetsMalus(assets);
            }
            
        }

        if (effect instanceof  FamilyMemberModifier)
        {
            FamilyMemberModifier familyMemberModifier = ((FamilyMemberModifier) effect);

            int diceValueBonus = jsonEffect.getInt("diceValueBonus");

            int diceValueSet = jsonEffect.getInt("diceValueSet");

            boolean isColoured = jsonEffect.getBoolean("toColoured");

            boolean toAll = jsonEffect.getBoolean("toAll");

            boolean isABonus = jsonEffect.getBoolean("isABonus");

            familyMemberModifier.setDiceValueBonus(diceValueBonus);

            familyMemberModifier.setDiceValueSet(diceValueSet);

            familyMemberModifier.setABonus(isABonus);

            familyMemberModifier.setToAll(toAll);

            familyMemberModifier.setToColoured(isColoured);

        }

        if (effect instanceof DoProductionAction)
        {
            DoProductionAction doProductionAction = ((DoProductionAction) effect);

            int value = jsonEffect.getInt("value");

            doProductionAction.setValue(value);
        }

        if (effect instanceof DoHarvestAction)
        {
            DoHarvestAction doHarvestAction = ((DoHarvestAction) effect);

            int value = jsonEffect.getInt("value");

            doHarvestAction.setValue(value);
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

    protected static List<RequiredCard> loadRequiredCards (JSONArray jsonRequiredCards)
            throws  JSONException
    {
        List<RequiredCard> requiredCards = new ArrayList<>();

        for(int i = 0; i < jsonRequiredCards.length(); i++)
        {
            JSONObject jsonRequiredCard = jsonRequiredCards.getJSONObject(i);

            RequiredCard requiredCard = loadRequiredCard(jsonRequiredCard);

            requiredCards.add(requiredCard);
        }

        return requiredCards;

    }

    protected static RequiredCard loadRequiredCard(JSONObject jsonRequiredCard)
            throws JSONException

    {
        String type = jsonRequiredCard.getString("cardType");

        int value = jsonRequiredCard.getInt("value");

        CardTypeEnum cardTypeEnum = CardTypeEnum.valueOf(type);

        return new RequiredCard(value, cardTypeEnum);
    }

}
