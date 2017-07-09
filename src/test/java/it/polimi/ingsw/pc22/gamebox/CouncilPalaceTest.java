package it.polimi.ingsw.pc22.gamebox;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.pc22.effects.AddAsset;
import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.player.Player;
import junit.framework.TestCase;

public class CouncilPalaceTest extends TestCase{

	private CouncilPalaceCell[] councilPalaceCells;
	
	private List<Player> playersInCouncilPalace;
	
	@Before
	public void setUp()
	{
		playersInCouncilPalace = new ArrayList<>();
		
		councilPalaceCells = new CouncilPalaceCell[16];
		
		List<Effect> effects = new ArrayList<>();
		
		AddAsset addAsset = new AddAsset();
		
		addAsset.setAsset(new Asset(2, AssetType.COIN));
		
		effects.add(addAsset);
		
		councilPalaceCells[0] = new CouncilPalaceCell(1, effects);
		
		councilPalaceCells[1] = new CouncilPalaceCell(1, effects);

	}
	
	@Test
	public void testGetSet()
	{
		CouncilPalace councilPalace = new CouncilPalace();
		
		councilPalace.setCouncilPalaceCells(councilPalaceCells);
		
		assertEquals(councilPalaceCells, councilPalace.getCouncilPalaceCells());
		
		playersInCouncilPalace.add(new Player("Test", "Test", true));
		
		councilPalace.setPlayersInCouncilPalace(playersInCouncilPalace);
		
		assertEquals(playersInCouncilPalace, councilPalace.getPlayersInCouncilPalace());
	}
	
	@Test
	public void testFirstCellFree()
	{
		CouncilPalace councilPalace = new CouncilPalace();
		
		councilPalace.setCouncilPalaceCells(councilPalaceCells);

		assertEquals(0, councilPalace.firstCellFree());
		
		councilPalace.getCouncilPalaceCells()[0].setFamilyMember(new FamilyMember());
		
		assertEquals(1, councilPalace.firstCellFree());
	}
	
}
