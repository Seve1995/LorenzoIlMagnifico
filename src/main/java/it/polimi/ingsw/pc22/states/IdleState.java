package it.polimi.ingsw.pc22.states;

import it.polimi.ingsw.pc22.client.Client;

/**
 * This is the state where the player is whe it is not his/her turn.
 * He/she can only type a command to show the two boards (only CLI).
 */
public class IdleState implements GenericState
{
	@Override
	public void printState()
	{
		System.out.println("Please wait your turn");

		System.out.println("Type 'show board' to see your player board");
	}

	@Override
	public boolean validate(String string)
	{
		if ("show board".equals(string))
			return true;

		return true;
	}

	@Override
	public void sendToServer(String string)
	{
		if ("show board".equals(string))
		{
			System.out.println(Client.getPlayer().getPlayerBoard().toString());

			return;
		}
	}
}
