package it.polimi.ingsw.pc22.states;

import it.polimi.ingsw.pc22.client.Client;

/**
 * Created by fandroid95 on 22/06/2017.
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
