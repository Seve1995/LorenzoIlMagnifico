package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.effects.AddAsset;
import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.effects.PickTwoCouncilPrivilege;
import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.AssetType;
import it.polimi.ingsw.pc22.gamebox.Market;
import it.polimi.ingsw.pc22.gamebox.MarketCell;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SettingFamiliarMemberOnMarketTest extends TestCase{
	private Market market;
	private List<MarketCell> marketCells;
	
	
	@Before
	public void setUp() 
	{
		Asset asset1 = new Asset(5, AssetType.COIN);
		Asset asset2 = new Asset(5, AssetType.SERVANT);
		Asset asset3a = new Asset(3, AssetType.MILITARYPOINT);
		Asset asset3b = new Asset(2, AssetType.COIN);
		AddAsset addAsset1 = new AddAsset();
		AddAsset addAsset2 = new AddAsset();
		AddAsset addAsset3a = new AddAsset();
		AddAsset addAsset3b = new AddAsset();
		addAsset1.setAsset(asset1);
		addAsset2.setAsset(asset2);
		addAsset3a.setAsset(asset3a);
		addAsset3b.setAsset(asset3b);
		Effect effect4 = new PickTwoCouncilPrivilege();
		List<Effect> effects1 = new ArrayList<Effect>();
		List<Effect> effects2 = new ArrayList<Effect>();
		List<Effect> effects3 = new ArrayList<Effect>();
		List<Effect> effects4 = new ArrayList<Effect>();
		effects1.add(addAsset1);
		effects2.add(addAsset2);
		effects3.add(addAsset3a);
		effects3.add(addAsset3b);
		effects4.add(effect4);
		List<MarketCell> marketCells = new ArrayList<MarketCell>();
		marketCells.add(new MarketCell(1, effects1));
		marketCells.add(new MarketCell(1, effects2));
		marketCells.add(new MarketCell(1, effects3));
		marketCells.add(new MarketCell(1, effects4));
		marketCells.get(2).setABlockedCell(true);
		marketCells.get(3).setABlockedCell(true);
		market = new Market(marketCells);
		
	}

	@Test
	public void testIsValid()
	{

	}
}
