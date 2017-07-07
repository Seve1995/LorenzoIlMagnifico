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

public class PickTwoCouncilPrivilege extends PickCouncilPrivilege implements Effect
{
	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) 
	{
		return true;
	}

	@Override
	public boolean executeEffects(Player player, GameBoard gameBoard)
	{
		IOAdapter adapter = player.getAdapter();

		GameMatch gameMatch = GameServer.getGameMatchMap().get(gameBoard.getGameMatchName());

		gameMatch.setCurrEffect(this);

		adapter.printMessage(new PickPrivilegeMessage(2));

		if (adapter instanceof SocketIOAdapter)
			new Thread(new ReceiveCouncilDecisionThread(2, gameBoard.getGameMatchName())).start();

		return super.waitForResult(player);
	}

	@Override
	public String toString() {
		return "Pick Two Council Privilege";
	}
}
