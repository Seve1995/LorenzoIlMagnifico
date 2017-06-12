package it.polimi.ingsw.pc22.utils;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.gamebox.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoardLoader extends GenericLoader
{
    private static final Logger LOGGER = Logger.getLogger(BoardLoader.class.getName());

    public static GameBoard loadGameBoard(JSONObject board)
    {
        GameBoard gameBoard = null;

        try
        {
            gameBoard = new GameBoard();

            Tower[] towers = loadTowers(board);

            gameBoard.setTowers(towers);

            CouncilPalace councilPalace = loadCouncilPalace(board);

            gameBoard.setCouncilPalace(councilPalace);

            Market market = loadMarket(board);

            gameBoard.setMarket(market);

            Production production = loadProduction(board);

            gameBoard.setProduction(production);

            Harvest harvest = loadHarvest(board);

            gameBoard.setHarvest(harvest);

        }

        catch
        (
            JSONException | IllegalAccessException |
                    InstantiationException | ClassNotFoundException e
        )
        {
            LOGGER.log(Level.INFO, "Board not loaded", e);
        }

        return gameBoard;
    }

    private static Tower[] loadTowers(JSONObject board)
    throws JSONException, IllegalAccessException, ClassNotFoundException,
            InstantiationException
    {
        Tower[] towersArray = new Tower[4];

        JSONArray towers = board.getJSONArray("towers");

        for (int i = 0; i < towers.length(); i++)
        {
            JSONObject tower = towers.getJSONObject(i);

            String towerType = tower.getString("towerType");

            JSONArray cells = tower.getJSONArray("towerCells");

            Tower loadedTower = loadTower(cells, towerType);

            towersArray[i] = loadedTower;
        }

        return towersArray;
    }

    private static Tower loadTower(JSONArray cells, String towerType)
    throws JSONException, InstantiationException, IllegalAccessException,
            ClassNotFoundException
    {
        CardTypeEnum towerTypeEnum = CardTypeEnum.valueOf(towerType);

        Tower tower = new Tower(towerTypeEnum);

        tower.setTowerType(towerTypeEnum);

        List<TowerCell> towerCells = new ArrayList<>();

        for (int i = 0; i < cells.length(); i++)
        {
            JSONObject cell = cells.getJSONObject(i);

            int requiredDiceValue = cell.getInt("requiredDiceValue");

            TowerCell towerCell = new TowerCell();

            towerCell.setRequiredDiceValue(requiredDiceValue);

            if (!cell.isNull("effects"))
            {
                JSONArray effectsArray = cell.getJSONArray("effects");

                List<Effect> effects = loadEffectList(effectsArray);

                towerCell.setEffects(effects);
            }

            towerCells.add(towerCell);
        }

        tower.setTowerCells(towerCells);

        return tower;
    }

    private static CouncilPalace loadCouncilPalace(JSONObject board)
    throws JSONException, InstantiationException, IllegalAccessException,
            ClassNotFoundException
    {
        JSONObject jsonPalace = board.getJSONObject("councilPalace");

        int councilPalaceMaxPlaces = jsonPalace.getInt("councilPalaceMaxPlaces");

        JSONObject jsonCells = jsonPalace.getJSONObject("councilPalaceCells");

        int requiredDiceValue = jsonCells.getInt("requiredDiceValue");

        JSONArray jsonEffects = jsonCells.getJSONArray("effects");

        List<Effect> effects = loadEffectList(jsonEffects);

        CouncilPalace palace = new CouncilPalace();

        CouncilPalaceCell[] cells = new CouncilPalaceCell[councilPalaceMaxPlaces];

        for (int i = 0; i < councilPalaceMaxPlaces; i++)
        {
            CouncilPalaceCell cell =
                    new CouncilPalaceCell(requiredDiceValue,effects);

            cells[i] = cell;
        }

        palace.setCouncilPalaceCells(cells);

        return palace;
    }

    private static Market loadMarket(JSONObject board)
    throws JSONException, InstantiationException, IllegalAccessException,
            ClassNotFoundException
    {
        JSONArray jsonMarketCells = board.getJSONArray("market");

        List<MarketCell> cells = new ArrayList<>();

        for (int i = 0; i < jsonMarketCells.length(); i++)
        {
            JSONObject jsonCell = jsonMarketCells.getJSONObject(i);

            int requiredDiceValue = jsonCell.getInt("requiredDiceValue");

            JSONArray jsonEffect = jsonCell.getJSONArray("effects");

            List<Effect> effects = loadEffectList(jsonEffect);

            MarketCell cell = new MarketCell(requiredDiceValue, effects);

            boolean blockedCell = jsonCell.getBoolean("blockedCell");

            cell.setABlockedCell(blockedCell);

            cells.add(cell);
        }

        return new Market(cells);
    }

    private static Harvest loadHarvest(JSONObject board) throws JSONException
    {
        JSONObject jsonHarvest = board.getJSONObject("harvest");

        int harvestMaxPlaces = jsonHarvest.getInt("harvestMaxPlaces");

        HarvestCell[] harvestCells = new HarvestCell[harvestMaxPlaces + 1];

        JSONArray jsonHarvestCells = jsonHarvest.getJSONArray("harvestCells");

        for (int i = 0; i < jsonHarvestCells.length(); i++)
        {
            JSONObject jsonCell = jsonHarvestCells.getJSONObject(i);

            int requiredDiceValue = jsonCell.getInt("requiredDiceValue");

            boolean blockedCell = jsonCell.getBoolean("blockedCell");

            boolean penaltyCell = jsonCell.getBoolean("penaltyCell");

            HarvestCell harvestCell = new HarvestCell(requiredDiceValue);

            harvestCell.setABlockedCell(blockedCell);
            harvestCell.setAPenaltyCell(penaltyCell);

            harvestCells[i] = harvestCell;
        }

       return new Harvest(harvestCells);
    }

    private static Production loadProduction(JSONObject board) throws JSONException
    {
        JSONObject jsonProduction = board.getJSONObject("production");

        int productionMaxPlaces = jsonProduction.getInt("productionMaxPlaces");

        ProductionCell[] productionCells = new ProductionCell[productionMaxPlaces + 1];

        JSONArray jsonProductionCells = jsonProduction.getJSONArray("productionCells");

        for (int i = 0; i < jsonProductionCells.length(); i++)
        {
            JSONObject jsonCell = jsonProductionCells.getJSONObject(i);

            int requiredDiceValue = jsonCell.getInt("requiredDiceValue");

            boolean blockedCell = jsonCell.getBoolean("blockedCell");

            boolean penaltyCell = jsonCell.getBoolean("penaltyCell");

            ProductionCell productionCell = new ProductionCell(requiredDiceValue);

            productionCell.setABlockedCell(blockedCell);
            productionCell.setAPenaltyCell(penaltyCell);

            productionCells[i] = productionCell;
        }

        return new Production(productionCells);
    }
}
