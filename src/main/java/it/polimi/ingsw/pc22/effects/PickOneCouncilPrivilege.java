package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.messages.PickPrivilegeMessage;
import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

public class PickOneCouncilPrivilege implements Effect{

	private List<Asset> chosenAsset;

	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) {
		
		return true;
	}

	@Override
	public boolean executeEffects(Player player, GameBoard gameBoard)
	{
		IOAdapter adapter = player.getAdapter();

		adapter.printMessage(new PickPrivilegeMessage(1));

		chosenAsset = adapter.chooseCouncilPrivileges(1);

		if (chosenAsset==null) return false;

		for (Asset asset : chosenAsset)
			player.addAsset(asset);

		return true;
	}

	@Override
	public String toString() {
		return "Pick One Council Privilege";
	}
	
	
	
}
