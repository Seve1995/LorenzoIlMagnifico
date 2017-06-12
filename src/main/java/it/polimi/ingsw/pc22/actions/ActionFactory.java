package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fandroid95 on 06/06/2017.
 */
public class ActionFactory
{
    private static Map<String, String> parsers = new HashMap<>();

    private static String ACTION_PATH = "it.polimi.ingsw.pc22.actions.";

    private static final Logger LOGGER = Logger.getLogger(ActionFactory.class.getName());

    static
    {
        //TODO SI POSSONO TEORICAMENTE SACRIFICARE INFINITI SERVITORI?
        //TODO 2 GESTIRE ABBANDONO PARTITA E PERSISTENZA

        String tower = "set tower (BLACK|ORANGE|NEUTER|WHITE) [0-9] (TERRITORY|VENTURE|BUILDING|CHARACTER) [0-3]$";

        parsers.put(tower, "SettingFamiliarMemberOnTower");

        String market = "set market (BLACK|ORANGE|NEUTER|WHITE) [0-9] [0-3]$";

        parsers.put(market, "SettingFamiliarMemberOnMarket");

        String production = "set production (BLACK|ORANGE|NEUTER|WHITE) [0-9]$";

        parsers.put(production, "SettingFamiliarMemberOnProduction");

        String harvest = "set harvest (BLACK|ORANGE|NEUTER|WHITE) [0-9]$";

        parsers.put(harvest, "SettingFamiliarMemberOnHarvest");

        String council = "set council (BLACK|ORANGE|NEUTER|WHITE) [0-9]$";

        parsers.put(council, "SettingFamiliarMemberOnCouncilPalace");

        String pass = "^pass$";

        parsers.put(pass, "PassTurn");
    }

    public static Action createAction(String userCommand, Player player)
    {
        String actionName = parseAction(userCommand);

        System.out.println("String action name: " + actionName);

        if (actionName == null) return null;

        Action action;

        try
        {
            String className = ACTION_PATH + actionName;

            Class actionClass = Class.forName(className);

            action = (Action) actionClass.newInstance();
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException e)
        {
            LOGGER.log(Level.INFO, "Class not found", e);

            return null;
        }

        System.out.println("ActionFactory" + action);

        FamilyMember member = getParsedFamiliar(userCommand, player);

        if (member == null)
            return null;

        action.setFamilyMember(member);

        setActionParameter(action, userCommand);

        Asset servants = getParsedServants(userCommand, player);

        if (servants == null || servants.getValue() == 0)
            return action;

        return new ServantsHandler(action, servants);
    }

    private static Asset getParsedServants(String userCommand, Player player)
    {
        Pattern servantsPattern = Pattern.compile("[0-9]");

        Matcher matcher = servantsPattern.matcher(userCommand);

        if (!matcher.find())
            return null;

        Integer servantNumber;

        servantNumber = Integer.parseInt(matcher.group(0));

        if (servantNumber > player.getServants())
            return null;

        return new Asset(servantNumber, AssetType.SERVANT);
    }

    private static FamilyMember getParsedFamiliar(String userCommand, Player player)
    {
        Pattern familiarPattern = Pattern.compile("(BLACK|ORANGE|NEUTER|WHITE)");

        Matcher matcher = familiarPattern.matcher(userCommand);

        if (!matcher.find())
            return null;

        ColorsEnum color = ColorsEnum.getColorFromString(matcher.group(0));

        return player.getUnusedFamilyMemberByColor(color);
    }

    public static String parseAction(String action)
    {
        for (Map.Entry<String,String> entry : parsers.entrySet())
        {
            String regEx = entry.getKey();

            Pattern pattern = Pattern.compile(regEx);

            Matcher matcher = pattern.matcher(action);

            System.out.println("pattern " + pattern);

            System.out.println(parsers.get(regEx));

            if (matcher.find())
            {
                return parsers.get(regEx);
            }
        }

        return null;
    }

    private static void setActionParameter
        (Action action, String userCommand)
    {
        if (action instanceof SettingFamiliarMemberOnTower)
        {
            SettingFamiliarMemberOnTower tower = ((SettingFamiliarMemberOnTower) action);

            Pattern pattern =
                    Pattern.compile("(TERRITORY|VENTURE|BUILDING|CHARACTER)");

            Matcher matcher = pattern.matcher(userCommand);

            matcher.find();

            CardTypeEnum type = CardTypeEnum.valueOf(matcher.group(0));

            tower.setCardTypeEnum(type);

            Pattern floorPattern = Pattern.compile("[0-3]$");

            Matcher floorMatcher = floorPattern.matcher(userCommand);

            floorMatcher.find();

            Integer floor = Integer.parseInt(floorMatcher.group(0));

            tower.setFloor(floor);
        }

        if (action instanceof SettingFamiliarMemberOnMarket)
        {
            SettingFamiliarMemberOnMarket market =
                    ((SettingFamiliarMemberOnMarket) action);

            Pattern pattern = Pattern.compile("[0-3]$");

            Matcher matcher = pattern.matcher(userCommand);

            matcher.find();

            Integer zone = Integer.parseInt(matcher.group(0));

            market.setZone(zone);
        }
    }

}
