package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.states.StartMatchState;
import it.polimi.ingsw.pc22.states.WaitingState;

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

				System.out.println("Server: " + msgReceived);

				//TODO SISTEMARE STO SCHIFO
				if ("logged".equalsIgnoreCase(msgReceived))
				{
					System.out.println("prova andata");

					Client.setGenericState(new StartMatchState());

					Client.setStateChanged(true);
				}

				if ("started".equalsIgnoreCase(msgReceived))
				{
					System.out.println("prova andata");

					Client.setGenericState(new WaitingState());

					Client.setStateChanged(true);
				}

				if("EXIT".equalsIgnoreCase(msgReceived))
				{
					System.out.println("Shutting down input");

					socket.close();

					break;
				}
			}
		}
		catch(Exception e)
		{
			LOGGER.log(Level.INFO, "ERROR RECEIVE THREAD", e);
		}
	}
}

