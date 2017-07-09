package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.effects.AddAsset;
import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.effects.PickTowerCard;
import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by fandroid95 on 08/07/2017.
 */
public class SettingFamiliarOnTowerTest
{
    private Tower[] towers;

    private Tower tower;

    private List<TowerCell> towerCells;

    private List<Effect> effects;

    private FamilyMember familyMember;

    private GameBoard gameBoard;

    private List<PlayerColorsEnum> players;

    private Player player;

    private PickTowerCard pickTowerCard;

    private TowerCell cell;

    @Before
    public void setUp()
    {
        gameBoard = mock(GameBoard.class);

        familyMember = mock(FamilyMember.class);

        towers = new Tower[1];

        tower = mock(Tower.class);

        towers[0] = tower;

        towerCells = new ArrayList<>();

        cell = mock(TowerCell.class);

        towerCells.add(cell);

        players = new ArrayList<>();

        players.add(PlayerColorsEnum.BLUE);

        when(tower.getTowerCells()).thenReturn(towerCells);

        player = mock(Player.class);

        pickTowerCard = mock(PickTowerCard.class);

        effects = new ArrayList<>();

        AddAsset addAsset = new AddAsset();

        Asset asset = new Asset(2, AssetType.SERVANT);

        addAsset.setAsset(asset);

        effects.add(addAsset);
    }

    @Test
    public void testNotLegal()
    {
        when(tower.getTowerType()).thenReturn(CardTypeEnum.BUILDING);

        SettingFamiliarMemberOnTower settingTower
                = new SettingFamiliarMemberOnTower();

        settingTower.setFloor(0);

        settingTower.setCardTypeEnum(CardTypeEnum.CHARACTER);

        settingTower.setFamilyMember(familyMember);

        when(familyMember.getValue()).thenReturn(1);

        when(gameBoard.getTowers()).thenReturn(towers);

        assertFalse(settingTower.isLegal(player, gameBoard));
    }

    @Test
    public void testNotLegalTowerCell()
    {
        when(tower.getTowerType()).thenReturn(CardTypeEnum.BUILDING);

        SettingFamiliarMemberOnTower settingTower
                = new SettingFamiliarMemberOnTower();

        settingTower.setFloor(0);

        settingTower.setCardTypeEnum(CardTypeEnum.BUILDING);

        settingTower.setFamilyMember(familyMember);

        when(familyMember.getValue()).thenReturn(1);

        when(gameBoard.getTowers()).thenReturn(towers);

        when(player.isDontCareOccupiedPlaces()).thenReturn(false);

        assertFalse(settingTower.isLegal(player, gameBoard));
    }

    @Test
    public void testNotLegalTowerCellWrongValue()
    {
        when(tower.getTowerType()).thenReturn(CardTypeEnum.BUILDING);

        SettingFamiliarMemberOnTower settingTower
                = new SettingFamiliarMemberOnTower();

        settingTower.setFloor(0);

        settingTower.setCardTypeEnum(CardTypeEnum.BUILDING);

        settingTower.setFamilyMember(familyMember);

        when(familyMember.getValue()).thenReturn(1);

        when(gameBoard.getTowers()).thenReturn(towers);

        when(player.isDontCareOccupiedPlaces()).thenReturn(false);

        when(cell.isEmpty()).thenReturn(true);

        when(cell.getRequiredDiceValue()).thenReturn(2);

        assertFalse(settingTower.isLegal(player, gameBoard));
    }

