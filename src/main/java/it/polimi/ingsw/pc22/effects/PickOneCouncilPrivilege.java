package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.adapters.SocketIOAdapter;
import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.messages.PickPrivilegeMessage;
import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

public class PickOneCouncilPrivilege implements Effect
{
	private List<Asset> chosenAsset = null;

	@Override
	public boolean isLegal(Player player, GameBoard gameBoard)
	{
		return true;
	}

	@Override
	public boolean executeEffects(Player player, GameBoard gameBoard)
	{
		GameMatch.getCurrentGameBoard().setCurreEffect(this);

		IOAdapter adapter = player.getAdapter();

		adapter.printMessage(new PickPrivilegeMessage(1));

		if (adapter instanceof SocketIOAdapter)
			new Thread(new ReceiceCouncilDecisionThread(1)).start();

		chosenAsset = adapter.chooseCouncilPrivileges(1);

		Long timestamp = System.currentTimeMillis();

		Long timeout = GameMatch.getTimeout();

		while (System.currentTimeMillis() < timestamp + timeout)
		{
			try
			{
				Thread.sleep(100L);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			if (chosenAsset != null)
			{
				System.out.println(chosenAsset);

				for (Asset asset : chosenAsset)
					player.addAsset(asset);

				return true;
			}
		}

		return false;

	}

	public void setChosenAsset(List<Asset> chosenAsset)
	{
		this.chosenAsset = chosenAsset;
	}

	@Override
	public String toString() {
		return "Pick One Council Privilege";
	}
	
	
	
}
