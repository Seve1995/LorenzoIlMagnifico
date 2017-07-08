package it.polimi.ingsw.pc22.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class parses the strings typed by the user to the action related to them.
 */
public enum ParseEnum
{
    TOWER("set tower (BLACK|ORANGE|NEUTER|WHITE) [0-9] (TERRITORY|VENTURE|BUILDING|CHARACTER) [0-3]$", "SettingFamiliarMemberOnTower"),
    MARKET("set market (BLACK|ORANGE|NEUTER|WHITE) [0-9] [0-3]$", "SettingFamiliarMemberOnMarket"),
    PRODUCTION("set production (BLACK|ORANGE|NEUTER|WHITE) [0-9]$", "SettingFamiliarMemberOnProduction"),
    HARVEST("set harvest (BLACK|ORANGE|NEUTER|WHITE) [0-9]$", "SettingFamiliarMemberOnHarvest"),
    COUNCIL("set council (BLACK|ORANGE|NEUTER|WHITE) [0-9]$", "SettingFamiliarMemberOnCouncilPalace"),
    PASS("^pass$", "PassTurn"),
    PLAY_CARD("^play card [0-3]$", "PlayLeaderCard"),
    DISCARD_CARD("^activate card [0-3]$", "ActiveLeaderCard"),
    ACTIVATE_CARD("^discard card [0-3]$", "DiscardLeaderCard"),
    SHOW_BOARD("^show board$", "ShowBoard"),
    EXIT_GAME("^exit$", "ExitGame");


    private String regex;
    private String action;

    ParseEnum(String regex, String action)
    {
        this.regex = regex;
        this.action = action;
    }

    public static String parseAction(String action)
    {
        for (ParseEnum parser : ParseEnum.values())
        {
            String regEx = parser.regex;

            Pattern pattern = Pattern.compile(regEx);

            Matcher matcher = pattern.matcher(action);

            if (matcher.find())
            {
                return parser.action;
            }
        }

        return null;
    }
}
