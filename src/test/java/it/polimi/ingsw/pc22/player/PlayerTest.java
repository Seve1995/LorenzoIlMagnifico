package it.polimi.ingsw.pc22.player;


import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.adapters.SocketIOAdapter;
import it.polimi.ingsw.pc22.gamebox.*;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerTest extends TestCase {

    private List<Dice> dices;

    @Before
    public void setUp()
    {
        dices = new ArrayList<>();

        dices.add(new Dice(ColorsEnum.BLACK));
        dices.get(0).rollingDice();
    }

    @Test
    public void testGetSet()
    {
        Player player = new Player(null, null, false);

        player.setUsername("test");

        assertEquals("test", player.getUsername());

        player.setPassword("test");

        assertEquals("test", player.getPassword());

        player.setLogged(true);

        assertEquals(true, player.isLogged());

        player.setInMatch(true);

        player.setSuspended(true);

        assertTrue(player.isSuspended());

        assertTrue(player.isInMatch());

        player.setExcommunicationChoice(1);

        assertEquals((Integer)1, player.getExcommunicationChoice());

        player.setNumberOfMatch(5);

        assertEquals(5, player.getNumberOfMatch());

        player.setNumberOfMatchWon(2);

        player.setNumberOfMatchLost(3);

        assertEquals(2, player.getNumberOfMatchWon());

        assertEquals(3, player.getNumberOfMatchLost());

        player.setPlayerColorsEnum(PlayerColorsEnum.BLUE);

        assertEquals(PlayerColorsEnum.BLUE, player.getPlayerColorsEnum());

        player.setWoods(5);

        player.setStones(5);

        player.setCoins(5);

        player.setServants(5);

        assertEquals(5, player.getWoods());

        assertEquals(5, player.getStones());

        assertEquals(5, player.getCoins());

        assertEquals(5, player.getServants());

        player.setMilitaryPoints(5);

        player.setFaithPoints(5);

        player.setVictoryPoints(5);

        player.setPriority(1);

        assertEquals(5, player.getFaithPoints());

        assertEquals(5, player.getMilitaryPoints());

        assertEquals(5, player.getVictoryPoints());

        assertEquals(1, player.getPriority());

        player.setFamiliarPositioned(true);

        player.setHasPassed(true);

        assertEquals(true, player.isHasPassed());

        assertEquals(true, player.isFamiliarPositioned());

        player.setEndGameVictoryPoints(7);

        assertEquals(7, player.getEndGameVictoryPoints());

        List<LeaderCard> leaderCards = new ArrayList<>();

        List<CardModifier> cardModifiers = new ArrayList<>();

        player.setLeaderCards(leaderCards);

        player.setCardModifiers(cardModifiers);

        assertEquals(leaderCards, player.getLeaderCards());

        assertEquals(cardModifiers, player.getCardModifiers());

        player.setHarvestValueModifier(4);

        player.setProductionValueModifier(4);

        assertEquals(4, player.getHarvestValueModifier());

        assertEquals(4, player.getProductionValueModifier());

        player.setMilitaryPointsMalus(true);

        player.setCoinMalus(true);

        player.setServantMalus(true);

        player.setWoodMalus(true);

        player.setStoneMalus(true);

        player.setFamilyMemberMalus(true);

        player.setDisableMarket(true);

        player.setNoFirstAction(true);

        player.setNoMilitaryPointsForTerritories(true);

        player.setDontCareOccupiedPlaces(true);

        player.setDontPayThreeCoinsInTowers(true);

        player.setPlayWithThePope(true);

        player.setSantaRita(true);

        player.setSistoIV(true);

        player.setNewAction(true);

        player.setRemoveTowerBonus(true);

        assertEquals(true, player.isMilitaryPointsMalus());

        assertEquals(true, player.isCoinMalus());

        assertEquals(true, player.isServantMalus());

        assertEquals(true, player.isWoodMalus());

        assertEquals(true, player.isStoneMalus());

        assertEquals(true, player.isFamilyMemberMalus());

        assertEquals(true, player.isDisableMarket());

        assertEquals(true, player.isNoFirstAction());

        assertEquals(true, player.isNoMilitaryPointsForTerritories());

        assertEquals(true, player.isDontCareOccupiedPlaces());

        assertEquals(true, player.isDontPayThreeCoinsInTowers());

        assertEquals(true, player.isPlayWithThePope());

        assertEquals(true, player.isSantaRita());

        assertEquals(true, player.isSistoIV());

        assertEquals(true, player.isNewAction());

        assertEquals(true, player.isRemoveTowerBonus());

        IOAdapter adapter = new SocketIOAdapter();

        PlayerBoard playerBoard = new PlayerBoard();

        List<FamilyMember> familyMembers = new ArrayList<>();

        player.setAdapter(adapter);

        player.setPlayerBoard(playerBoard);

        player.setFamilyMembers(familyMembers);

        assertEquals(adapter, player.getAdapter());

        assertEquals(playerBoard, player.getPlayerBoard());

        assertEquals(familyMembers, player.getFamilyMembers());

    }


    @Test
    public void testGetAsset()
    {
        Player player = new Player(null, null, false);

        player.setCoins(4);

        assertEquals(4, player.getAsset(AssetType.COIN));

        player.setWoods(4);

        assertEquals(4, player.getAsset(AssetType.WOOD));

        player.setStones(4);

        assertEquals(4, player.getAsset(AssetType.STONE));

        player.setVictoryPoints(7);

        //assertEquals(4, player.getAsset(AssetType.VICTORYPOINT));

        player.setFaithPoints(4);

        //assertEquals(4, player.getAsset(AssetType.FAITHPOINT));

        player.setMilitaryPoints(4);

        assertEquals(4, player.getMilitaryPoints());

        player.setServants(4);

        assertEquals(4, player.getAsset(AssetType.SERVANT));

    }

    @Test
    public void testGetUnusedFamilyMembers()
    {
        Player player = new Player(null, null, false);

        List<FamilyMember> familyMembers = new ArrayList<>();

        FamilyMember familyMember = mock(FamilyMember.class);

        when(familyMember.isPlayed()).thenReturn(false);

        familyMembers.add(familyMember);

        player.setFamilyMembers(familyMembers);

        assertEquals(familyMembers, player.getUnusedFamiliarMembers());
    }

    @Test
    public void testGetUnusedFamilyMemberByColor()
    {
        Player player = new Player(null, null, false);

        List<FamilyMember> familyMembers = new ArrayList<>();

        FamilyMember familyMember = mock(FamilyMember.class);

        when(familyMember.isPlayed()).thenReturn(false);

        when(familyMember.getColor()).thenReturn(ColorsEnum.NEUTER);

        familyMembers.add(familyMember);

        player.setFamilyMembers(familyMembers);

        assertEquals(familyMembers.get(0), player.getUnusedFamilyMemberByColor(ColorsEnum.NEUTER));

    }

    @Test
    public void testSetFamiliarMemebersFirstBranch()
    {
        Player player = new Player(null, null, false);

        player.setPlayerColorsEnum(PlayerColorsEnum.BLUE);

        List<FamilyMember> familyMembers = new ArrayList<>();

        player.setFamilyMembers(familyMembers);

        player.setFamiliarToPlayer(dices);

        assertEquals(dices.get(0).getNumber(), player.getFamilyMembers().get(0).getValue());

        assertEquals(ColorsEnum.BLACK, player.getFamilyMembers().get(0).getColor());

        assertEquals(PlayerColorsEnum.BLUE, player.getFamilyMembers().get(0).getPlayerColor());

        assertEquals(false, player.getUnusedFamiliarMembers().get(0).isPlayed());

        assertEquals(2, player.getFamilyMembers().size());

        assertEquals(0, player.getFamilyMembers().get(1).getValue());

        assertEquals(ColorsEnum.NEUTER, player.getFamilyMembers().get(1).getColor());

        assertEquals(PlayerColorsEnum.BLUE, player.getFamilyMembers().get(1).getPlayerColor());

        assertEquals(false, player.getFamilyMembers().get(1).isPlayed());

        player.setFamiliarToPlayer(dices);

        assertEquals(0, player.getFamilyMembers().get(1).getValue());

        assertEquals(dices.get(0).getNumber(), player.getFamilyMembers().get(0).getValue());

        assertEquals(false, player.getUnusedFamiliarMembers().get(0).isPlayed());

        assertEquals(false, player.getFamilyMembers().get(1).isPlayed());

    }

    @Test
    public void testAddAsset() {
        Player player = new Player(null, null, false);

        player.setCoins(0);

        player.setFaithPoints(0);

        player.setVictoryPoints(0);

        player.setMilitaryPoints(0);

        player.setServants(0);

        player.setStones(0);

        player.setWoods(0);

        player.addAsset(new Asset(1, AssetType.COIN));

        player.addAsset(new Asset(1, AssetType.WOOD));

        player.addAsset(new Asset(1, AssetType.STONE));

        player.addAsset(new Asset(1, AssetType.SERVANT));

        player.addAsset(new Asset(1, AssetType.FAITHPOINT));

        player.addAsset(new Asset(1, AssetType.MILITARYPOINT));

        player.addAsset(new Asset(1, AssetType.VICTORYPOINT));

        assertEquals(1, player.getCoins());

        assertEquals(1, player.getServants());

        assertEquals(1, player.getStones());

        assertEquals(1, player.getWoods());

        assertEquals(1, player.getFaithPoints());

        assertEquals(1, player.getMilitaryPoints());

        assertEquals(1, player.getVictoryPoints());

        player.addAsset(new Asset(-4, AssetType.COIN));

        player.addAsset(new Asset(-4, AssetType.WOOD));

        player.addAsset(new Asset(-4, AssetType.STONE));

        player.addAsset(new Asset(-4, AssetType.SERVANT));

        player.addAsset(new Asset(-4, AssetType.FAITHPOINT));

        player.addAsset(new Asset(-4, AssetType.MILITARYPOINT));

        player.addAsset(new Asset(-4, AssetType.VICTORYPOINT));

        assertEquals(0, player.getCoins());

        assertEquals(0, player.getServants());

        assertEquals(0, player.getStones());

        assertEquals(0, player.getWoods());

        assertEquals(0, player.getFaithPoints());

        assertEquals(0, player.getMilitaryPoints());

        assertEquals(0, player.getVictoryPoints());
    }

}