    @Test
    public void testNotLegalTowerCellWrongFamiliar()
    {
        when(tower.getTowerType()).thenReturn(CardTypeEnum.BUILDING);

        SettingFamiliarMemberOnTower settingTower
                = new SettingFamiliarMemberOnTower();

        settingTower.setFloor(0);

        settingTower.setCardTypeEnum(CardTypeEnum.BUILDING);

        settingTower.setFamilyMember(familyMember);

        when(familyMember.getValue()).thenReturn(1);

        when(gameBoard.getTowers()).thenReturn(towers);

        when(player.isDontCareOccupiedPlaces()).thenReturn(true);

        when(cell.isEmpty()).thenReturn(true);

        when(cell.getRequiredDiceValue()).thenReturn(1);

        when(tower.getListPlayers()).thenReturn(players);

        when(player.getPlayerColorsEnum()).thenReturn(PlayerColorsEnum.BLUE);

        when(familyMember.getColor()).thenReturn(ColorsEnum.BLACK);

        assertFalse(settingTower.isLegal(player, gameBoard));
    }

    @Test
    public void testNotLegalTowerCellWrongPay()
    {
        when(tower.getTowerType()).thenReturn(CardTypeEnum.BUILDING);

        SettingFamiliarMemberOnTower settingTower
                = new SettingFamiliarMemberOnTower();

        settingTower.setFloor(0);

        settingTower.setCardTypeEnum(CardTypeEnum.BUILDING);

        settingTower.setFamilyMember(familyMember);

        when(familyMember.getValue()).thenReturn(1);

        when(gameBoard.getTowers()).thenReturn(towers);

        when(player.isDontCareOccupiedPlaces()).thenReturn(true);

        when(cell.getRequiredDiceValue()).thenReturn(1);

        when(tower.getListPlayers()).thenReturn(players);

        when(player.getPlayerColorsEnum()).thenReturn(PlayerColorsEnum.BLUE);

        when(familyMember.getColor()).thenReturn(ColorsEnum.NEUTER);

        when(player.getCoins()).thenReturn(2);

        when(cell.isEmpty()).thenReturn(false);

        assertFalse(settingTower.isLegal(player, gameBoard));
    }

    @Test
    public void testNotLegalTowerCellWrongNotLegal()
    {
        when(tower.getTowerType()).thenReturn(CardTypeEnum.BUILDING);

        SettingFamiliarMemberOnTower settingTower
                = new SettingFamiliarMemberOnTower();

        settingTower.setFloor(0);

        settingTower.setPickTowerCard(pickTowerCard);

        settingTower.setCardTypeEnum(CardTypeEnum.BUILDING);

        settingTower.setFamilyMember(familyMember);

        when(familyMember.getValue()).thenReturn(1);

        when(gameBoard.getTowers()).thenReturn(towers);

        when(player.isDontCareOccupiedPlaces()).thenReturn(true);

        when(cell.getRequiredDiceValue()).thenReturn(1);

        when(tower.getListPlayers()).thenReturn(players);

        when(player.getPlayerColorsEnum()).thenReturn(PlayerColorsEnum.BLUE);

        when(familyMember.getColor()).thenReturn(ColorsEnum.NEUTER);

        when(player.getCoins()).thenReturn(4);

        when(cell.isEmpty()).thenReturn(false);

        assertTrue(settingTower.isLegal(player, gameBoard));
    }

    @Test
    public void testLegal()
    {
        when(tower.getTowerType()).thenReturn(CardTypeEnum.BUILDING);

        SettingFamiliarMemberOnTower settingTower
                = new SettingFamiliarMemberOnTower
                (familyMember, CardTypeEnum.BUILDING, 0);

        settingTower.setPickTowerCard(pickTowerCard);

        settingTower.setCardTypeEnum(CardTypeEnum.BUILDING);

        settingTower.setFamilyMember(familyMember);

        when(familyMember.getValue()).thenReturn(1);

        when(gameBoard.getTowers()).thenReturn(towers);

        when(player.isDontCareOccupiedPlaces()).thenReturn(true);

        when(cell.getRequiredDiceValue()).thenReturn(1);

        when(tower.getListPlayers()).thenReturn(new ArrayList<>());

        when(player.getPlayerColorsEnum()).thenReturn(PlayerColorsEnum.RED);

        when(familyMember.getColor()).thenReturn(ColorsEnum.NEUTER);

        when(player.getCoins()).thenReturn(4);

        when(cell.isEmpty()).thenReturn(false);

        when(pickTowerCard.isLegal(player, gameBoard)).thenReturn(true);

        assertTrue(settingTower.isLegal(player, gameBoard));

        when(tower.getListPlayers()).thenReturn(players);

        when(cell.isEmpty()).thenReturn(true);

        assertTrue(settingTower.isLegal(player, gameBoard));
    }

