package it.polimi.ingsw.pc22.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameServer 
{
	//TODO: @FABIO: Meglio dichiararla nell'AuthenticationHandler come fatto con la userList e fare da gameServer il caricamento con una chiamate a un metodo statico?
	private static Map<String, GameMatch> gameMatchMap;
	private static final int PORT = 9001;
	private static List<User> usersList; 

	public static void main(String[] args)
	{
		System.out.println("Server online");
		
		gameMatchMap = new ConcurrentHashMap<>();
		
		ServerSocket serverSocket;
		
		try 
		{
			serverSocket = new ServerSocket(PORT);
			
			usersList = loadJSon();
			
			while(true)
			{
				Socket socket = serverSocket.accept();
				
				AuthenticationHandler handler = new AuthenticationHandler(socket);
				
				new Thread(handler).start();
			}
			
		} 
			catch (IOException e) 
		{
			e.printStackTrace();
		}	
	}
	
	protected static List<User> loadJSon() throws IOException
	{
		List<User> usersList = JsonManager.returnList();
		
		return usersList; 
	}

	public static Map<String, GameMatch> getGameMatchMap() 
	{
		return gameMatchMap;
	}

	public static void setGameMatchMap(Map<String, GameMatch> gameMatchMap) 
	{
		GameServer.gameMatchMap = gameMatchMap;
	}

	public static List<User> getUsersList() 
	{
		return usersList;
	}

	public static void setUsersList(List<User> usersList) 
	{
		GameServer.usersList = usersList;
	}
}
