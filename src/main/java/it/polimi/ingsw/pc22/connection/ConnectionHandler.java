package it.polimi.ingsw.pc22.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import it.polimi.ingsw.pc22.player.Player;

public class ConnectionHandler implements Runnable
{
	private Map<String, GameMatch> gameMatchMap;
	private Socket socket;
	
	//TODO refactor nomi soprattutto specificare contatore
	
	@Override
	public void run()
	{
		try 
		{
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			
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
		
	}
	
	public void setGameMatchMap(Map<String, GameMatch> gameMatchMap)
	{
		this.gameMatchMap = gameMatchMap;
	}
	
	public void setSocket(Socket socket)
	{
		this.socket = socket;
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
