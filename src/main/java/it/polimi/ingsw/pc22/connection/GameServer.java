package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.adapters.RMIIOAdapter;
import it.polimi.ingsw.pc22.adapters.SocketIOAdapter;
import it.polimi.ingsw.pc22.exceptions.GenericException;
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
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameServer 
{
	private static Map<String, GameMatch> gameMatchMap;

	private static final int SOCKET_PORT = 9001;

	private static final int RMI_PORT = 5439;

	private static Map<String, User> usersMap;

    private static final Logger LOGGER = Logger.getLogger(GameServer.class.getName());

	public static void main(String[] args)
	{
		System.out.println("Server online");
		
		gameMatchMap = new ConcurrentHashMap<>();

		Long timeout = 30000L;

		try
		{
			Registry registry = LocateRegistry.createRegistry(RMI_PORT);

			RMIIOAdapter rmiIoAdapter = new RMIIOAdapter(registry, timeout);

			RMIAuthenticationService stub = (RMIAuthenticationService)
					UnicastRemoteObject.exportObject(rmiIoAdapter, 0);

			registry.rebind("auth", stub);

		}
			catch (RemoteException e)
		{
			throw new GenericException(e);
		}

		System.out.println("Authentication Service running at " + RMI_PORT + " port");

		ServerSocket serverSocket = null;

		ExecutorService games = Executors.newCachedThreadPool();

		ExecutorService executor = Executors.newSingleThreadExecutor();

		Future<Boolean> exit = executor.submit(new ExitThread());

		try 
		{
			serverSocket = new ServerSocket(SOCKET_PORT);

			usersMap = loadUsers();

			while(true)
			{

                if (exit.isDone())
                {
                    break;
                }

				Socket socket = serverSocket.accept();

				SocketIOAdapter socketIOAdapter
						= new SocketIOAdapter(socket, timeout);

				games.submit(socketIOAdapter);
			}
		} 
		catch (IOException | JSONException e)
		{
			throw new GenericException(e);
		}
		finally
        {
            try
            {
                if (serverSocket != null)
                    serverSocket.close();
            }
                catch(IOException e)
            {
                LOGGER.log(Level.INFO, "User not loaded", e);
            }
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