    @Test
    public void testNotExecuted()
    {
        when(tower.getTowerType()).thenReturn(CardTypeEnum.BUILDING);

        SettingFamiliarMemberOnTower settingTower
                = new SettingFamiliarMemberOnTower();

        settingTower.setFloor(0);

        when(gameBoard.getTowers()).thenReturn(towers);

        settingTower.setCardTypeEnum(CardTypeEnum.CHARACTER);

        settingTower.setFamilyMember(familyMember);

        assertFalse(settingTower.executeAction(player, gameBoard));

        when(familyMember.getValue()).thenReturn(0);

        settingTower.setCardTypeEnum(CardTypeEnum.BUILDING);

        assertFalse(settingTower.executeAction(player, gameBoard));
    }

    @Test
    public void testExecuted()
    {
        when(tower.getTowerType()).thenReturn(CardTypeEnum.BUILDING);

        SettingFamiliarMemberOnTower settingTower
                = new SettingFamiliarMemberOnTower
                (familyMember, CardTypeEnum.BUILDING, 0);

        settingTower.setPickTowerCard(pickTowerCard);

        when(familyMember.getValue()).thenReturn(1);

        when(gameBoard.getTowers()).thenReturn(towers);

        when(player.isDontCareOccupiedPlaces()).thenReturn(true);

        when(cell.getRequiredDiceValue()).thenReturn(1);

        when(tower.getListPlayers()).thenReturn(new ArrayList<>());

        when(player.getPlayerColorsEnum()).thenReturn(PlayerColorsEnum.RED);

        when(familyMember.getColor()).thenReturn(ColorsEnum.NEUTER);

        when(player.getCoins()).thenReturn(4);

        when(cell.isEmpty()).thenReturn(false);

        when(tower.getListPlayers()).thenReturn(players);

        when(pickTowerCard.executeEffects(player, gameBoard)).thenReturn(false);

        when(pickTowerCard.isLegal(player, gameBoard)).thenReturn(true);

        assertFalse(settingTower.executeAction(player, gameBoard));
    }

    @Test
    public void testExecutedTrue()
    {
        when(tower.getTowerType()).thenReturn(CardTypeEnum.BUILDING);

        SettingFamiliarMemberOnTower settingTower
                = new SettingFamiliarMemberOnTower
                (familyMember, CardTypeEnum.BUILDING, 0);

        settingTower.setPickTowerCard(pickTowerCard);

        when(familyMember.getValue()).thenReturn(1);

        when(gameBoard.getTowers()).thenReturn(towers);

        when(player.isDontCareOccupiedPlaces()).thenReturn(true);

        when(cell.getRequiredDiceValue()).thenReturn(1);

        when(tower.getListPlayers()).thenReturn(new ArrayList<>());

        when(player.getPlayerColorsEnum()).thenReturn(PlayerColorsEnum.RED);

        when(familyMember.getColor()).thenReturn(ColorsEnum.BLACK);

        when(player.getCoins()).thenReturn(4);

        when(cell.isEmpty()).thenReturn(false);

        when(tower.getListPlayers()).thenReturn(players);

        when(pickTowerCard.executeEffects(player, gameBoard)).thenReturn(true);

        when(pickTowerCard.isLegal(player, gameBoard)).thenReturn(true);

        when(cell.getEffects()).thenReturn(effects);

        assertTrue(settingTower.executeAction(player, gameBoard));
    }

}
