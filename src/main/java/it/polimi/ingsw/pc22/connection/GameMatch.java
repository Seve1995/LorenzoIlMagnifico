package it.polimi.ingsw.pc22.connection;

import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public class GameMatch implements Runnable
{
	private int currentRoundNumber;

	//- playersThreadPool : ThreadPool
	
	private String gameName;
	
	private Map<Player, Socket> players;
	
	private int playerCounter = 0;
	
	//private List<Socket> playersSocket = new ArrayList<>();
	
	private ExecutorService executor = Executors.newFixedThreadPool(2);
	
	private GameBoard gameBoard;
	
	@Override
	public void run() 
	{
		FutureTask<String> time = new FutureTask<>(new Timer());
		
		FutureTask<Boolean> counter = new FutureTask<>(new Counter(this));
		
		executor.execute(time); executor.execute(counter);
		
		while(true)
		{
			if (time.isDone() || counter.isDone())
			{
				counter.cancel(true);
				time.cancel(true);
				
				executor.shutdown();
				
				System.out.println("Inizio partita");
				break;
			}
		}
		
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
	
	public ExecutorService getExecutor() {
		return executor;
	}

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
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
