package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.utils.UserLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class SocketAuthenticationHandler implements Runnable
{
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	
	public SocketAuthenticationHandler(Socket socket)
	{
		this.socket=socket;
	}
	
	public void run()
	{

		//CHIEDERE QUALE COMPORTAMENTO SI PREFERISCE NULL CHE RITORNA OPPURE NULL CHE BLOCCA??

		try 
		{
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);

			User user = null;

			while(true)
			{
				out.println("Sign In or Login?");

				String answer = in.readLine().toLowerCase();

				switch (answer)
				{
					case "login":

						user = login();

						break;
					case "sign in":

						user = registration();

						break;

					default:
						out.println("Non-valid input. Please retry... ");

						break;
				}

				if (user == null) continue;

				break;
			}

			String playerName = user.getUsername();
			
			Player player = new Player();
			
			player.setName(playerName);
			
			while(true)
			{
				out.println("Choose an operation:\n"
			 			+ "(1) Create new game match\n"
			 			+ "(2) Join a friend's game match\n"
			 			+ "(3) Join a random game match"
			 			);
				
				String userChoice = in.readLine();
				
				if(userChoice.equals("1"))
				{
					out.println("Type a name for the new game match:");
					
					String gameName = in.readLine();
					
					out.println("Game name: " + gameName);
					
					boolean existingGameMatch = 
							GameServer.getGameMatchMap().containsKey(gameName);
					
					if (existingGameMatch)
					{
						out.println("A game match with the specified name already exists.");
						
						continue;
					}
					
					startNewGameMatch(gameName, player);

					out.println("Player: " + playerName + " created GameMatch - " + gameName);

					break;
					
				}
				
				if(userChoice.equals("2"))
				{
					out.println("Type the name of the chosen game match:");
					
					String gameName = in.readLine();
					
					out.println("Game name: " + gameName);
					
					boolean existingGameMatch = 
							GameServer.getGameMatchMap().containsKey(gameName);
					
					if (!existingGameMatch) 
					{
						out.println("Game match not found.");
						
						continue;
					}
					
					loadGameMatch(gameName, player);

					out.println("Player: " + playerName + " joined GameMatch - " + gameName);

					break;
				}
				
				if(userChoice.equals("3"))
				{
					
				}
				
				out.println("Non-valid input. Please retry... ");
			}

			updateJson();
				
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	private User login() throws IOException
	{
		User user;

		while(true)
		{
			out.println("Type an existing username:");
			String username = in.readLine();
			user = existingUsername(username);

			if (user != null) break;

			out.println("Error: username not found! Please retry.");
		}
		out.println("Username OK. Now type the password:");

		while(true)
		{
			String password=in.readLine();

			if (user.getPassword().equals(password)) break;

			out.println("Error: wrong password! Please retry.");

		}
		out.println("Successful logged in!");
		return user;
	}
	
	private User registration() throws IOException
	{
		User user;

		while(true)
		{
			out.println("Type an username:");
			String username = in.readLine();

			Boolean invalidUsername =
					GameServer.getUsersMap().containsKey(username);
			
			if (invalidUsername)
			{
				out.println("The specified username already exist! Please type a new username.");
				continue;
			}

			out.println("Type a password"); 

			String password = in.readLine();

			user = new User(username, password, true);

			Map<String, User> usersMap = GameServer.getUsersMap();

			synchronized (usersMap)
			{	
				usersMap.put(username, user);
			}

			break;
		}
		out.println("User created!");
		return user;
	}

	//TODO REFACTOR NAME
	synchronized private User existingUsername(String username)
	{ 
		Map<String, User> usersMap = GameServer.getUsersMap();

		User user = usersMap.get(username);

		if (user == null) return null;

		if (user.isLogged()) return null;

		return  user;
	}

	//TODO FAR SI CHE I VALORI VENGANO GESTITI DAL PARSER JSON
	private void startNewGameMatch(String gameName, Player player)
	{
		GameMatch gameMatch = new GameMatch(15000L, 4);
		
		Map<String, GameMatch> gameMatchMap = GameServer.getGameMatchMap();
		
		gameMatchMap.put(gameName, gameMatch);
		
		Map<Player, Socket> players = new TreeMap<>(new PlayerComparator());
		
		players.put(player, socket);
		
		gameMatch.setPlayers(players);
		
		int counter = gameMatch.getPlayerCounter() + 1;
		
		gameMatch.setPlayerCounter(counter);
		
		new Thread(gameMatch).start();
	}
	
	synchronized private void loadGameMatch(String gameName, Player player)
	{
		Map<String, GameMatch> gameMatchMap = GameServer.getGameMatchMap();

		GameMatch gameMatch = gameMatchMap.get(gameName);

		Map<Player, Socket> players = gameMatch.getPlayers();
		
		players.put(player, socket);
		
		gameMatch.setPlayers(players);
	
		int counter = gameMatch.getPlayerCounter() + 1;
		
		gameMatch.setPlayerCounter(counter);
	}
	
	synchronized private void updateJson() throws IOException
	{
		Map<String, User> usersMap = GameServer.getUsersMap();
		
		UserLoader.refreshJson(usersMap);
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

