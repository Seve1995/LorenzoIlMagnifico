package it.polimi.ingsw.pc22.utils;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.gamebox.ExcommunicationCard;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fandroid95 on 31/05/2017.
 */
public class ExcommunicationCardLoader extends GenericLoader
{
    public static List<ExcommunicationCard> loadExcomunicationCards
            (JSONObject jsonCardsObject)
    {
        List<ExcommunicationCard> cards = new ArrayList<>();

        JSONArray jsonCards = jsonCardsObject.getJSONArray("ExcommunicationCards");
        try
        {
            for(int i  = 0; i < jsonCards.length(); i++)
            {
                JSONObject jsonCard = jsonCards.getJSONObject(i);

                ExcommunicationCard card = loadExcommunicationCard(jsonCard);

                cards.add(card);
            }
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return cards;
    }

    private static ExcommunicationCard loadExcommunicationCard(JSONObject jsonCard)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        int age = jsonCard.getInt("age");
        int number = jsonCard.getInt("number");

        List<Effect> effects = loadEffectList(jsonCard.getJSONArray("effects"));

        String description = jsonCard.getString("description");

        ExcommunicationCard card = new ExcommunicationCard();

        card.setAge(age);
        card.setNumber(number);
        card.setEffects(effects);
        card.setDescription(description);
        
        return card;
    }
}
