package it.polimi.ingsw.pc22.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewThread implements Runnable
{
	private BufferedReader inKeyboard = null;

	private static final Logger LOGGER = Logger.getLogger(ViewThread.class.getName());

	public void run()
	{
		try
		{
			inKeyboard = new BufferedReader(new InputStreamReader(System.in));

			while(true)
			{
				if (Client.isStopped()) break;

				if (Client.isStateChanged())
				{
					Client.getGenericState().printState();

					Client.setStateChanged(false);
				}

				if (!inKeyboard.ready())
				{
					Thread.sleep(100);

					continue;
				}

				String msgToServer = inKeyboard.readLine();

				if (!Client.getGenericState().validate(msgToServer))
					continue;

				Client.getGenericState().sendToServer(msgToServer);
			}

			System.out.println("Shutting down output");

			inKeyboard.close();
		}
			catch(Exception e)
		{
			LOGGER.log(Level.INFO, "ERROR RECEIVE THREAD", e);
		}
	}

}

