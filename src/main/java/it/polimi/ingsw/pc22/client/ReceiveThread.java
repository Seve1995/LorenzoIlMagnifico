package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.messages.CommunicationMessage;
import it.polimi.ingsw.pc22.messages.Message;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceiveThread implements Runnable
{
	private Socket socket=null;
	private ObjectInputStream inSocket=null;
	
	private static final Logger LOGGER = Logger.getLogger(ReceiveThread.class.getName());
	
	public ReceiveThread(Socket socket) {
		this.socket = socket;
	}

	public void run() 
	{
		try
		{
			inSocket = new ObjectInputStream(this.socket.getInputStream());

			while(true)
			{
				Message message = (Message) inSocket.readObject();

				if (message == null)
				{
					Thread.sleep(100);

					continue;
				}

				if (message instanceof CommunicationMessage)
				{
					String messageString = ((CommunicationMessage) message).getMessage();

					if ("EXIT".equals(messageString))
						break;
				}

				MessageHandler.handleMessage(message);
			}
		}
		catch(Exception e)
		{
			LOGGER.log(Level.INFO, "ERROR RECEIVE THREAD", e);
		}
	}
}

