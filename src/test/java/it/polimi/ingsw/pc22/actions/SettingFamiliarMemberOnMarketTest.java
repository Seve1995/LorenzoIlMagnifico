package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.effects.AddAsset;
import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.gamebox.Market;
import it.polimi.ingsw.pc22.gamebox.MarketCell;
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
	private AddAsset addCoins;
	private MarketCell marketCell;
	private GameBoard gameBoard;
	private FamilyMember familyMember;

	private Player player;
	
	@Before
	public void setUp() 
	{
		MockitoAnnotations.initMocks(this);

		addCoins = mock(AddAsset.class);

		List<Effect> effects = new ArrayList<>();

		effects.add(addCoins);

		List<MarketCell> marketCells = new ArrayList<>();

		marketCell = mock(MarketCell.class);

		marketCells.add(marketCell);

		market = new Market(marketCells);

		gameBoard = new GameBoard();

		gameBoard.setMarket(market);

		familyMember = mock(FamilyMember.class);

		when(familyMember.getValue()).thenReturn(1);

		player = mock(Player.class);
	}

	@Test
	public void testIsLegalTrue()
	{
		SettingFamiliarMemberOnMarket settingFamiliarMemberOnMarket
				= new SettingFamiliarMemberOnMarket();

		settingFamiliarMemberOnMarket.setZone(0);
		settingFamiliarMemberOnMarket.setFamilyMember(familyMember);

		when(player.isDisableMarket()).thenReturn(false);

		when(marketCell.isABlockedCell()).thenReturn(false);

		when(marketCell.getRequiredDiceValue()).thenReturn(1);

		when(marketCell.isEmpty()).thenReturn(true);

		when(player.isDontCareOccupiedPlaces()).thenReturn(false);

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

	@Test
	public void testIsLegalWrongValue()
	{
		SettingFamiliarMemberOnMarket settingFamiliarMemberOnMarket
				= new SettingFamiliarMemberOnMarket();

		settingFamiliarMemberOnMarket.setZone(0);
		settingFamiliarMemberOnMarket.setFamilyMember(familyMember);

		when(marketCell.isABlockedCell()).thenReturn(false);

		when(player.isDisableMarket()).thenReturn(false);

		when(player.isDontCareOccupiedPlaces()).thenReturn(false);

		when(marketCell.isEmpty()).thenReturn(true);

		when(marketCell.getRequiredDiceValue()).thenReturn(1);

		when(familyMember.getValue()).thenReturn(0);

		assertFalse(settingFamiliarMemberOnMarket.isLegal(player, gameBoard));
	}

	@Test
	public void testIsLegalOccupied()
	{
		SettingFamiliarMemberOnMarket settingFamiliarMemberOnMarket
				= new SettingFamiliarMemberOnMarket();

		settingFamiliarMemberOnMarket.setZone(0);
		settingFamiliarMemberOnMarket.setFamilyMember(familyMember);

		when(player.isDisableMarket()).thenReturn(false);

		when(player.isDontCareOccupiedPlaces()).thenReturn(false);

		when(marketCell.isEmpty()).thenReturn(false);

		when(marketCell.getRequiredDiceValue()).thenReturn(1);

		when(marketCell.isABlockedCell()).thenReturn(false);

		assertFalse(settingFamiliarMemberOnMarket.isLegal(player, gameBoard));
	}

	@Test
	public void testExecuteActionTrue()
	{
		SettingFamiliarMemberOnMarket settingFamiliarMemberOnMarket
				= new SettingFamiliarMemberOnMarket();

		Player player = new Player("Test", "test", true);

		FamilyMember familyMember = new FamilyMember();

		settingFamiliarMemberOnMarket.setZone(0);
		settingFamiliarMemberOnMarket.setFamilyMember(familyMember);

		when(settingFamiliarMemberOnMarket.isLegal(player, gameBoard)).thenReturn(true);

		assertTrue(settingFamiliarMemberOnMarket.executeAction(player, gameBoard));

		assertEquals(player.isFamiliarPositioned(), true);

		assertEquals(familyMember.isPlayed(), true);
	}

	@Test
	public void testExecuteActionFalse()
	{
		Player player = new Player("Test", "test", true);

		FamilyMember familyMember = new FamilyMember();

		SettingFamiliarMemberOnMarket settingFamiliarMemberOnMarket
				= new SettingFamiliarMemberOnMarket(familyMember, 0);

		when(settingFamiliarMemberOnMarket.isLegal(player, gameBoard)).thenReturn(false);

		assertFalse(settingFamiliarMemberOnMarket.executeAction(player, gameBoard));

		assertEquals(player.isFamiliarPositioned(), false);

		assertEquals(familyMember.isPlayed(), false);
	}

	@Test
	public void testCostrunctor()
	{

	}
}
