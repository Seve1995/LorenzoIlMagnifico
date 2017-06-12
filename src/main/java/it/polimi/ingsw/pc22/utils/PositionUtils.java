package it.polimi.ingsw.pc22.utils;

import it.polimi.ingsw.pc22.gamebox.*;

/**
 * Created by fandroid95 on 11/06/2017.
 */
public class PositionUtils
{
    public static String getActionAvailableString(GameBoard gameBoard)
    {
        String actions = "";

        if (areTowersAvailable(gameBoard.getTowers()))
            actions = "- Metti familiare su torre (es: set tower <familiar> <servants> <type> <floor>)\n";

        if (isMarketAvailable(gameBoard.getMarket()))
            actions += "- Metti familiare su market (es: set market <familiar> <servants> <zone>)\n";

        if (isProductionAvailable(gameBoard.getProduction()))
            actions += "- Metti familiare nella produzione (es: set production <familiar> <servants>)\n";

        if (isHarvestAvailable(gameBoard.getHarvest()))
            actions += "- Metti familiare nella raccolto (es: set harvest <familiar> <servants>)\n";

        if (isCouncilAvailable(gameBoard.getCouncilPalace()))
            actions += "- Metti familiare nel concilio (es: set council <familiar> <servants>)";

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
            if (cell.getFamilyMember() != null) return false;
        }

        return true;
    }

    public static boolean isCouncilAvailable(CouncilPalace councilPalace)
    {
        for (CouncilPalaceCell cell : councilPalace.getCouncilPalaceCells())
        {
            if (cell.getFamilyMember() != null) return false;
        }

        return true;
    }

    public static boolean isHarvestAvailable(Harvest harvest)
    {
        for (HarvestCell cell : harvest.getHarvestCell())
        {
            if (cell.getFamilyMember() != null) return false;
        }

        return true;
    }

    public static boolean isProductionAvailable(Production production)
    {
        for (ProductionCell cell : production.getProductionCell())
        {
            if (cell.getFamilyMember() != null) return false;
        }

        return true;
    }
}
