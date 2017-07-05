package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.effects.PickOneCouncilPrivilege;
import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.utils.ParseEnum;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fandroid95 on 06/06/2017.
 */
public class ActionFactory
{
    private ActionFactory()
    {
        throw new IllegalAccessError("Utility class");
    }

    private static String action_path = "it.polimi.ingsw.pc22.actions.";

    private static final Logger LOGGER = Logger.getLogger(ActionFactory.class.getName());

    public static Action createAction(String userCommand, Player player)
    {
        String actionName = ParseEnum.parseAction(userCommand);

        if (actionName == null)
            return null;

        Action action;

        try
        {
            String className = action_path + actionName;

            Class actionClass = Class.forName(className);

            action = (Action) actionClass.newInstance();
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException e)
        {
            LOGGER.log(Level.INFO, "Class not found", e);

            return null;
        }

        System.out.println("ActionFactory" + action);

        setActionParameter(action, userCommand);

        if (!action.isFamiliarNeeded()) return action;

        FamilyMember member = getParsedFamiliar(userCommand, player);

        if (member == null)
            return null;

        action.setFamilyMember(member);

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

        matcher.find();

        ColorsEnum color = ColorsEnum.getColorFromString(matcher.group(0));

        return player.getUnusedFamilyMemberByColor(color);
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

        if (action instanceof ActiveLeaderCard)
        {
            Pattern pattern = Pattern.compile("[0-9]$");

            Matcher matcher = pattern.matcher(userCommand);

            matcher.find();

            Integer index = Integer.parseInt(matcher.group(0));

            ((ActiveLeaderCard) action).setIndex(index);

            action.setFamiliarNeeded(false);
        }

        if (action instanceof DiscardLeaderCard)
        {
            Pattern pattern = Pattern.compile("[0-9]$");

            Matcher matcher = pattern.matcher(userCommand);

            matcher.find();

            Integer index = Integer.parseInt(matcher.group(0));

            ((DiscardLeaderCard) action).setIndex(index);

            ((DiscardLeaderCard) action).setPickPrivilege
                    (new PickOneCouncilPrivilege());

            action.setFamiliarNeeded(false);
        }

        if (action instanceof PlayLeaderCard)
        {
            Pattern pattern = Pattern.compile("[0-9]$");

            Matcher matcher = pattern.matcher(userCommand);

            matcher.find();

            Integer index = Integer.parseInt(matcher.group(0));

            ((PlayLeaderCard) action).setIndex(index);

            action.setFamiliarNeeded(false);
        }

        if (action instanceof PassTurn)
        {
            action.setFamiliarNeeded(false);
        }
    }

}
