package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.adapters.RmiServerImpl;
import it.polimi.ingsw.pc22.adapters.SocketIOAdapter;
import it.polimi.ingsw.pc22.exceptions.GenericException;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.rmi.RMIServerInterface;
import it.polimi.ingsw.pc22.utils.PlayerLoader;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameServer 
{
	private static Map<String, GameMatch> gameMatchMap;

	private static final int SOCKET_PORT = 9001;

	private static final int RMI_PORT = 5439;

	private static Map<String, Player> playerMap;

    private static final Logger LOGGER = Logger.getLogger(GameServer.class.getName());

	public static void main(String[] args)
	{
		System.out.println("Server online");
		
		gameMatchMap = new ConcurrentHashMap<>();

		Long timeout = 60000L; 

		try
		{
			Registry registry = LocateRegistry.createRegistry(RMI_PORT);

			RmiServerImpl rmiServer = new RmiServerImpl(timeout);

			RMIServerInterface stub = (RMIServerInterface)
					UnicastRemoteObject.exportObject(rmiServer, 0);

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

			playerMap = loadPlayers();

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
	
	protected static Map<String, Player> loadPlayers() throws IOException, JSONException
	{
		Map<String, Player> usersMap = PlayerLoader.generatePlayerMap();
		
		return usersMap;
	}

	public static Map<String, GameMatch> getGameMatchMap() 
	{
		return gameMatchMap;
	}

	public static Map<String, Player> getPlayersMap() {
		return playerMap;
	}

	public class PlayerComparator implements Comparator<Player>
	{
		@Override
		public int compare(Player o1, Player o2)
		{
			int value = Integer.compare(o1.getPriority(), o2.getPriority());

			if (value == 0) value = o1.getUsername().compareTo(o2.getUsername());

			return value;
		}
	}
}
