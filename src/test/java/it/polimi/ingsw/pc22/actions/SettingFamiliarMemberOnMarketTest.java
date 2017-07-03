package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.effects.AddAsset;
import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.states.PlayState;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SettingFamiliarMemberOnMarketTest
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
	public void testIsValid()
	{
		SettingFamiliarMemberOnMarket settingFamiliarMemberOnMarket
				= new SettingFamiliarMemberOnMarket();

		settingFamiliarMemberOnMarket.setZone(0);
		settingFamiliarMemberOnMarket.setFamilyMember(familyMember);

		familyMember.setFamiliarValue(1);

		when(player.isDontCareOccupiedPlaces()).thenReturn(true);

		when(player.isDisableMarket()).thenReturn(false);

		when(marketCell.isEmpty()).thenReturn(false);

		when(marketCell.getRequiredDiceValue()).thenReturn(1);

		assertTrue(settingFamiliarMemberOnMarket.isLegal(player, gameBoard));
	}
}
