package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.rmi.RMIAuthenicationService;
import it.polimi.ingsw.pc22.utils.UserLoader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
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

		/*//TODO HANDLE RMI CONNECTION

		RMIAuthenticationHandler rmiAuthenicationService
				= new RMIAuthenticationHandler();

		try
		{
			RMIAuthenicationService stub = (RMIAuthenicationService)
					UnicastRemoteObject.exportObject(rmiAuthenicationService, 0);

			Registry registry = LocateRegistry.createRegistry(RMI_PORT);


			registry.bind("auth", stub);

		}
			catch (RemoteException | AlreadyBoundException e )
		{
			e.printStackTrace();
		}

		System.out.println("Authentication Service running at "+ RMI_PORT +" port...");*/

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
			catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	protected static Map<String, User> loadUsers() throws IOException
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
}
