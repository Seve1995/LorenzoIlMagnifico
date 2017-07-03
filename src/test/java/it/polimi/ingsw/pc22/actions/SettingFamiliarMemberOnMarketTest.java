package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.effects.AddAsset;
import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.player.Player;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SettingFamiliarMemberOnMarketTest extends TestCase
{
	private Market market;
	private MarketCell marketCell;
	private GameBoard gameBoard;
	private FamilyMember familyMember;

	private Player player;
	
	@Before
	public void setUp() 
	{
		MockitoAnnotations.initMocks(this);

		Asset coins = new Asset(5, AssetType.COIN);

		AddAsset addCoins = new AddAsset();

		addCoins.setAsset(coins);

		List<Effect> effects = new ArrayList<>();

		effects.add(addCoins);

		List<MarketCell> marketCells = new ArrayList<>();

		marketCell = mock(MarketCell.class);

		marketCells.add(marketCell);

		market = new Market(marketCells);

		gameBoard = new GameBoard();

		gameBoard.setMarket(market);

		familyMember = new FamilyMember();

		player = mock(Player.class);
	}

	@Test
	public void testIsLegalTrue()
	{
		SettingFamiliarMemberOnMarket settingFamiliarMemberOnMarket
				= new SettingFamiliarMemberOnMarket();

		settingFamiliarMemberOnMarket.setZone(0);
		settingFamiliarMemberOnMarket.setFamilyMember(familyMember);

		familyMember.setFamiliarValue(1);

		when(player.isDontCareOccupiedPlaces()).thenReturn(false);

		when(player.isDisableMarket()).thenReturn(false);

		when(marketCell.isEmpty()).thenReturn(true);

		when(marketCell.getRequiredDiceValue()).thenReturn(1);

		when(marketCell.isABlockedCell()).thenReturn(false);

		assertTrue(settingFamiliarMemberOnMarket.isLegal(player, gameBoard));
	}

	@Test
	public void testIsLegalBlockedMarket()
	{
		SettingFamiliarMemberOnMarket settingFamiliarMemberOnMarket
				= new SettingFamiliarMemberOnMarket();

		settingFamiliarMemberOnMarket.setZone(0);
		settingFamiliarMemberOnMarket.setFamilyMember(familyMember);

		when(player.isDisableMarket()).thenReturn(true);

		when(player.isDontCareOccupiedPlaces()).thenReturn(false);

		when(marketCell.isEmpty()).thenReturn(true);

		when(marketCell.getRequiredDiceValue()).thenReturn(1);

		when(marketCell.isABlockedCell()).thenReturn(false);

		assertFalse(settingFamiliarMemberOnMarket.isLegal(player, gameBoard));
	}

	@Test
	public void testIsLegalBlockedCell()
	{
		SettingFamiliarMemberOnMarket settingFamiliarMemberOnMarket
				= new SettingFamiliarMemberOnMarket();

		settingFamiliarMemberOnMarket.setZone(0);
		settingFamiliarMemberOnMarket.setFamilyMember(familyMember);

		when(player.isDisableMarket()).thenReturn(false);

		when(player.isDontCareOccupiedPlaces()).thenReturn(false);

		when(marketCell.isEmpty()).thenReturn(true);

		when(marketCell.getRequiredDiceValue()).thenReturn(1);

		when(marketCell.isABlockedCell()).thenReturn(true);

		assertFalse(settingFamiliarMemberOnMarket.isLegal(player, gameBoard));
	}
}
