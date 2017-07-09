package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.adapters.SocketIOAdapter;
import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.utils.RequiredCard;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by fandroid95 on 04/07/2017.
 */
public class PlayLeaderCardTest
{
    private GameBoard gameBoard;
    private PlayerBoard playerBoard;

    private List<LeaderCard> cards;

    private RequiredCard requiredCard;

    private LeaderCard card;

    private Player player;

    private IOAdapter adapter;

    @Before
    public void setUp()
    {
        gameBoard = new GameBoard();

        player = mock(Player.class);

        playerBoard = mock(PlayerBoard.class);

        cards = new ArrayList<>();

        card = new LeaderCard();

        cards.add(card);

        card.setEffects(new ArrayList<>());

        Asset asset = new Asset(1, AssetType.COIN);

        List<Asset> assets = new ArrayList<>();

        assets.add(asset);

        card.setRequiredAssets(assets);

        when(player.getPlayerBoard()).thenReturn(playerBoard);

        adapter = mock(SocketIOAdapter.class);
    }

    @Test
    public void isLegalIndexOverFlow()
    {
        PlayLeaderCard playLeaderCard = new PlayLeaderCard();
        playLeaderCard.setIndex(2);

        when(player.getLeaderCards()).thenReturn(cards);

        assertFalse(playLeaderCard.isLegal(player, gameBoard));
    }

    @Test
    public void isLegalWrongSize()
    {
        PlayLeaderCard playLeaderCard = new PlayLeaderCard();
        playLeaderCard.setIndex(0);

        when(player.getLeaderCards()).thenReturn(cards);

        List<LeaderCard> cards = mock(ArrayList.class);

        when(cards.size()).thenReturn(5);

        when(playerBoard.getLeaderCards()).thenReturn(cards);

        assertFalse(playLeaderCard.isLegal(player, gameBoard));
    }

    @Test
    public void isLegalAsset()
    {
        PlayLeaderCard playLeaderCard = new PlayLeaderCard();
        playLeaderCard.setIndex(0);

        when(player.getLeaderCards()).thenReturn(cards);

        when(playerBoard.getLeaderCards()).thenReturn(cards);

        when(player.getAsset(AssetType.COIN)).thenReturn(0);

        assertFalse(playLeaderCard.isLegal(player, gameBoard));
    }

    @Test
    public void isLegalTrue()
    {
        PlayLeaderCard playLeaderCard = new PlayLeaderCard();
        playLeaderCard.setIndex(0);

        when(player.getLeaderCards()).thenReturn(cards);

        when(playerBoard.getLeaderCards()).thenReturn(cards);

        when(player.getAsset(AssetType.COIN)).thenReturn(1);

        assertTrue(playLeaderCard.isLegal(player, gameBoard));
    }

    @Test
    public void isLegalTrueNullRequirements()
    {
        PlayLeaderCard playLeaderCard = new PlayLeaderCard();
        playLeaderCard.setIndex(0);

        ArrayList<LeaderCard> cards = new ArrayList<>();

        LeaderCard card = mock(LeaderCard.class);

        cards.add(card);

        when(player.getLeaderCards()).thenReturn(cards);

        when(playerBoard.getLeaderCards()).thenReturn(cards);

        when(card.getRequiredAssets()).thenReturn(null);

        when(card.getRequiredCards()).thenReturn(null);

        assertTrue(playLeaderCard.isLegal(player, gameBoard));
    }


    @Test
    public void isLegalTrueNullLeaderCard()
    {
        PlayLeaderCard playLeaderCard = new PlayLeaderCard();
        playLeaderCard.setIndex(0);

        when(player.getLeaderCards()).thenReturn(cards);

        when(playerBoard.getLeaderCards()).thenReturn(cards);

        when(player.getAsset(AssetType.COIN)).thenReturn(1);

        card.setRequiredCards(null);

        assertTrue(playLeaderCard.isLegal(player, gameBoard));
    }

    @Test
    public void isLegalRequiredClientsBuilding()
    {
        PlayLeaderCard playLeaderCard = new PlayLeaderCard();
        playLeaderCard.setIndex(0);

        when(player.getLeaderCards()).thenReturn(cards);

        when(playerBoard.getLeaderCards()).thenReturn(cards);

        when(player.getAsset(AssetType.COIN)).thenReturn(1);

        requiredCard = new RequiredCard(1, CardTypeEnum.BUILDING);

        List<RequiredCard> requiredCards = new ArrayList<>();

        requiredCards.add(requiredCard);

        card.setRequiredCards(requiredCards);

        List<BuildingCard> buildingCards = mock(ArrayList.class);

        when(buildingCards.size()).thenReturn(2);

        when(playerBoard.getBuildings()).thenReturn(buildingCards);

        assertTrue(playLeaderCard.isLegal(player, gameBoard));

        when(buildingCards.size()).thenReturn(0);

        assertFalse(playLeaderCard.isLegal(player, gameBoard));
    }

