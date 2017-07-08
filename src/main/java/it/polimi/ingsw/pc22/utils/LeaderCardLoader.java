package it.polimi.ingsw.pc22.utils;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.LeaderCard;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the loader of the leader cards Deck.
 * Every single card is fully loaded according to its attributes.
 *
 *
 */
public class LeaderCardLoader extends GenericLoader
{
    private static final Logger LOGGER = Logger.getLogger(LeaderCardLoader.class.getName());

    public static List<LeaderCard> loadLeaderCards
            (JSONObject jsonCardsObject) {
        
        List<LeaderCard> leaderCards = new ArrayList<>();

        JSONArray jsonCards = jsonCardsObject.getJSONArray("LeaderCards");

        try {

            for (int i = 0; i < jsonCards.length(); i++)
            {
                JSONObject jsonLeaderCard = jsonCards.getJSONObject(i);

                LeaderCard leaderCard = loadLeaderCard(jsonLeaderCard);

                leaderCards.add(leaderCard);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e)
        {
            LOGGER.log(Level.INFO, "Cards not loaded", e);
        }

        return leaderCards;

    }

    private static LeaderCard loadLeaderCard(JSONObject jsonCard)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        String name = jsonCard.getString("name");
        boolean faceUp = jsonCard.getBoolean( "faceUp");
        int number = jsonCard.getInt("number");

        List<RequiredCard> requiredCards = loadRequiredCards(jsonCard.getJSONArray("requiredCard"));

        List<Asset> requiredAssets = loadAssetList(jsonCard.getJSONArray("requiredAssets"));

        List<Effect> effects = loadEffectList(jsonCard.getJSONArray("effects"));

        LeaderCard leaderCard = new LeaderCard();

        leaderCard.setName(name);
        leaderCard.setFaceUp(faceUp);
        leaderCard.setNumber(number);
        leaderCard.setRequiredCards(requiredCards);
        leaderCard.setRequiredAssets(requiredAssets);
        leaderCard.setEffects(effects);
        leaderCard.setPlayed(false);

        return leaderCard;

    }


}


