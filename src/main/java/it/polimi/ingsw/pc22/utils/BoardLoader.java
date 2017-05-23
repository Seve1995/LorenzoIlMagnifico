package it.polimi.ingsw.pc22.utils;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.gamebox.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BoardLoader
{
    public static GameBoard loadGameBoard(JSONObject board)
    {
        GameBoard gameBoard = new GameBoard();

        Tower[] towers = loadTowers(board);

        gameBoard.setTower(towers);

        CouncilPalace councilPalace = loadCouncilPalace(board);

        gameBoard.setCouncilPalace(councilPalace);

        return gameBoard;
    }

    private static Tower[] loadTowers(JSONObject board)
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

    private static Tower loadTower(JSONArray cells, String towerType)
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

            try
            {
                Class effectName = Class.forName(name);

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

    private static Asset loadAsset(JSONObject jsonAsset)
    {
        String type = jsonAsset.getString("type");

        int value = jsonAsset.getInt("value");

        AssetType assetType = AssetType.valueOf(type);

        return new Asset(value, assetType);
    }

    private static CouncilPalace loadCouncilPalace(JSONObject board)
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
}
