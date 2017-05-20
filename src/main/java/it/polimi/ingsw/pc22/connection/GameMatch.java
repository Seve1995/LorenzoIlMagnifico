package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.utils.BoardLoader;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameMatch implements Runnable
{
	private int currentRoundNumber;
	
	private String gameName;
	
	private Map<Player, Socket> players;
	
	private int playerCounter = 0;
	
	private int maxPlayersNumber;

	private GameBoard gameBoard;
	
	private Long timeOut;

	private static final String BOARD_PATH = "boards/";
	
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
		
		if (playerCounter <= 1)
		{
			endGame();

			return;
		}
		
		System.out.println("Inizio partita");
		
		startGame();

		endGame();
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
		String boardName = BOARD_PATH + "Gameboard-" + playerCounter + ".json";

		ClassLoader classLoader = this.getClass().getClassLoader();

		File file = new File(classLoader.getResource(boardName).getFile());

		JSONObject jsonBoard = new JSONObject(file);

		jsonBoard.toString();

		GameBoard gameBoard = BoardLoader.loadGameBoard();

		//loadJson su base counter
		//caricamento board
		//
	}

	private void endGame()
	{
		List<Socket> playerSockets = new ArrayList<>(players.values());

		for (Socket playerSocket : playerSockets)
		{
			try
			{
				PrintWriter printWriter =
						new PrintWriter(playerSocket.getOutputStream(), true);

				printWriter.println("EXIT");

				playerSocket.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
