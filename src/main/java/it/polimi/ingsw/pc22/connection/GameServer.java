package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.adapters.RmiServerImpl;
import it.polimi.ingsw.pc22.adapters.SocketIOAdapter;
import it.polimi.ingsw.pc22.exceptions.GenericException;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.rmi.RMIServerInterface;
import it.polimi.ingsw.pc22.utils.GameMatchLoader;
import it.polimi.ingsw.pc22.utils.PlayerLoader;
import org.json.JSONException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the core of the server.
 * It contains information about ports used, all the player that subscribed
 * on the server. It contains main method, and it loads all the things
 * needed to communicate via RMI, and via Socket. It remains online, listening
 * and accepting connection (or method calling) from the various client.
 */

public class GameServer 
{
	private static Map<String, GameMatch> gameMatchMap;

	private static final int SOCKET_PORT = 9001;

	private static final int RMI_PORT = 5439;

	private static Map<String, Player> playerMap;

	private static boolean isClosed = false;

    private static final Logger LOGGER = Logger.getLogger(GameServer.class.getName());

	public static void main(String[] args)
	{
		System.out.println("Server online");

		Long timeout = 60000L;

		Registry registry;

		RmiServerImpl rmiServer;

		try
		{
			registry = LocateRegistry.createRegistry(RMI_PORT);

			rmiServer = new RmiServerImpl(timeout);

			RMIServerInterface stub = (RMIServerInterface)
					UnicastRemoteObject.exportObject(rmiServer, 0);

			registry.rebind("auth", stub);

		}
			catch (RemoteException e)
		{
			throw new GenericException(e);
		}

		System.out.println("Authentication Service running at " + RMI_PORT + " port");

		ServerSocket serverSocket;

		ExecutorService games = Executors.newCachedThreadPool();

		ExecutorService executor = Executors.newSingleThreadExecutor();

		try 
		{
			serverSocket = new ServerSocket(SOCKET_PORT);

			executor.submit(new ExitThread(serverSocket));

			playerMap = loadPlayers();

			gameMatchMap = GameMatchLoader.loadGameMatches();

			while(!isClosed)
			{
				Socket socket = serverSocket.accept();

				SocketIOAdapter socketIOAdapter
						= new SocketIOAdapter(socket, timeout);

				games.submit(socketIOAdapter);
			}
		} 
		catch (IOException | JSONException e)
		{
			LOGGER.log(Level.INFO, "SOCKET CLOSED", e);
		}

		System.exit(0);
	}
	
	protected static Map<String, Player> loadPlayers() throws IOException, JSONException
	{
		Map<String, Player> usersMap = PlayerLoader.generatePlayerMap();
		
		return usersMap;
	}

	public static boolean isIsClosed() {
		return isClosed;
	}

	public static void setIsClosed(boolean isClosed) {
		GameServer.isClosed = isClosed;
	}

	public static Map<String, GameMatch> getGameMatchMap()
	{
		return gameMatchMap;
	}

	public static Map<String, Player> getPlayersMap() {
		return playerMap;
	}
}
