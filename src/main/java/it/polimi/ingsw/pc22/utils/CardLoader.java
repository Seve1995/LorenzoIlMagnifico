package it.polimi.ingsw.pc22.utils;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.gamebox.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by fandroid95 on 31/05/2017.
 */
public class CardLoader extends GenericLoader
{
    private static final Logger LOGGER = Logger.getLogger(CardLoader.class.getName());

    public static List<DevelopmentCard> loadCards(JSONObject jsonCardsObject)
    {
        List<DevelopmentCard> cards = new ArrayList<>();

        JSONArray jsonCards = jsonCardsObject.getJSONArray("cards");

        try {
            for(int i  = 0; i < jsonCards.length(); i++)
            {
                JSONObject jsonCard = jsonCards.getJSONObject(i);

                DevelopmentCard card = loadCard(jsonCard);

                cards.add(card);
            }
        }
            catch (ClassNotFoundException | InstantiationException | IllegalAccessException e)
        {
            LOGGER.log(Level.INFO, "Cards not loaded", e);
        }

        return cards;
        
    }

    private static DevelopmentCard loadCard(JSONObject jsonCard)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        String name = jsonCard.getString("name");

        int cardNumber = jsonCard.getInt("cardNumber");

        String cardTypeValue = jsonCard.getString("cardType");

        CardTypeEnum cardType = CardTypeEnum.valueOf(cardTypeValue);

        int roundNumber = jsonCard.getInt("roundNumber");

        List<Effect> immediateEffects = null;

        if (!jsonCard.isNull("immediateEffect"))
            immediateEffects = loadEffectList(jsonCard.getJSONArray("immediateEffect"));

        List<Effect> permanentEffect = null;

        if (!jsonCard.isNull("permanentEffect"))
            permanentEffect = loadEffectList(jsonCard.getJSONArray("permanentEffect"));

        DevelopmentCard card = createCard
                (cardType, name, roundNumber, cardNumber, immediateEffects, permanentEffect);

        setCustomAttributes(jsonCard, cardType, card);

        return card;
    }

    private static void setCustomAttributes
            (JSONObject jsonCard, CardTypeEnum type, DevelopmentCard card)
    {
        switch (type)
        {
            case BUILDING:

                loadCustomParametersBuildingCard(jsonCard, card);

                break;

            case VENTURE:

                loadCustomParametersVentureCard(jsonCard, card);

                break;

            case TERRITORY:

                loadCustomParametersTerritoryCard(jsonCard, card);

                break;

            case CHARACTER:

                loadCustomParametersCharacterCard(jsonCard, card);

                break;
        }
    }

    private static void loadCustomParametersTerritoryCard
            (JSONObject jsonCard, DevelopmentCard card)
    {
        TerritoryCard currentCard = ((TerritoryCard)card);

        int permanentEffectActivationCost =
                jsonCard.getInt("permanentEffectActivationCost");

        currentCard.setPermanentEffectActivationCost(permanentEffectActivationCost);
    }

    private static void loadCustomParametersCharacterCard
        (JSONObject jsonCard, DevelopmentCard card)
    {
        CharacterCard currentCard = ((CharacterCard) card);

        JSONObject coinCosts = jsonCard.getJSONObject("coinsCost");

        Asset costs = loadAsset(coinCosts);

        currentCard.setCoinsCost(costs);
    }

    private static void loadCustomParametersBuildingCard
        (JSONObject jsonCard, DevelopmentCard card)
    {
        BuildingCard currentCard = ((BuildingCard)card);

        int permanentEffectActivationCost =
                jsonCard.getInt("permanentEffectActivationCost");

        boolean permanentEffectChoice =
                jsonCard.getBoolean("permanentEffectChoice");

        List<Asset> costs = loadAssetList(jsonCard.getJSONArray("costs"));

        currentCard.setPermanentEffectChoiche(permanentEffectChoice);

        currentCard.setCosts(costs);
        currentCard.setPermanentEffectActivationCost(permanentEffectActivationCost);

    }

    private static void loadCustomParametersVentureCard
            (JSONObject jsonCard, DevelopmentCard card)
    {
        VentureCard currentCard = ((VentureCard) card);

        boolean requiredCostChoice =
                jsonCard.getBoolean("requiredCostChoice");

        if (!jsonCard.isNull("costs"))
        {
            List<Asset> costs = loadAssetList(jsonCard.getJSONArray("costs"));

            currentCard.setRequiredCostChoice(requiredCostChoice);

            currentCard.setResourcesCost(costs);
        }

        if (!jsonCard.isNull("militaryPointsRequired"))
        {
            Asset militaryPointsRequired =
                    loadAsset(jsonCard.getJSONObject("militaryPointsRequired"));

            currentCard.setMilitaryPointsRequired(militaryPointsRequired);
        }

        if (!jsonCard.isNull("militaryPointsCost"))
        {
            Asset militaryPointsCost =
                    loadAsset(jsonCard.getJSONObject("militaryPointsCost"));

            currentCard.setMilitaryPointsCost(militaryPointsCost);
        }

    }

    private static DevelopmentCard createCard
    (
        CardTypeEnum type, String name, int roundNumber, int cardNumber,
        List<Effect> immediateEffects, List<Effect> permanentEffects
    )
    {
        DevelopmentCard card = null;

        switch (type)
        {
            case BUILDING:
                card = new BuildingCard
                        (name, roundNumber, immediateEffects, permanentEffects, cardNumber);
                break;

            case VENTURE:
                card = new VentureCard
                        (name, roundNumber, immediateEffects, permanentEffects, cardNumber);

                break;

            case TERRITORY:
                card = new TerritoryCard
                        (name, roundNumber, immediateEffects, permanentEffects, cardNumber);

                break;

            case CHARACTER:
                card = new CharacterCard
                        (name, roundNumber, immediateEffects, permanentEffects, cardNumber);

                break;
        }

        return card;
    }

}
