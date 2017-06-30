package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.adapters.SocketIOAdapter;
import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.messages.PickPrivilegeMessage;
import it.polimi.ingsw.pc22.player.Player;

public class PickThreeCouncilPrivilege extends PickCouncilPrivilege implements Effect
{
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

		adapter.printMessage(new PickPrivilegeMessage(3));

		if (adapter instanceof SocketIOAdapter)
			new Thread(new ReceiveCouncilDecisionThread(3)).start();

		return super.waitForResult(player);
	}
	
	@Override
	public String toString() {
		return "Pick Three Council Privilege";
	}
}
