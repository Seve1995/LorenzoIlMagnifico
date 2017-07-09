package it.polimi.ingsw.pc22.effects;


import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.player.Player;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class EndGameMalusTest extends TestCase {

    private Player player;
    private GameBoard gameBoard;

    @Before
    public void setUp()
    {
        player = new Player("test", "test", false);


    }

    @Test
    public void testIsLegal()
    {
        EndGameMalus endGameMalus = new EndGameMalus();


        assertEquals(true, endGameMalus.isLegal(player, gameBoard));
    }

    @Test
    public void testExecuteEffectFirstBranch()
    {
        EndGameMalus endGameMalus = new EndGameMalus();

        endGameMalus.setLoseOneVictoryPointEveryFiveVictoryPoints(true);

        player.setVictoryPoints(5);

        endGameMalus.setNoCardVictoryPoint(CardTypeEnum.BUILDING);

        endGameMalus.executeEffects(player, gameBoard);

        assertEquals(4, player.getVictoryPoints());
    }

    @Test
    public void testExecuteEffectsSecondBranch()
    {
        EndGameMalus endGameMalus = new EndGameMalus();

        endGameMalus.setLoseOneVictoryPointEveryFiveVictoryPoints(false);

        endGameMalus.setAssetsMalus(new ArrayList<>());

        endGameMalus.getAssetsMalus().add(new Asset(1, AssetType.COIN));

        endGameMalus.setNoCardVictoryPoint(CardTypeEnum.BUILDING);

        player.setCoins(50);

        player.setVictoryPoints(5);

        endGameMalus.executeEffects(player, gameBoard);

        assertEquals(0, player.getVictoryPoints());

    }

    @Test
    public void testExecuteEffectsThirdBranch()
    {
        EndGameMalus endGameMalus = new EndGameMalus();

        endGameMalus.setLoseOneVictoryPointEveryFiveVictoryPoints(false);

        List<Asset> costs = new ArrayList<>();

        costs.add(new Asset(1, AssetType.WOOD));

        player.getPlayerBoard().getBuildings().add(new BuildingCard(null, 0, null, null, costs, 0, 0));

        endGameMalus.setNoCardVictoryPoint(CardTypeEnum.BUILDING);

        endGameMalus.setBuildingCardsMalus(true);

        player.setVictoryPoints(5);

        endGameMalus.executeEffects(player, gameBoard);

        assertEquals(4, player.getVictoryPoints());

    }

    @Test
    public void testExecuteEffectsFourthBranch()
    {
        EndGameMalus endGameMalus = new EndGameMalus();

        endGameMalus.setLoseOneVictoryPointEveryFiveVictoryPoints(false);

        endGameMalus.setNoCardVictoryPoint(CardTypeEnum.CHARACTER);

        endGameMalus.executeEffects(player, gameBoard);

        assertEquals(new ArrayList<>(), player.getPlayerBoard().getCharacters());


    }

    @Test
    public void testExecuteEffectsFifthBranch()
    {
        EndGameMalus endGameMalus = new EndGameMalus();

        endGameMalus.setLoseOneVictoryPointEveryFiveVictoryPoints(false);

        endGameMalus.setNoCardVictoryPoint(CardTypeEnum.VENTURE);

        endGameMalus.executeEffects(player, gameBoard);

        assertEquals(new ArrayList<>(), player.getPlayerBoard().getVentures());

    }

    @Test
    public void testExecuteEffectsSixthBranch()
    {
        EndGameMalus endGameMalus = new EndGameMalus();

        endGameMalus.setLoseOneVictoryPointEveryFiveVictoryPoints(false);

        endGameMalus.setNoCardVictoryPoint(CardTypeEnum.TERRITORY);

        endGameMalus.executeEffects(player, gameBoard);

        assertEquals(new ArrayList<>(), player.getPlayerBoard().getTerritories());

    }

    @Test
    public void testSumWoodsAndStones()
    {
        List<Asset> assets = new ArrayList<>();

        assets.add(new Asset(50, AssetType.WOOD));

        assets.add(new Asset(50, AssetType.STONE));

        EndGameMalus endGameMalus = new EndGameMalus();

        assertEquals(100, endGameMalus.sumWoodsAndStones(assets));

    }

}
