package it.polimi.ingsw.pc22.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameServer 
{
	private static Map<String, GameMatch> gameMatchMap;
	private static final int PORT = 9001;

	public static void main(String[] args)
	{
		System.out.println("Inizializzazione");
		
		gameMatchMap = new ConcurrentHashMap<>();
		
		ServerSocket serverSocket;
		
		try 
		{
			serverSocket = new ServerSocket(PORT);
			AuthenticationHandler.loadJSon();
			while(true)
			{
				Socket socket = serverSocket.accept();
				
				AuthenticationHandler handler = 
						new AuthenticationHandler(socket, gameMatchMap);
				
				new Thread(handler).start();
			}
			
		} 
			catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		
	}

}
