package it.polimi.ingsw.pc22.gamebox;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PlayerBoardTest extends TestCase {

    private PlayerBoard playerBoard;
    private BonusTile bonusTile = new BonusTile();
    private List<CharacterCard> characterCards = new ArrayList<>();
    private List<BuildingCard> buildingCards = new ArrayList<>();
    private List<TerritoryCard> territoryCards = new ArrayList<>();
    private List<VentureCard> ventureCards = new ArrayList<>();
    private List<LeaderCard> leaderCards = new ArrayList<>();


    @Before
    public void setUp()
    {
        playerBoard = new PlayerBoard();

        playerBoard.setTypeOfBonusTile(0);
        playerBoard.setBonusTile(bonusTile);


        playerBoard.setCharacters(characterCards);
        playerBoard.setBuildings(buildingCards);
        playerBoard.setTerritories(territoryCards);
        playerBoard.setVentures(ventureCards);
        playerBoard.setLeaderCards(leaderCards);
    }

    @Test
    public void testGetSet()
    {
        assertEquals(characterCards, playerBoard.getCharacters());
        assertEquals(ventureCards, playerBoard.getVentures());
        assertEquals(buildingCards, playerBoard.getBuildings());
        assertEquals(leaderCards, playerBoard.getLeaderCards());
        assertEquals(territoryCards, playerBoard.getTerritories());
        assertEquals(bonusTile, playerBoard.getBonusTile());
        assertEquals(0, playerBoard.getTypeOfBonusTile());
    }
}
