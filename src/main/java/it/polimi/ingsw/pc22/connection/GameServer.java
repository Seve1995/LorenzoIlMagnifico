package it.polimi.ingsw.pc22.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameServer 
{
	private static Map<String, GameMatch> gameMatchMap;
	
	public static void main(String[] args)
	{
		System.out.println("Inizializzazione");
		
		gameMatchMap = new ConcurrentHashMap<>();
		
		ServerSocket serverSocket;
		
		try 
		{
			serverSocket = new ServerSocket(9001);
			
			while(true)
			{
				Socket socket = serverSocket.accept();
				
				ConnectionHandler handler = new ConnectionHandler();
				
				handler.setGameMatchMap(gameMatchMap);
				handler.setSocket(socket);
				
				new Thread(handler).start();
			}
			
		} 
			catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		
	}

}
