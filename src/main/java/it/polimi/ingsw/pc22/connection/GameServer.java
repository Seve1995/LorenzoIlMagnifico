package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.utils.BoardLoader;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
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
		String boardName = "boards/GameBoard-" + 2 + ".json";

		ClassLoader classLoader = GameServer.class.getClassLoader();

		File file = new File(classLoader.getResource(boardName).getFile());

		StringBuilder builder = new StringBuilder();

		try
		{
			Files.lines(file.toPath()).forEach(s -> builder.append(s));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		JSONObject jsonBoard = new JSONObject(builder.toString());

		jsonBoard.toString();

		GameBoard gameBoard = BoardLoader.loadGameBoard(jsonBoard);

		System.out.println(gameBoard);
	}
}
