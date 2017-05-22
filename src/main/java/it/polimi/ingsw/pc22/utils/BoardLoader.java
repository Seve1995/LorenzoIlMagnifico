package it.polimi.ingsw.pc22.utils;

import it.polimi.ingsw.pc22.gamebox.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class BoardLoader
{
    public static GameBoard loadGameBoard(JSONObject board)
    {
        GameBoard gameBoard = new GameBoard();

        Tower[] towers = loadTowers(board);

        gameBoard.setTower(towers);

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

                Tower loadedTower = loadTower(cells, towerType);;

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

        TowerCell[] towerCellsArray = new TowerCell[4];

        tower.setTowerCells(towerCellsArray);

        for (int i = 0; i < cells.length(); i++)
        {
            JSONObject cell = cells.getJSONObject(i);

            int requiredDiceValue = cell.getInt("requiredDiceValue");

            Asset asset = null;

            if (!cell.isNull("resourceBonus"))
            {
                JSONObject resourceBonus = cell.getJSONObject("resourceBonus");

                String type = resourceBonus.getString("type");

                AssetType assetType = AssetType.valueOf(type);

                int value = resourceBonus.getInt("value");

                asset = new Asset(value, assetType);
            }

            TowerCell towerCell = new TowerCell(asset, requiredDiceValue);

            towerCellsArray[i] = towerCell;
        }

        return tower;
    }
}
