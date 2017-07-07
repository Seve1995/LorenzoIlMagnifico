package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.gamebox.Tower;
import it.polimi.ingsw.pc22.gamebox.TowerCell;
import it.polimi.ingsw.pc22.player.CardModifier;
import it.polimi.ingsw.pc22.player.Player;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matteo on 05/07/17.
 */
public class PickTowerCardTest extends TestCase {

    private Player player;
    private GameBoard gameBoard;

    @Before
    public void setUp()
    {
        player = new Player(null, null, false);

        gameBoard = new GameBoard();
    }

    @Test
    public void testIsLegal()
    {
        PickTowerCard pickTowerCard = new PickTowerCard();

        pickTowerCard.setFloor(-1);

        assertEquals(false, pickTowerCard.isLegal(player, gameBoard));

        pickTowerCard.setCardType(null);

        assertEquals(false, pickTowerCard.isLegal(player, gameBoard));

        pickTowerCard.setFloor(0);

        gameBoard.setTowers(new Tower[4]);

        gameBoard.getTowers()[0] = new Tower(CardTypeEnum.BUILDING);

        gameBoard.getTowers()[0].setTowerCells(new ArrayList<>());

        gameBoard.getTowers()[0].getTowerCells().add(new TowerCell(0, null));

        gameBoard.getTowers()[0].getTowerCells().get(0).setDevelopmentCard(null);

        pickTowerCard.setCardType(CardTypeEnum.BUILDING);

        assertEquals(false, pickTowerCard.isLegal(player, gameBoard));

        List<CardModifier> cardModifiers = new ArrayList<>();

        cardModifiers.add (new CardModifier());

        cardModifiers.get(0).setCardType(CardTypeEnum.BUILDING);

        cardModifiers.get(0).setValueModifier(0);

        pickTowerCard.setCardType(CardTypeEnum.BUILDING);

        player.setCardModifiers(cardModifiers);

        assertEquals(false,  pickTowerCard.isLegal(player, gameBoard));

        pickTowerCard.setCardType(CardTypeEnum.CHARACTER);












    }

}
