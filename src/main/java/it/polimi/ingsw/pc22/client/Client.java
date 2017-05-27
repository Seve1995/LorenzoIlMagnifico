package it.polimi.ingsw.pc22.client;
import java.net.Socket;

public class Client 
{
	public static void main(String[] args)
	{
		Socket socket;
		
		try 
		{
			System.out.println("Client running. Type 'EXIT' to exit.");

			socket = new Socket("localhost",9001);

			//Start the SendThread
			SendThread sendThread = new SendThread(socket);
			Thread thread = new Thread(sendThread);
			thread.start();

			//Start the ReceiveThread
			ReceiveThread recieveThread = new ReceiveThread(socket);
			Thread thread2 = new Thread(recieveThread);
			thread2.start();
		} 
			catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		
	}

}
