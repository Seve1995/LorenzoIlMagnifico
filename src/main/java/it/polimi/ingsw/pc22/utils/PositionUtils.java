package it.polimi.ingsw.pc22.utils;

import it.polimi.ingsw.pc22.gamebox.*;

/**
 * This class shows which actions
 * are available among the others, printing
 * them on the command line.
 */
public class PositionUtils
{
    public static String getActionAvailableString(GameBoard gameBoard)
    {
        String actions = "";

        if (areTowersAvailable(gameBoard.getTowers()))
            actions = "- Set a familair on a tower (es: set tower <familiar> <servants> <type> <floor>)\n";

        if (isMarketAvailable(gameBoard.getMarket()))
            actions += "- Set a familiar on the market (es: set market <familiar> <servants> <zone>)\n";

        if (isProductionAvailable(gameBoard.getProduction()))
            actions += "- Set a familiar on the production (es: set production <familiar> <servants>)\n";

        if (isHarvestAvailable(gameBoard.getHarvest()))
            actions += "- Set a familiar on the harvest (es: set harvest <familiar> <servants>)\n";

        actions += "- Set a familiar on the council (es: set council <familiar> <servants>)";

        return actions;
    }

    public static boolean areTowersAvailable(Tower[] towers)
    {
        for (Tower tower : towers)
        {
            if (!tower.getAvailableCells().isEmpty()) return true;
        }

        return false;
    }

    public static boolean isMarketAvailable(Market market)
    {
        for (MarketCell cell : market.getMarketCells())
        {
            if (cell.getFamilyMember() == null && !(cell.isABlockedCell()))
                return true;
        }

        return false;
    }

    public static boolean isHarvestAvailable(Harvest harvest)
    {
        for (HarvestCell cell : harvest.getHarvestCell())
        {
            if (cell.getFamilyMember() == null && !(cell.isABlockedCell()))
                return true;
        }

        return false;
    }

    public static boolean isProductionAvailable(Production production)
    {
        for (ProductionCell cell : production.getProductionCell())
        {
            if (cell.getFamilyMember() == null && !(cell.isABlockedCell()))
            {
                return true;
            }
        }

        return false;
    }
}
