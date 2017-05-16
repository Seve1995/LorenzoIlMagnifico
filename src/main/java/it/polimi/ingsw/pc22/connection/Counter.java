package it.polimi.ingsw.pc22.connection;

import java.util.concurrent.Callable;

public class Counter implements Callable<Boolean>
{
	private GameMatch gameMatch;
	
	public Counter(GameMatch gameMatch)
	{
		this.gameMatch = gameMatch;
	}
	
	@Override
	public Boolean call() throws Exception 
	{
		while(true)
		{
			if (gameMatch.getPlayerCounter() == 4) 
			{
				System.out.println("inizio Partita");
				
				return true;
			}
			
			if (Thread.currentThread().isInterrupted()) return true;
		}
			
	}

	public GameMatch getGameMatch() 
	{
		return gameMatch;
	}
}
