package it.polimi.ingsw.pc22.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import it.polimi.ingsw.pc22.connection.ConnectionHandler.PlayerComparator;
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
	public AuthenticationHandler (Socket socket){
		this.socket=socket;
	}
	
	public void run(){
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			while(!authenticated) 
			{	
				out.println("Registrazione o login?");
				String risposta = in.readLine();
				if (risposta.equalsIgnoreCase("Login"))
					{
					user = login();
					authenticated = true;
					}
				if (risposta.equalsIgnoreCase("Registra"))
					{
					user = registration();
					authenticated = true;
					}
				else
					out.println("Input non valido. Riprova. :) ");
			}
			 	out.print("Scegliere un'operazione da effettuare:/n"
			 			+ "1) Creazione nuova partita/n"
			 			+ "2) Partecipa a una partita creata da un amico/n"
			 			+ "3) Partecipa a una partita casuale"
			 			);
				String playerName = (String) ois.readObject();
				
				String gameName = (String) ois.readObject();
				
				Player player = new Player();
				
				player.setName(playerName);
				
				GameMatch gameMatch = gameMatchMap.get(gameName);
				
				if (gameMatch == null)
				{
					gameMatch = new GameMatch();
					
					gameMatchMap.put(gameName, gameMatch);
					
					Map<Player, Socket> players = new TreeMap<>(new PlayerComparator());
					
					System.out.println(player);
					
					players.put(player, socket);
					
					gameMatch.setPlayers(players);
					
					int counter = gameMatch.getPlayerCounter() + 1;
					
					gameMatch.setPlayerCounter(counter);
					
					new Thread(gameMatch).start();
				}
					else
				{
					Map<Player, Socket> players = gameMatch.getPlayers();
					
					players.put(player, socket);
					
					gameMatch.setPlayers(players);
					
					System.out.println(player);
					
					int counter = gameMatch.getPlayerCounter() + 1;
					
					gameMatch.setPlayerCounter(counter);
				}
							
			} catch (IOException | ClassNotFoundException e) 
			{
				
				e.printStackTrace();
			}
			in.close();
			out.close();
			socket.close();
			JsonManager.refreshJson(usersList);
		} catch (IOException e) 
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
		out.println("Password corretta!");
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
		out.println("User creato!");
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
}

