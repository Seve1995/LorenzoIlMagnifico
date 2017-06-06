package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fandroid95 on 06/06/2017.
 */
public class ActionFactory
{
    private static Map<String, String> parsers = new HashMap<>();

    private static String ACTION_PATH = "it.polimi.ingsw.pc22.actions.";

    static
    {
        String tower = "set tower (TERRITORY|VENTURE|BUILDING|CHARACTER) [0-3]$";

        parsers.put(tower, "SettingFamiliarMemberOnTower");

        String market = "set market [0-3]$";

        parsers.put(market, "SettingFamiliarMemberOnMarket");

        String production = "set production$";

        parsers.put(production, "SettingFamiliarMemberOnProduction");

        String harvest = "set harvest$";

        parsers.put(harvest, "SettingFamiliarMemberOnHarvest");

        String council = "set council$";

        parsers.put(council, "SettingFamiliarMemberOnCouncilPalace");
    }

    public static Action createAction
        (FamilyMember familyMember, String userCommand)
    {
        String actionName = ActionFactory.parseAction(userCommand);

        if (actionName == null) return null;

        Action action;

        try
        {
            Class actionClass = Class.forName(actionName);

            action = (Action) actionClass.newInstance();
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException e)
        {
            return null;
        }

        action.setFamilyMember(familyMember);

        setActionParameter(action, userCommand);

        return action;
    }

    public static String parseAction(String action)
    {
        for (String regEx : parsers.keySet())
        {
            Pattern pattern = Pattern.compile(regEx);

            Matcher matcher = pattern.matcher(action);

            if (matcher.find()) return parsers.get(regEx);
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

            CardTypeEnum type = CardTypeEnum.valueOf(matcher.group(0));

            tower.setCardTypeEnum(type);

            Pattern floorPattern = Pattern.compile("[0-3]$");

            Matcher floorMatcher = floorPattern.matcher(userCommand);

            Integer floor = Integer.parseInt(floorMatcher.group(0));

            tower.setFloor(floor);
        }

        if (action instanceof SettingFamiliarMemberOnMarket)
        {
            SettingFamiliarMemberOnMarket market =
                    ((SettingFamiliarMemberOnMarket) action);

            Pattern pattern = Pattern.compile("[0-3]$");

            Matcher matcher = pattern.matcher(userCommand);

            Integer zone = Integer.parseInt(matcher.group(0));

            market.setZone(zone);
        }
    }

}
