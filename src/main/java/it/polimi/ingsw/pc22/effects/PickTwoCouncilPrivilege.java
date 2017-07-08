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
		String gameName = gameBoard.getGameMatchName();

		GameMatch pickTwoMatch = GameServer.getGameMatchMap().get(gameName);

		pickTwoMatch.setCurrEffect(this);

		IOAdapter pickTwoAdapter = player.getAdapter();

		pickTwoAdapter.printMessage(new PickPrivilegeMessage(2));

		if (pickTwoAdapter instanceof SocketIOAdapter)
			new Thread(new ReceiveCouncilDecisionThread(2, gameName)).start();

		System.out.println("Pick One Council Executed");

		return super.waitForResult(player);
	}

	@Override
	public String toString() {
		return "Pick Two Council Privilege";
	}
}
