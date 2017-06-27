package it.polimi.ingsw.pc22.states;

/**
 * Created by fandroid95 on 22/06/2017.
 */
public class IdleState implements GenericState
{
	@Override
	public void printState()
	{
		System.out.println("Please wait your turn");
	}

	@Override
	public boolean validate(String string)
	{
		return true;
	}
}
