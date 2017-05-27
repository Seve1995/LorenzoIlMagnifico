package it.polimi.ingsw.pc22.client;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReceiveThread implements Runnable
{
	private Socket socket=null;
	private BufferedReader inSocket=null;
	
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
				if (!inSocket.ready()) continue;

				String msgReceived = inSocket.readLine();

				System.out.println("From Server: " + msgReceived);

				if(!msgReceived.equalsIgnoreCase("EXIT")) continue;

				System.out.println("Shutting down input");

				socket.close();

				break;

			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}

