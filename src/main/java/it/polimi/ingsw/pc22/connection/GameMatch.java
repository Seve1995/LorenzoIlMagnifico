package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.adapters.GameAdapter;
import it.polimi.ingsw.pc22.gamebox.DevelopmentCard;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.utils.BoardLoader;
import it.polimi.ingsw.pc22.utils.CardLoader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameMatch implements Runnable
{
	private int currentRoundNumber = 0;
	
	private String gameName;

	private List<Player> players;
	
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
			try
			{
				Thread.sleep(1000);
			}
				catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			boolean isTimeoutExpired =
					System.currentTimeMillis() > timeStamp + timeOut;;


			boolean isGameFull = playerCounter == maxPlayersNumber;

			if (!isTimeoutExpired && !isGameFull) continue;
			
			break;
		}

		System.out.println("Inizio partita");
		
		//startGame();

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

		System.out.println(gameBoard);

		String cardFile = BOARD_PATH + "VentureCards.json";

		file = new File(classLoader.getResource(cardFile).getFile());

		StringBuilder cardBuilder = new StringBuilder();

		try
		{
			Files.lines(file.toPath()).forEach(s -> cardBuilder.append(s));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		JSONObject jsonCards = new JSONObject(builder.toString());

		jsonBoard.toString();

		List<DevelopmentCard> cards = CardLoader.loadCards(jsonCards);

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
		for (Player player : players)
		{
			GameAdapter playerAdapter = player.getAdapter();

			try
			{
				playerAdapter.endConnection(player);
			}
				catch (IOException e)
			{
				e.printStackTrace();
			}
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

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
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
