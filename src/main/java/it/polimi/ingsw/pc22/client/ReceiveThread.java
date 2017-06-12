package it.polimi.ingsw.pc22.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceiveThread implements Runnable
{
	private Socket socket=null;
	private BufferedReader inSocket=null;

	private static final Logger LOGGER = Logger.getLogger(ReceiveThread.class.getName());
	
	public ReceiveThread(Socket socket) {
		this.socket = socket;
	}
	
	public void run() 
	{
		try
		{
			inSocket = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

			while(true)
			{
				if (!inSocket.ready()) Thread.sleep(100);

				String msgReceived = inSocket.readLine();

				System.out.println("From Server: " + msgReceived);

				if(!"EXIT".equalsIgnoreCase(msgReceived)) continue;

				System.out.println("Shutting down input");

				socket.close();

				break;

			}
		}
		catch(Exception e)
		{
			LOGGER.log(Level.INFO, "ERROR RECEIVE THREAD", e);
		}
	}
}