    @Test
    public void isLegalRequiredClientsTerritories()
    {
        PlayLeaderCard playLeaderCard = new PlayLeaderCard();
        playLeaderCard.setIndex(0);

        when(player.getLeaderCards()).thenReturn(cards);

        when(playerBoard.getLeaderCards()).thenReturn(cards);

        when(player.getAsset(AssetType.COIN)).thenReturn(1);

        requiredCard = new RequiredCard(1, CardTypeEnum.TERRITORY);

        List<RequiredCard> requiredCards = new ArrayList<>();

        requiredCards.add(requiredCard);

        card.setRequiredCards(requiredCards);

        List<TerritoryCard> territoryCards = mock(ArrayList.class);

        when(territoryCards.size()).thenReturn(2);

        when(playerBoard.getTerritories()).thenReturn(territoryCards);

        assertTrue(playLeaderCard.isLegal(player, gameBoard));

        when(territoryCards.size()).thenReturn(0);

        assertFalse(playLeaderCard.isLegal(player, gameBoard));
    }

    @Test
    public void isLegalRequiredClientsCharacters()
    {
        PlayLeaderCard playLeaderCard = new PlayLeaderCard();
        playLeaderCard.setIndex(0);

        when(player.getLeaderCards()).thenReturn(cards);

        when(playerBoard.getLeaderCards()).thenReturn(cards);

        when(player.getAsset(AssetType.COIN)).thenReturn(1);

        requiredCard = new RequiredCard(1, CardTypeEnum.CHARACTER);

        List<RequiredCard> requiredCards = new ArrayList<>();

        requiredCards.add(requiredCard);

        card.setRequiredCards(requiredCards);

        List<CharacterCard> characterCards = mock(ArrayList.class);

        when(characterCards.size()).thenReturn(2);

        when(playerBoard.getCharacters()).thenReturn(characterCards);

        assertTrue(playLeaderCard.isLegal(player, gameBoard));

        when(characterCards.size()).thenReturn(0);

        assertFalse(playLeaderCard.isLegal(player, gameBoard));
    }

    @Test
    public void isLegalRequiredClientsVenture()
    {
        PlayLeaderCard playLeaderCard = new PlayLeaderCard();
        playLeaderCard.setIndex(0);

        when(player.getLeaderCards()).thenReturn(cards);

        when(playerBoard.getLeaderCards()).thenReturn(cards);

        when(player.getAsset(AssetType.COIN)).thenReturn(1);

        requiredCard = new RequiredCard(1, CardTypeEnum.VENTURE);

        List<RequiredCard> requiredCards = new ArrayList<>();

        requiredCards.add(requiredCard);

        card.setRequiredCards(requiredCards);

        List<VentureCard> ventureCards = mock(ArrayList.class);

        when(ventureCards.size()).thenReturn(2);

        when(playerBoard.getVentures()).thenReturn(ventureCards);

        assertTrue(playLeaderCard.isLegal(player, gameBoard));

        when(ventureCards.size()).thenReturn(0);

        assertFalse(playLeaderCard.isLegal(player, gameBoard));
    }

    @Test
    public void executeTrue()
    {
        PlayLeaderCard playLeaderCard = new PlayLeaderCard();
        playLeaderCard.setIndex(0);

        when(player.getLeaderCards()).thenReturn(cards);

        when(player.getAdapter()).thenReturn(adapter);

        when(playerBoard.getLeaderCards()).thenReturn(cards);

        when(player.getAsset(AssetType.COIN)).thenReturn(1);

        card.setRequiredCards(null);

        assertTrue(playLeaderCard.executeAction(player, gameBoard));

        assertTrue(card.isPlayed());

        ArrayList<LeaderCard> expectedArray = new ArrayList<>();

        expectedArray.add(card);
        expectedArray.add(card);

        assertEquals(playerBoard.getLeaderCards(), expectedArray);
    }

    @Test
    public void executeFalse()
    {
        PlayLeaderCard playLeaderCard = new PlayLeaderCard();
        playLeaderCard.setIndex(2);

        when(playerBoard.getLeaderCards()).thenReturn(cards);

        when(player.getLeaderCards()).thenReturn(cards);

        when(player.getAdapter()).thenReturn(adapter);

        assertFalse(playLeaderCard.executeAction(player, gameBoard));

        assertFalse(card.isPlayed());

        ArrayList<LeaderCard> expectedArray = new ArrayList<>();

        expectedArray.add(card);

        assertEquals(playerBoard.getLeaderCards(), expectedArray);
    }

    @Test
    public void equals()
    {
        PlayLeaderCard playLeaderCard = new PlayLeaderCard();

        playLeaderCard.setIndex(0);

        PlayLeaderCard playLeaderCard1 = new PlayLeaderCard();

        playLeaderCard1.setIndex(0);

        assertEquals(playLeaderCard, playLeaderCard1);

        assertEquals(playLeaderCard, playLeaderCard);

        assertFalse(playLeaderCard.equals(new Integer(1)));

        assertFalse(playLeaderCard.equals(null));
    }
}
