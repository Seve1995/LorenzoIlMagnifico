package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.rmi.RMIAuthenticationService;
import it.polimi.ingsw.pc22.utils.UserLoader;
import org.json.JSONException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameServer 
{
	private static Map<String, GameMatch> gameMatchMap;

	private static final int SOCKET_PORT = 9001;

	private static final int RMI_PORT = 5252;

	private static Map<String, User> usersMap;

	public static void main(String[] args)
	{
		System.out.println("Server online");
		
		gameMatchMap = new ConcurrentHashMap<>();

		try
		{
			Registry registry = LocateRegistry.createRegistry(RMI_PORT);

			RMIAuthenticationServiceImpl rmiAuthenticationHandler
					= new RMIAuthenticationServiceImpl(registry);

			RMIAuthenticationService stub = (RMIAuthenticationService)
					UnicastRemoteObject.exportObject(rmiAuthenticationHandler, 0);

			registry.rebind("auth", stub);

		}
			catch (RemoteException e )
		{
			e.printStackTrace();
		}

		System.out.println("Authentication Service running at " + RMI_PORT + " port");

		ServerSocket serverSocket;

		try 
		{
			serverSocket = new ServerSocket(SOCKET_PORT);

			usersMap = loadUsers();

			while(true)
			{
				Socket socket = serverSocket.accept();


				SocketAuthenticationHandler handler
						= new SocketAuthenticationHandler(socket);

				new Thread(handler).start();
			}
			
		} 
			catch (IOException | JSONException e)
		{
			e.printStackTrace();
		}
	}
	
	protected static Map<String, User> loadUsers() throws IOException, JSONException
	{
		Map<String, User> usersMap = UserLoader.generateUserMap();
		
		return usersMap;
	}

	public static Map<String, GameMatch> getGameMatchMap() 
	{
		return gameMatchMap;
	}

	public static void setGameMatchMap(Map<String, GameMatch> gameMatchMap) 
	{
		GameServer.gameMatchMap = gameMatchMap;
	}

	public static Map<String, User> getUsersMap() {
		return usersMap;
	}

	public static void setUsersMap(Map<String, User> usersMap) {
		GameServer.usersMap = usersMap;
	}

	public class PlayerComparator implements Comparator<Player>
	{
		@Override
		public int compare(Player o1, Player o2)
		{
			int value = Integer.compare(o1.getPriority(), o2.getPriority());

			if (value == 0) value = o1.getName().compareTo(o2.getName());

			return value;
		}
	}
}
