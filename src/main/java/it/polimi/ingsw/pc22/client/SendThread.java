package it.polimi.ingsw.pc22.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SendThread implements Runnable
{	
	private Socket socket = null;
	private PrintWriter outSocket = null;
	private BufferedReader inKeyboard = null;

	private boolean printed = false;

	private static final Logger LOGGER = Logger.getLogger(SendThread.class.getName());
	
	public SendThread(Socket socket)
	{
		this.socket = socket;
	}
	
	public void run()
	{
		try
		{
			inKeyboard = new BufferedReader(new InputStreamReader(System.in));
			outSocket = new PrintWriter(socket.getOutputStream(), true);

			while(true)
			{
				if (Client.isStateChanged())
				{
					Client.getGenericState().printState();

					Client.setStateChanged(false);
				}

				if (socket.isClosed()) break;

				if (!inKeyboard.ready())
				{
					Thread.sleep(100);

					continue;
				}

				String msgToServer = inKeyboard.readLine();

				if (!Client.getGenericState().validate(msgToServer))
					continue;

				outSocket.println(msgToServer);
			}

			System.out.println("Shutting down output");

			inKeyboard.close();
			outSocket.close();
		}
			catch(Exception e)
		{
			LOGGER.log(Level.INFO, "ERROR RECEIVE THREAD", e);
		}
	}

}

