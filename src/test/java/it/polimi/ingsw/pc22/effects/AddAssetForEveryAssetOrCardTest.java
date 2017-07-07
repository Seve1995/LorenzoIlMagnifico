package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.player.Player;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddAssetForEveryAssetOrCardTest extends TestCase {
	
	private GameBoard gameBoard;

    private Player player;

	private Asset paidAsset;
	
	private CardTypeEnum paidCardType;
	
	private Asset gainedAsset;
	
	@Before
	public void setUp()
	{
		gameBoard = mock(GameBoard.class);
		
		player = mock(Player.class);
		
		paidAsset = new Asset(2, AssetType.COIN);
		
		gainedAsset = new Asset(1, AssetType.SERVANT);
		
	}
	
	@Test
	public void testGetSet()
	{
		AddAssetForEveryAssetOrCard addAssetForEveryAssetOrCard = new AddAssetForEveryAssetOrCard();
		
		addAssetForEveryAssetOrCard.setGainedAsset(gainedAsset);
		
		addAssetForEveryAssetOrCard.setPaidAsset(paidAsset);
		
		addAssetForEveryAssetOrCard.setPaidCardType(paidCardType);

		assertEquals(gainedAsset, addAssetForEveryAssetOrCard.getGainedAsset());
		
		assertEquals(paidAsset, addAssetForEveryAssetOrCard.getPaidAsset());
		
		assertEquals(paidCardType, addAssetForEveryAssetOrCard.getPaidCardType());
	}
	
    @Test
    public void testIsLegal()
    {
		AddAssetForEveryAssetOrCard addAssetForEveryAssetOrCard = new AddAssetForEveryAssetOrCard();
		
    	assertTrue(addAssetForEveryAssetOrCard.isLegal(player, gameBoard));
    }
    
    @Test
    public void testExecuteEffectsNullPaidCardTypeAndGainedAsset()
    {
		AddAssetForEveryAssetOrCard addAssetForEveryAssetOrCard = new AddAssetForEveryAssetOrCard();
		
    	paidCardType = null;
    	
    	addAssetForEveryAssetOrCard.setPaidCardType(paidCardType);
    	
    	gainedAsset = null;
    	
    	addAssetForEveryAssetOrCard.setGainedAsset(gainedAsset);
    	
    	assertFalse(addAssetForEveryAssetOrCard.executeEffects(player, gameBoard));
    }
    
    @Test
    public void testExecuteEffectsNullPaidCardType()
    {
		AddAssetForEveryAssetOrCard addAssetForEveryAssetOrCard = new AddAssetForEveryAssetOrCard();
		
    	paidCardType = null;
    	
    	addAssetForEveryAssetOrCard.setPaidCardType(paidCardType);
    	
    	addAssetForEveryAssetOrCard.setGainedAsset(gainedAsset);
    	
    	addAssetForEveryAssetOrCard.setPaidAsset(paidAsset);

    	player.addAsset(new Asset(6, paidAsset.getType()));
    	
    	int totalPlayerPaidAssetBeforeInvocation = player.getAsset(paidAsset.getType());
    	
    	int totalAssetsGained = totalPlayerPaidAssetBeforeInvocation/paidAsset.getValue();
    	
    	int totalPlayerGainedAssetBeforeInvocation = player.getAsset(gainedAsset.getType());

    	addAssetForEveryAssetOrCard.executeEffects(player, gameBoard);
    	    	
    	assertEquals(totalAssetsGained, player.getAsset(gainedAsset.getType())-totalPlayerGainedAssetBeforeInvocation);
    	
    }
    
    @Test
    public void testExecuteEffectsNullGainedAssetForTerritoryCard()
    {
		AddAssetForEveryAssetOrCard addAssetForEveryAssetOrCard = new AddAssetForEveryAssetOrCard();
		
    	addAssetForEveryAssetOrCard.setPaidCardType(CardTypeEnum.TERRITORY);
		
    	addAssetForEveryAssetOrCard.setGainedAsset(gainedAsset);
		
		player = new Player("Test", "Test", true);
		    	
    	PlayerBoard playerBoard = mock(PlayerBoard.class);
    	    	
    	TerritoryCard territoryCard = mock(TerritoryCard.class);
    	
    	List<TerritoryCard> territories = new ArrayList<>();
    	
    	territories.add(territoryCard);
    	
    	territories.add(territoryCard);

    	when(playerBoard.getTerritories()).thenReturn(territories);
    	
    	player.setPlayerBoard(playerBoard);
    	
    	int totalGainedAssetBeforeInvocation = player.getAsset(gainedAsset.getType());
    	
    	addAssetForEveryAssetOrCard.executeEffects(player, gameBoard);
    	
    	assertEquals(player.getAsset(gainedAsset.getType()) - totalGainedAssetBeforeInvocation, gainedAsset.getValue()*player.getPlayerBoard().getTerritories().size());
 	
    }

    @Test
    public void testExecuteEffectsNullGainedAssetForBuildingCard()
    {
		AddAssetForEveryAssetOrCard addAssetForEveryAssetOrCard = new AddAssetForEveryAssetOrCard();
		
    	addAssetForEveryAssetOrCard.setPaidCardType(CardTypeEnum.BUILDING);
		
    	addAssetForEveryAssetOrCard.setGainedAsset(gainedAsset);
		
		player = new Player("Test", "Test", true);
		    	
    	PlayerBoard playerBoard = mock(PlayerBoard.class);
    	    	
    	BuildingCard buildingCard = mock(BuildingCard.class);
    	
    	List<BuildingCard> buildings = new ArrayList<>();
    	
    	buildings.add(buildingCard);
    	
    	buildings.add(buildingCard);

    	when(playerBoard.getBuildings()).thenReturn(buildings);
    	
    	player.setPlayerBoard(playerBoard);
    	
    	int totalGainedAssetBeforeInvocation = player.getAsset(gainedAsset.getType());
    	
    	addAssetForEveryAssetOrCard.executeEffects(player, gameBoard);
    	
    	assertEquals(player.getAsset(gainedAsset.getType()) - totalGainedAssetBeforeInvocation, gainedAsset.getValue()*player.getPlayerBoard().getBuildings().size());
 	
    }
    
    @Test
    public void testExecuteEffectsNullGainedAssetForCharacters()
    {
		AddAssetForEveryAssetOrCard addAssetForEveryAssetOrCard = new AddAssetForEveryAssetOrCard();
		
    	addAssetForEveryAssetOrCard.setPaidCardType(CardTypeEnum.CHARACTER);
		
    	addAssetForEveryAssetOrCard.setGainedAsset(gainedAsset);
		
		player = new Player("Test", "Test", true);
		    	
    	PlayerBoard playerBoard = mock(PlayerBoard.class);
    	    	
    	CharacterCard characterCard = mock(CharacterCard.class);
    	
    	List<CharacterCard> characters = new ArrayList<>();
    	
    	characters.add(characterCard);
    	
    	characters.add(characterCard);

    	when(playerBoard.getCharacters()).thenReturn(characters);
    	
    	player.setPlayerBoard(playerBoard);
    	
    	int totalGainedAssetBeforeInvocation = player.getAsset(gainedAsset.getType());
    	
    	addAssetForEveryAssetOrCard.executeEffects(player, gameBoard);
    	
    	assertEquals(player.getAsset(gainedAsset.getType()) - totalGainedAssetBeforeInvocation, gainedAsset.getValue()*player.getPlayerBoard().getCharacters().size());
 	
    }
    
    @Test
    public void testExecuteEffectsNullGainedAssetForVentures()
    {
		AddAssetForEveryAssetOrCard addAssetForEveryAssetOrCard = new AddAssetForEveryAssetOrCard();
		
    	addAssetForEveryAssetOrCard.setPaidCardType(CardTypeEnum.VENTURE);
		
    	addAssetForEveryAssetOrCard.setGainedAsset(gainedAsset);
		
		player = new Player("Test", "Test", true);
		    	
    	PlayerBoard playerBoard = mock(PlayerBoard.class);
    	    	
    	VentureCard ventureCard = mock(VentureCard.class);
    	
    	List<VentureCard> ventures = new ArrayList<>();
    	
    	ventures.add(ventureCard);
    	
    	ventures.add(ventureCard);

    	when(playerBoard.getVentures()).thenReturn(ventures);
    	
    	player.setPlayerBoard(playerBoard);
    	
    	int totalGainedAssetBeforeInvocation = player.getAsset(gainedAsset.getType());
    	
    	addAssetForEveryAssetOrCard.executeEffects(player, gameBoard);
    	
    	assertEquals(player.getAsset(gainedAsset.getType()) - totalGainedAssetBeforeInvocation, gainedAsset.getValue()*player.getPlayerBoard().getVentures().size());
 	
    }
    
}
