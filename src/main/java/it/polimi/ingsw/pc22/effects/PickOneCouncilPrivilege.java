package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.adapters.SocketIOAdapter;
import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.connection.GameServer;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.messages.PickPrivilegeMessage;
import it.polimi.ingsw.pc22.player.Player;

/**
 * Please refer to "PickCouncilPrivilege"
 *
 */

public class PickOneCouncilPrivilege extends PickCouncilPrivilege implements Effect
{
	@Override
	public boolean isLegal(Player player, GameBoard gameBoard)
	{
		return true;
	}

	@Override
	public boolean executeEffects(Player player, GameBoard gameBoard)
	{
		String gameName = gameBoard.getGameMatchName();

		GameMatch gameMatch = GameServer.getGameMatchMap().get(gameName);

		gameMatch.setCurrEffect(this);

		IOAdapter adapter = player.getAdapter();

		adapter.printMessage(new PickPrivilegeMessage(1));

		if (adapter instanceof SocketIOAdapter)
			new Thread(new ReceiveCouncilDecisionThread(1, gameName)).start();

		System.out.println("Executed");

		return super.waitForResult(player);
	}

	@Override
	public String toString() {
		return "Pick One Council Privilege";
	}


}
