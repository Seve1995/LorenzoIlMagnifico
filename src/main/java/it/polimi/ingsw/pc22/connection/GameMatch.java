package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.adapters.SocketIOAdapter;
import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.messages.CommunicationMessage;
import it.polimi.ingsw.pc22.messages.EndMatchMessage;
import it.polimi.ingsw.pc22.messages.ErrorMessage;
import it.polimi.ingsw.pc22.messages.GameStatusMessage;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.utils.*;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class GameMatch implements Runnable
{	
	private String gameName;

	private Boolean started = false;

	private List<Player> players;

	private int playerCounter = 0;
	
	private int maxPlayersNumber;

	private GameBoard gameBoard;

	private List<DevelopmentCard> cards;

	private List<BonusTile> tiles;

	private List<ExcommunicationCard> excommunicationCards;

	private List<LeaderCard> leaderCards;

	private static Long timeout;
	
	private int turn = 0;
	
	private int era = 0;

	private static Player currentPlayer;

	private static GameBoard currentGameBoard;

	private static final String BOARD_PATH = "boards/";

	private static final Logger LOGGER = Logger.getLogger(GameMatch.class.getName());
	
	public GameMatch(Long timeOut, int maxPlayersNumber)
	{
		GameMatch.timeout = timeOut;
		this.maxPlayersNumber = maxPlayersNumber;
	}
	
	@Override
	public void run()
	{
		Long timeStamp = System.currentTimeMillis();

		Timer timer = new Timer();

		timer.schedule(new TimerTask()
		{
			public void run()
			{
				boolean isGameFull = playerCounter == maxPlayersNumber;

				System.out.println("isGameFull " + isGameFull);

				boolean isTimeoutExpired =
						System.currentTimeMillis() > timeStamp + timeout;

				System.out.println("isTimeoutExpired " + isTimeoutExpired);

				if (isTimeoutExpired || isGameFull)
				{
					startGame();

					timer.cancel();
				}
			}
		}, 5000, 5000);
	}

	private void startGame()
	{
		System.out.println("Inizio partita");

		for (Player player : players)
		{
			//TODO RICORDARSI POI ALLA FINE DI RESETTARE LO STATO
			player.setInMatch(true);
		}

		this.started = true;

		handleGame();

		endGame();
	}

	private void handleGame()
	{
		loadGameBoard();

		loadCards();

		loadExcommunicationCards();

		loadBonusTiles();

		loadLeaderCards();

		assignLeaderCards();

		assignExcommunicationCards();

		//=6 turni da 4 azioni l'una//=6 turni da 3 azioni l'una
        int turnNumber = playerCounter == 5 ? 24 : 18;

		GameBoardUtils.setUpPlayers(players, playerCounter, tiles);

		for (Player p : players)
		{
			IOAdapter adapter = p.getAdapter();
			//Serve per la GUI
			adapter.printMessage(new GameStatusMessage(gameBoard, p, "startGameBoard"));
		}
		
		for (int currentRoundNumber = 0; currentRoundNumber < turnNumber; currentRoundNumber++)
		{
			if (isNewTurn(currentRoundNumber))
			{
				turn++;

				if (turn%2==1) era++;
				
				GameBoardUtils.excommunicationHandling
				(players, playerCounter, currentRoundNumber, era,
						excommunicationCards, gameBoard);
				
				addDices();

				addTowerCards(era);

				checkOrderTurn();

				resetLeaderCards(players);

				GameBoardUtils.purgeGameBoard(gameBoard);
				
				addFamiliarsValue();

				for (Player p : players)
				{
					IOAdapter adapter = p.getAdapter();

					for (CharacterCard c : p.getPlayerBoard().getCharacters())
					{
						executeOneTurnEffect(adapter, c.getPermanentEffects(), p);
					}

					adapter.printMessage(new CommunicationMessage("Turn " + turn + " now starting!"));
				}
			}

			for(Player player : players)
			{
				if (player.isSuspended()) continue;

				for (Player p : players)
				{
					if (p.equals(player) || p.isSuspended())
						continue;

					IOAdapter adapter = p.getAdapter();
					adapter.printMessage(new GameStatusMessage(gameBoard, p, "refreshGameBoard"));
				}
				
				currentPlayer = player;

				currentGameBoard = gameBoard;

				IOAdapter adapter = player.getAdapter();

				adapter.printMessage(new GameStatusMessage(gameBoard, player, "started"));

				if (adapter instanceof SocketIOAdapter)
				{
					new Thread(new ActionThread()).start();
				}

				Long timestamp = System.currentTimeMillis();
				
				while (System.currentTimeMillis() < timestamp + timeout)
				{
					try
					{
						Thread.sleep(100L);
					}
						catch (InterruptedException e)
					{
						LOGGER.log(Level.WARNING, "Interrupted!", e);
						Thread.currentThread().interrupt();
					}

					if (currentPlayer.isHasPassed()) break;

					if (currentPlayer.isFamiliarPositioned()) break;
				}

				/*

				LASCIARE PER QUANDO FUNZIONAERÀ LA SOSPENSIONE
				if (!currentPlayer.isHasPassed() && !currentPlayer.isFamiliarPositioned())
				{
					player.setSuspended(true);

					adapter.printMessage(new SuspendedMessage("YOU HAVE BEEN SUSPENDED, PLEASE RE-LOG"));

					if (adapter instanceof SocketIOAdapter)
						new Thread((SocketIOAdapter) adapter).start();
				}
				else
					adapter.printMessage(new GameStatusMessage(gameBoard, player, "finished"));
				*/
				
				adapter.printMessage(new GameStatusMessage(gameBoard, player, "finished"));
			}

			GameBoardUtils.resetPlayerStatus(players);

		}

		GameBoardUtils.endGameExcommunicationHandling(players, excommunicationCards, gameBoard, era);

		//check excommunication
		GameBoardUtils.sumFinalPoints(players, gameBoard);

		String winnerName = selectWinner(players);

		for (Player p : players)
		{
			IOAdapter adapter = p.getAdapter();

			List<Player> standings = new ArrayList<>(GameServer.getPlayersMap().values());

			adapter.printMessage(new EndMatchMessage(standings, winnerName));
		}

		endGame();
	}

	private void executeOneTurnEffect(IOAdapter adapter, List<Effect> effects, Player p)
	{
		if (effects == null)
			return;

		for (Effect e : effects)
		{
			if (e.executeEffects(p, gameBoard))
			{
				continue;
			}

			else
			{
				adapter.printMessage(new ErrorMessage("Effect not executed!"));
			}

		}

	}

	private void endGame()
	{
		for (Player player : players)
		{
			IOAdapter playerAdapter = player.getAdapter();

			try
			{
				playerAdapter.endConnection(player);
			}
				catch (IOException e)
			{
				LOGGER.log(Level.INFO, "CANNOT CLOSE CONNECTION", e);
			}
		}
	}

	private void loadGameBoard()
	{
		String path = BOARD_PATH + "GameBoard-" + playerCounter + ".json";

		String boardString = fileLoader(path);

		JSONObject jsonBoard = new JSONObject(boardString);

		gameBoard = BoardLoader.loadGameBoard(jsonBoard);
	}

	private void loadCards()
	{
		String path = BOARD_PATH + "cards.json";

		String cardString = fileLoader(path);

		JSONObject jsonCards = new JSONObject(cardString);

		cards = CardLoader.loadCards(jsonCards);
		
		Collections.shuffle(cards);
	}

	private void assignLeaderCards()
	{
		Collections.shuffle(leaderCards);

		int i=0;

		while(i<players.size())
		{

			Player player = players.get(i);

			for (int j=4*i; j<(4*(i+1)); j++)
			{
				LeaderCard currLeaderCard = leaderCards.get(j);

				player.getLeaderCards().add(currLeaderCard);
			}

			i++;
		}
	}

	private void assignExcommunicationCards()
	{

		ExcommunicationCard firstEraExcommunication =
				excommunicationCards.stream().filter(excommunicationCard -> (excommunicationCard.getAge()==1))
						.collect(Collectors.toList()).get(0);

		ExcommunicationCard secondEraExcommunication =
				excommunicationCards.stream().filter(excommunicationCard -> (excommunicationCard.getAge()==2))
						.collect(Collectors.toList()).get(0);

		ExcommunicationCard thirdEraExcommunication =
				excommunicationCards.stream().filter(excommunicationCard -> (excommunicationCard.getAge()==3))
						.collect(Collectors.toList()).get(0);


		List<ExcommunicationCard> tempExcCards = new ArrayList<>();

		tempExcCards.add(firstEraExcommunication);
		tempExcCards.add(secondEraExcommunication);
		tempExcCards.add(thirdEraExcommunication);

		gameBoard.setExcommunicationCards(tempExcCards);
	}

	private void loadExcommunicationCards()
	{
		String path = BOARD_PATH + "ExcommunicationCards.json";

		String excommunicationString = fileLoader(path);

		JSONObject cards = new JSONObject(excommunicationString);

		excommunicationCards =
				ExcommunicationCardLoader.loadExcommunicationCards(cards);
	}

	private void loadBonusTiles()
	{
		String path = BOARD_PATH + "BonusTiles.json";

		String bonusTilesString = fileLoader(path);

		JSONObject bonusTiles = new JSONObject(bonusTilesString);

		tiles = BonusTileLoader.loadBonusTiles(bonusTiles);
	}

	private void loadLeaderCards()
	{
		String path = BOARD_PATH + "LeaderCards.json";

		String leaderCardsString = fileLoader(path);

		JSONObject leaders = new JSONObject(leaderCardsString);

		leaderCards = LeaderCardLoader.loadLeaderCards(leaders);
	}

	private String fileLoader(String path)
	{
		ClassLoader classLoader = this.getClass().getClassLoader();

		File file = new File(classLoader.getResource(path).getFile());

		StringBuilder builder = new StringBuilder();

		try
		{
			Files.lines(file.toPath()).forEach(s -> builder.append(s));
		}
		catch (IOException e)
		{
			LOGGER.log(Level.INFO, "Cannot import file loaded", e);
		}

		return builder.toString();
	}

	private boolean isNewTurn(int currentNumber)
	{
		if (currentNumber==0) 
		{
			return true;
		}
		
		if (this.playerCounter==5 && currentNumber%3==0)
		{
			return true;
		}
		
		if (this.playerCounter<=4 && currentNumber%4==0) 
		{
			return true;
		}
		
		return false;
	}
	
	private void addDices() 
	{
		ArrayList<Dice> dices = new ArrayList<>();

		Dice blackDice = new Dice(ColorsEnum.BLACK);
		blackDice.rollingDice();

		dices.add(blackDice);
		Dice whiteDice = new Dice(ColorsEnum.WHITE);

		whiteDice.rollingDice();
		dices.add(whiteDice);

		if(this.playerCounter < 5)
		{
			Dice orangeDice = new Dice(ColorsEnum.ORANGE);
			dices.add(orangeDice);
			orangeDice.rollingDice();
		}
		gameBoard.setDices(dices);
	}
	
	private void addTowerCards(int era) 
	{   
		Tower[] towers = gameBoard.getTowers();

		final int roundNumber = era;
		
		System.out.println(turn);
		
		List<TowerCell> territoryTowerCells = towers[0].getTowerCells();
				
		List<DevelopmentCard> territoryCards = cards
				.parallelStream()
				.filter(devCard -> ((devCard.getRoundNumber() == roundNumber) && (devCard instanceof TerritoryCard)))
				.limit(4)
				.collect(Collectors.toList());

		for (DevelopmentCard t : territoryCards)
			cards.remove(t);
		
		for (int i=0; i < territoryTowerCells.size(); i++)
		{
			territoryTowerCells.get(i).setDevelopmentCard(territoryCards.get(i));
		}
	
		List<TowerCell> characterTowerCells = towers[1].getTowerCells();
		
		List<DevelopmentCard> characterCards = cards
				.parallelStream()
				.filter(devCard -> (devCard.getRoundNumber() == roundNumber && devCard instanceof CharacterCard))
				.limit(4)
				.collect(Collectors.toList());

		for (DevelopmentCard t : characterCards)
			cards.remove(t);

		for (int i=0; i<characterTowerCells.size(); i++)
		{
			characterTowerCells.get(i).setDevelopmentCard(characterCards.get(i));
		}

		List<TowerCell> buildingTowerCells = towers[2].getTowerCells();
		
		List<DevelopmentCard> buildingCards = cards
				.parallelStream()
				.filter(devCard -> (devCard.getRoundNumber() == roundNumber && devCard instanceof BuildingCard))
				.limit(4)
				.collect(Collectors.toList());

		for (DevelopmentCard t : buildingCards)
			cards.remove(t);

		for (int i=0; i<buildingTowerCells.size(); i++)
		{
			buildingTowerCells.get(i).setDevelopmentCard(buildingCards.get(i));
		}
		
		List<TowerCell> ventureTowerCells = towers[3].getTowerCells();
		
		List<DevelopmentCard> ventureCards = cards
				.parallelStream()
				.filter(devCard -> (devCard.getRoundNumber() == roundNumber && devCard instanceof VentureCard))
				.limit(4)
				.collect(Collectors.toList());

		for (DevelopmentCard t : ventureCards)
			cards.remove(t);
		
		for (int i=0; i<ventureTowerCells.size(); i++)
		{
			ventureTowerCells.get(i).setDevelopmentCard(ventureCards.get(i));
		}
		System.out.println(cards.size());		
	}

	private void resetLeaderCards(List<Player> players)
	{
		for (Player p : players)
		{
			if (p.getPlayerBoard().getLeaderCards().isEmpty()) return;

			for (LeaderCard l : p.getPlayerBoard().getLeaderCards())
			{
				l.setFaceUp(true);
			}

		}
	}

	private void checkOrderTurn()
	{
		CouncilPalace palace = gameBoard.getCouncilPalace();

		List<Player> tempPlayers = palace.getPlayersInCouncilPalace();

		if (tempPlayers==null) return; 
		
		for (Player player : players)
		{
			if(tempPlayers.contains(player)) continue;

			tempPlayers.add(player);
		}

		players = tempPlayers;
	}

	
	private void addFamiliarsValue()
	{
		for (Player player : players)
			player.setFamiliarToPlayer(gameBoard.getDices());
	}
	
	private String selectWinner(List<Player> players)
	{
		Collections.sort(players, new PlayerComparator());

		String winnerName = null;

		for (int i  = 0; i < players.size(); i++)
		{
			Player player = players.get(i);

			if (i == 0)
			{
				winnerName = player.getUsername();

				int victories = player.getNumberOfMatchWon() + 1;

				player.setNumberOfMatchWon(victories);
			}
			else
			{
				int matchesLost = player.getNumberOfMatchLost() + 1;

				player.setNumberOfMatchLost(matchesLost);
			}

		}

		return winnerName;
	}

	public int getMaxPlayersNumber()
	{
		return maxPlayersNumber;
	}

	public Boolean getStarted() {
		return started;
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

	public static Player getCurrentPlayer() {
		return currentPlayer;
	}

	public static GameBoard getCurrentGameBoard() {
		return currentGameBoard;
	}

	public static Long getTimeout()
	{
		return timeout;
	}

	public class PlayerComparator implements Comparator<Player>
	{
		@Override
		public int compare(Player o1, Player o2)
		{
			int value = Integer.compare(o2.getVictoryPoints(), o1.getVictoryPoints());

			return value;
		}
	}
}
