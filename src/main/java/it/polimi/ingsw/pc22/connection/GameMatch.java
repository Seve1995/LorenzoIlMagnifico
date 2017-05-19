package it.polimi.ingsw.pc22.connection;

import java.net.Socket;
import java.util.Map;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public class GameMatch implements Runnable
{
	private int currentRoundNumber;
	
	private String gameName;
	
	private Map<Player, Socket> players;
	
	private int playerCounter = 0;
	
	private int maxPlayersNumber;

	private GameBoard gameBoard;
	
	private Long timeOut;
	
	public GameMatch(Long timeOut, int maxPlayersNumber)
	{
		this.timeOut = timeOut;
		this.maxPlayersNumber = maxPlayersNumber;
	}
	
	@Override
	public void run() 
	{
		Long timeStamp = System.currentTimeMillis();
		
		while(true)
		{
			boolean isTimeoutExpired = 
					System.currentTimeMillis() > timeStamp + timeOut; 
					
			boolean isGameFull = playerCounter == maxPlayersNumber;
			
			if (!isTimeoutExpired && !isGameFull) continue;
			
			break;
		}
		
		if (playerCounter <= 1) return;
		
		System.out.println("Inizio partita");
		
		startGame();
	}
	

	public int getCurrentRoundNumber() {
		return currentRoundNumber;
	}
	
	public void setCurrentRoundNumber(int currentRoundNumber) {
		this.currentRoundNumber = currentRoundNumber;
	}
	
	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public Map<Player, Socket> getPlayers() {
		return players;
	}

	public void setPlayers(Map<Player, Socket> players) {
		this.players = players;
	}

	public int getPlayerCounter() {
		return playerCounter;
	}

	public void setPlayerCounter(int playerCounter) {
		this.playerCounter = playerCounter;
	}
	
	public GameBoard getGameBoard() {
		return gameBoard;
	}
	
	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}
	
	private void startGame()
	{
		//loadJson su base counter
		//caricamento board
		//
	}
}
