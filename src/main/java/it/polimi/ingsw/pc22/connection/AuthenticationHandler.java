package it.polimi.ingsw.pc22.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import it.polimi.ingsw.pc22.player.Player;


public class AuthenticationHandler implements Runnable {
	private Socket socket;
	int i;
	private static List<User> usersList; 
	private Boolean validUsername=false;
	private Boolean validPassword=false;
	private Boolean authenticated=false;
	private BufferedReader in;
	private PrintWriter out;
	private User user;
	private Map<String, GameMatch> gameMatchMap;
	
	public AuthenticationHandler(Socket socket, Map<String, GameMatch> gameMatchMap)
	{
		this.gameMatchMap = gameMatchMap;
		this.socket=socket;
	}
	
	public void run()
	{
		try 
		{
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			
			while(!authenticated) 
			{	
				out.println("Sign In or Login?");
				
				String risposta = in.readLine().toLowerCase();
				
				switch(risposta)
				{
					case "login":
						
						user = login();
						authenticated = true;
						
						break;
					case "sign in":
						
						user = registration();
						authenticated = true;
						
						break;
						
					default:
						out.println("Non-valid input. Please retry... ");
						
						break;
				}
			}
			 	
			GameMatch gameMatch = null;
			
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
					gameMatch = gameMatchMap.get(gameName);
					
					if (gameMatch != null) 
					{
						out.println("A game match with the specified name already exists.");
						
						continue;
					}
					
					startNewGameMatch(gameName, player);
					
					break;
					
				}
				
				if(userChoice.equals("2"))
				{
					out.println("Type the name of the chosen game match:");
					
					String gameName = in.readLine();
					
					out.println("Game name: " + gameName);
					
					gameMatch = gameMatchMap.get(gameName);
					
					if (gameMatch == null) 
					{
						out.println("Game match not found.");
						
						continue;
					}
					
					loadGameMatch(gameMatch, player);
					
					break;
				}
				
				if(userChoice.equals("3"))
				{
					
				}
				
				out.println("Non-valid input. Please retry... ");
			}
			
			in.close();
			
			out.close();
			
			socket.close();
			
			JsonManager.refreshJson(usersList);
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	private User login() throws IOException
	{
		String username=null, password=null;
		while(!validUsername)
		{
			out.println("Type an existing username:");
			username = in.readLine();
			validUsername = existingUsername(username);
			if (!validUsername)
				out.println("Error: username not found! Please retry.");
		}
		out.println("Username OK. Now type the password:");
		while(!validPassword)
		{
			password=in.readLine();
			validPassword = checkPassword(username, password);
			if (!validPassword) 
				out.println("Error: wrong password! Please retry.");
		}
		out.println("Successful logged in!");
		return new User(username, password);
	}
	
	private User registration() throws IOException
	{
		String username = null, password = null;
		boolean registrated=false;
		while(!registrated)
		{
			out.println("Type an username:");
			username = in.readLine();
			validUsername = !existingUsername(username);
			if (!validUsername) 
				{
					out.println("The specified username already exist! Please type a new username.");
					continue;
				}
			out.println("Type a password"); 
			password = in.readLine();
			synchronized (usersList) 
			{	
				if(!existingUsername(username)) 
					{
					usersList.add(new User(username,password));
					registrated = true;
					}
				else
					{
					out.println("The specified username already exist! Please type a new username.");
					validUsername = false;
					}
			}	
		}
		out.println("User created!");
		return new User(username, password);
	}
	
	synchronized private boolean existingUsername(String username) 
	{ 
		for (int i=0; i<usersList.size(); i++)
			if (username.equals(usersList.get(i).getUsername()))
				return true;
		return false;
	}

	synchronized private boolean checkPassword(String username, String password) 
	{
		for (int i=0; i<usersList.size(); i++)
			if (username.equals(usersList.get(i).getUsername()))
				if(password.equals(usersList.get(i).getPassword()))
						return true;
		return false;
	}
	
	protected static void loadJSon() throws IOException{
		usersList = JsonManager.returnList();
	}
	
	
	//TODO FAR SI CHE I VALORI VENGANO GESTITI DAL PARSER JSON
	private void startNewGameMatch(String gameName, Player player)
	{
		GameMatch gameMatch = new GameMatch(10000L, 4);
		
		gameMatchMap.put(gameName, gameMatch);
		
		Map<Player, Socket> players = new TreeMap<>(new PlayerComparator());
		
		players.put(player, socket);
		
		gameMatch.setPlayers(players);
		
		int counter = gameMatch.getPlayerCounter() + 1;
		
		gameMatch.setPlayerCounter(counter);
		
		new Thread(gameMatch).start();
	}
	
	synchronized private void loadGameMatch(GameMatch gameMatch, Player player)
	{
		Map<Player, Socket> players = gameMatch.getPlayers();
		
		players.put(player, socket);
		
		gameMatch.setPlayers(players);
	
		int counter = gameMatch.getPlayerCounter() + 1;
		
		gameMatch.setPlayerCounter(counter);
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
