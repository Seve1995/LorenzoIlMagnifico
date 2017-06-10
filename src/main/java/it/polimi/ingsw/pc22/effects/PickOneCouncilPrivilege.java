package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

public class PickOneCouncilPrivilege implements Effect{

	private List<Asset> choosenAsset;

	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) {
		
		return true;
	}

	@Override
	public boolean executeEffects(Player player, GameBoard gameBoard)
	{
		IOAdapter adapter = player.getAdapter();

		choosenAsset = adapter.chooseOneAsset();

		if (choosenAsset.isEmpty()) return false;

		for (Asset asset : choosenAsset)
			player.addAsset(asset);

		return true;
	}

	@Override
	public String toString() {
		return "Pick One Council Privilege";
	}
	
	
	
}
