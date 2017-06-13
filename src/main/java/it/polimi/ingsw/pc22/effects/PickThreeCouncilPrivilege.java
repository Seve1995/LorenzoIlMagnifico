package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

public class PickThreeCouncilPrivilege implements Effect
{

	private List<Asset> chosenThreeAssets;

	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) 
	{
		return true;
	}

	@Override
	public boolean executeEffects(Player player, GameBoard gameBoard)
	{
		IOAdapter adapter = player.getAdapter();

		chosenThreeAssets = adapter.chooseCouncilPrivileges(3);

		if (chosenThreeAssets==null) return false;

		for (Asset asset : chosenThreeAssets)
			player.addAsset(asset);

		return true;
	}
	
	@Override
	public String toString() {
		return "Pick Three Council Privilege";
	}
}
