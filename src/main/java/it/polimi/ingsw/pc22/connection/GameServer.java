package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.effects.Effect;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameServer 
{
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

		//test();
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

	private static void test()
	{
		try {
			Class effectName = Class.forName("it.polimi.ingsw.pc22.effects.PickCouncilPrivilege");

			Effect effect = (Effect) effectName.newInstance();

			System.out.println(effect);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
