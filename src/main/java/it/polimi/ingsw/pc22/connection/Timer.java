package it.polimi.ingsw.pc22.connection;
import java.util.concurrent.Callable;

public class Timer implements Callable<String>
{
	@Override
	public String call() throws Exception 
	{
		Thread.sleep(100000);
		
		return null;
	}
}
