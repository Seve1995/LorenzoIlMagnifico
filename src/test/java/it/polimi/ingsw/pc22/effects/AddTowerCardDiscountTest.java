package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class AddTowerCardDiscountTest extends TestCase{
	
	private GameBoard gameBoard;

    private Player player;

	private CardTypeEnum cardType;
	
	private int diceValueDiscount;
	
	private List<Asset> assetDiscounts; 
	
	private boolean onlyOneAsset;

	@Before
	public void setUp()
	{
		gameBoard = mock(GameBoard.class);
		
		player = mock(Player.class);
		
		cardType = CardTypeEnum.BUILDING;
		
		diceValueDiscount = 2;
		
		assetDiscounts = new ArrayList<>();
		
		onlyOneAsset = false;
				
	}
	
	@Test
	public void testGetSet()
	{
		AddTowerCardDiscount addTowerCardDiscount = new AddTowerCardDiscount();
		
		addTowerCardDiscount.setCardType(cardType);
		
		addTowerCardDiscount.setDiceValueDiscount(diceValueDiscount);
		
		addTowerCardDiscount.setDiscounts(assetDiscounts);
		
		addTowerCardDiscount.setOnlyOneAsset(onlyOneAsset);
		
		assertEquals(cardType, addTowerCardDiscount.getCardType());
		
		assertEquals(diceValueDiscount, addTowerCardDiscount.getDiceValueDiscount());
		
		assertEquals(assetDiscounts, addTowerCardDiscount.getDiscounts());
		
		assertEquals(onlyOneAsset, addTowerCardDiscount.isOnlyOneAsset());

	}
	
	@Test
	public void testIsLegal()
	{
		AddTowerCardDiscount addTowerCardDiscount = new AddTowerCardDiscount();

		assertTrue(addTowerCardDiscount.isLegal(player, gameBoard));
	}
	
	@Test
	public void testExecuteEffect()
	{
		AddTowerCardDiscount addTowerCardDiscount = new AddTowerCardDiscount();
		
		addTowerCardDiscount.setCardType(cardType);
		
		addTowerCardDiscount.setDiceValueDiscount(diceValueDiscount);
		
		addTowerCardDiscount.setDiscounts(assetDiscounts);
		
		addTowerCardDiscount.setOnlyOneAsset(onlyOneAsset);

		Player player = new Player("Test", "test", false);
		
		player.setCardModifiers(new ArrayList<>());
		
		addTowerCardDiscount.executeEffects(player, gameBoard);
		
		assertEquals(cardType, player.getCardModifiers().get(0).getCardType());
		
		assertEquals(diceValueDiscount, player.getCardModifiers().get(0).getValueModifier());

		assertEquals(assetDiscounts, player.getCardModifiers().get(0).getAssetDiscount());

		assertEquals(onlyOneAsset, player.getCardModifiers().get(0).isOnlyOneAsset());

	}

	
}
