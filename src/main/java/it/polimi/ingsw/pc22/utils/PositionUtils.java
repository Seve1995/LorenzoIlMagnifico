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
            actions = "- Metti familiare su torre (es: set tower BUILDING 3 con numberOfFloor tra 0 e 3)\n";

        if (isMarketAvailable(gameBoard.getMarket()))
            actions += "- Metti familiare su market (es: set market ZONA con zona tra 0 e 3)\n";

        if (isProductionAvailable(gameBoard.getProduction()))
            actions += "- Metti familiare nella produzione (es: set production)\n";

        if (isHarvestAvailable(gameBoard.getHarvest()))
            actions += "- Metti familiare nella raccolto (es: set harvest)\n";

        if (isCounclAvailable(gameBoard.getCouncilPalace()))
            actions += "- Metti familiare nel concilio (es: set council)";

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
            if (cell.getFamilyMember() != null) return true;
        }

        return false;
    }

    public static boolean isCounclAvailable(CouncilPalace councilPalace)
    {
        for (CouncilPalaceCell cell : councilPalace.getCouncilPalaceCells())
        {
            if (cell.getFamilyMember() != null) return true;
        }

        return false;
    }

    public static boolean isHarvestAvailable(Harvest harvest)
    {
        for (HarvestCell cell : harvest.getHarvestCell())
        {
            if (cell.getFamilyMember() != null) return true;
        }

        return false;
    }

    public static boolean isProductionAvailable(Production production)
    {
        for (ProductionCell cell : production.getProductionCell())
        {
            if (cell.getFamilyMember() != null) return true;
        }

        return false;
    }
}
