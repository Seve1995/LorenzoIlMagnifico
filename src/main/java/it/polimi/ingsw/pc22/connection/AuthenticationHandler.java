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
	private static List<User> usersList; //Meglio farla come List
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
				out.println("Registrazione o login?");
				
				String risposta = in.readLine();
				
				switch(risposta)
				{
					case "Login":
						
						user = login();
						authenticated = true;
						
						break;
					case "Registra":
						
						user = registration();
						authenticated = true;
						
						break;
						
					default:
						out.println("Input non valido. Riprova. :) ");
						
						break;
				}
			}
			 	
			GameMatch gameMatch = null;
			
			//TODO RINOMINARE IN GETUSERNAME?
			String playerName = user.getUser();
			
			Player player = new Player();
			
			player.setName(playerName);
			
			while(true)
			{
				out.println("Scegliere un'operazione da effettuare:\n"
			 			+ "1) Creazione nuova partita\n"
			 			+ "2) Partecipa a una partita creata da un amico\n"
			 			+ "3) Partecipa a una partita casuale"
			 			);
				
				String userChoice = in.readLine();
				
				if(userChoice.equals("1"))
				{
					out.println("inserire un nome per la partita");
					
					String gameName = in.readLine();
					
					out.println("Nome inserito: " + gameName);
					
					gameMatch = gameMatchMap.get(gameName);
					
					if (gameMatch != null) 
					{
						out.println("Nome non valido, partita già presente");
						
						continue;
					}
					
					startNewGameMatch(gameName, player);
					
					break;
					
				}
				
				if(userChoice.equals("2"))
				{
					out.println("inserire un nome per la partita");
					
					String gameName = in.readLine();
					
					out.println("Nome inserito: " + gameName);
					
					gameMatch = gameMatchMap.get(gameName);
					
					if (gameMatch == null) 
					{
						out.println("Partita non trovata");
						
						continue;
					}
					
					loadGameMatch(gameMatch, player);
					
					break;
				}
				
				if(userChoice.equals("3"))
				{
					
				}
				
				out.println("Inserire scelta valida");
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
			out.println("Inserire username:");
			username = in.readLine();
			validUsername = existingUsername(username);
			if (!validUsername)
				out.println("Username errato! Riprova.");
		}
		out.println("Username corretto. Adesso inserisci la password.");
		while(!validPassword)
		{
			password=in.readLine();
			validPassword = checkPassword(username, password);
			if (!validPassword) 
				out.println("Password errata! Riprova.");
		}
		out.print("Password corretta!");
		return new User(username, password);
	}
	
	private User registration() throws IOException
	{
		String username = null, password = null;
		boolean registrated=false;
		while(!registrated)
		{
			out.println("Inserire username:");
			username = in.readLine();
			validUsername = !existingUsername(username);
			if (!validUsername) 
				continue;
			out.println("Adesso inserisci la password"); 
			password = in.readLine();
			synchronized (usersList) 
			{	
				if(!existingUsername(username)) 
					{
					usersList.add(new User(username,password));
					registrated = true;
					}
				else
					validUsername = false;
			}	
		}
		out.print("User creato!");
		return new User(username, password);
	}
	
	synchronized private boolean existingUsername(String username) 
	{ 
		for (int i=0; i<usersList.size(); i++)
			if (username.equals(usersList.get(i).getUser()))
				return true;
		return false;
	}

	synchronized private boolean checkPassword(String username, String password) 
	{
		for (int i=0; i<usersList.size(); i++)
			if (username.equals(usersList.get(i).getUser()))
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

