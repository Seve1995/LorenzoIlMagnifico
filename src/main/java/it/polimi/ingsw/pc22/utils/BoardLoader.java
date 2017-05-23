package it.polimi.ingsw.pc22.utils;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.gamebox.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BoardLoader
{
    public static String EFFECT_PATH = "it.polimi.ingsw.pc22.effects.";

    public static GameBoard loadGameBoard(JSONObject board)
    {
        GameBoard gameBoard = null;

        try
        {
            gameBoard = new GameBoard();

            Tower[] towers = loadTowers(board);

            gameBoard.setTower(towers);

            CouncilPalace councilPalace = loadCouncilPalace(board);

            gameBoard.setCouncilPalace(councilPalace);

            Market market = loadMarket(board);

            gameBoard.setMarket(market);

            Production production = loadProduction(board);

            gameBoard.setProduction(production);

            Harvest harvest = loadHarvest(board);

            gameBoard.setHarvest(harvest);

        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        return gameBoard;
    }

    private static Tower[] loadTowers(JSONObject board) throws JSONException
    {
        Tower[] towersArray = new Tower[4];

        try
        {
            JSONArray towers = board.getJSONArray("towers");

            for (int i = 0; i < towers.length(); i++)
            {
                JSONObject tower = towers.getJSONObject(i);

                String towerType = tower.getString("towerType");

                JSONArray cells = tower.getJSONArray("towerCells");

                Tower loadedTower = loadTower(cells, towerType);

                if (loadedTower == null) continue;

                towersArray[i] = loadedTower;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return towersArray;
    }

    private static Tower loadTower(JSONArray cells, String towerType) throws JSONException
    {
        CardTypeEnum towerTypeEnum = CardTypeEnum.valueOf(towerType);

        Tower tower = new Tower(towerTypeEnum);

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

                List<Effect> effects = loadEffect(effectsArray);

                towerCell.setEffects(effects);
            }

            towerCells.add(towerCell);
        }

        tower.setTowerCells(towerCells);

        return tower;
    }

    private static List<Effect> loadEffect(JSONArray effectsArray)
    {
        List<Effect> effects = new ArrayList<>();

        for(int i = 0; i < effectsArray.length(); i++) {
            JSONObject jsonEffect = effectsArray.getJSONObject(i);

            String name = jsonEffect.getString("name");

            Effect effect = null;

            String className = EFFECT_PATH + name;

            try
            {
                Class effectName = Class.forName(className);

                effect = (Effect) effectName.newInstance();

            } catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            } catch (InstantiationException e)
            {
                e.printStackTrace();
            } catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }

            if (effect == null) continue;

            if (jsonEffect.isNull("asset"))
            {
                effects.add(effect);

                continue;
            }

            JSONObject jsonAsset = jsonEffect.getJSONObject("asset");

            Asset asset = loadAsset(jsonAsset);

            effect.setAsset(asset);

            effects.add(effect);
        }

        return effects;
    }

    private static Asset loadAsset(JSONObject jsonAsset) throws JSONException
    {
        String type = jsonAsset.getString("type");

        int value = jsonAsset.getInt("value");

        AssetType assetType = AssetType.valueOf(type);

        return new Asset(value, assetType);
    }

    private static CouncilPalace loadCouncilPalace(JSONObject board) throws JSONException
    {
        JSONObject jsonPalace = board.getJSONObject("councilPalace");

        int councilPalaceMaxPlaces = jsonPalace.getInt("councilPalaceMaxPlaces");

        JSONObject jsonCells = jsonPalace.getJSONObject("councilPalaceCells");

        int requiredDiceValue = jsonCells.getInt("requiredDiceValue");

        JSONArray jsonEffects = jsonCells.getJSONArray("resourceBonus");

        List<Effect> effects = loadEffect(jsonEffects);

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

    private static Market loadMarket(JSONObject board) throws JSONException
    {
        JSONArray jsonMarketCells = board.getJSONArray("market");

        List<MarketCell> cells = new ArrayList<>();

        for (int i = 0; i < jsonMarketCells.length(); i++)
        {
            JSONObject jsonCell = jsonMarketCells.getJSONObject(i);

            int requiredDiceValue = jsonCell.getInt("requiredDiceValue");

            JSONArray jsonEffect = jsonCell.getJSONArray("effects");

            List<Effect> effects = loadEffect(jsonEffect);

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

        HarvestCell[] harvestCells = new HarvestCell[harvestMaxPlaces];

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

        ProductionCell[] productionCells = new ProductionCell[productionMaxPlaces];

        JSONArray jsonProductionCells = jsonProduction.getJSONArray("harvestCells");

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
