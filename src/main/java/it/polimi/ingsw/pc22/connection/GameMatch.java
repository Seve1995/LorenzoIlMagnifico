package it.polimi.ingsw.pc22.connection;

import com.sun.xml.internal.xsom.impl.scd.Iterators;
import it.polimi.ingsw.pc22.adapters.GameAdapter;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.utils.BoardLoader;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameMatch implements Runnable
{
	private int currentRoundNumber = 0;
	
	private String gameName;

	//TODO non possiamo avere solo scoket, nel caso di rmi bisogna mettere il callback del client
	private Map<Player, GameAdapter> players;
	
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

	private void startGame()
	{
		String boardName = BOARD_PATH + "GameBoard-" + playerCounter + ".json";

		ClassLoader classLoader = this.getClass().getClassLoader();

		File file = new File(classLoader.getResource(boardName).getFile());

		StringBuilder builder = new StringBuilder();

		try
		{
			Files.lines(file.toPath()).forEach(s -> builder.append(s));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		JSONObject jsonBoard = new JSONObject(builder.toString());

		jsonBoard.toString();

		GameBoard gameBoard = BoardLoader.loadGameBoard(jsonBoard);

		//loadCarte

		//loadBonusTile

        //int turnNumber = 6 *

		//loadresourcestoplayers

		//loadBonusTilestoplayers

		//setDellecartescomunica

		//INIZIO PARTITA SU BASE CONNESSIONE

		/*for ( ; currentRoundNumber < 24; currentRoundNumber++)
		{
			//if (currentRoundNumber % 4|3) funzione rolladadi gameBoard.setDice(rollDices()); e settacarte

			for(Socket socket : players.values())
			{
				//socket.getInputStream();
				//socket.getOutputStream();

				//pensare a timeout

				while(true)
				{

					//stampa gameBoard

					//stampa plancia

					//Richiesta: che razza di azione vuoi fare??

					//vuoi sacrificare i servitori??

					//scegli dove metterti? mercato-concilio-torre-produzione-raccolto

					//se player dice "setFamiliarOnTower WHITE tipo floor"
					//se player dice "setFamiliarOnmarket BLACK position"
					//se player dice "setFamiliarOnCouncil ORANGE"
					//se player dice "setFamiliarOnHarvest NEUTER"
					//se player dice "setFamiliarOnProduction "

					//new decorator(new Action(), numServitori);
					//boolean  executed = decorator.execute(player);
					//else
					//boolean executed =  action.execute(player);

					//if (exectuted) continue;

				}
			}

			//if (currentRoundNumber % 8|6) gestionefineEra()
		}
		*/
		//gestioneFineGioco();decreto vincitore e tutto quello che ne consegue


	}

	private void endGame()
	{
		List<GameAdapter> playersAdapters = new ArrayList<GameAdapter>(players.values());

		for (GameAdapter playerAdapter : playersAdapters)
		{
			playerAdapter.endConnection();
		}
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

	public Map<Player, GameAdapter> getPlayers() {
		return players;
	}

	public void setPlayers(Map<Player, GameAdapter> players) {
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
}
