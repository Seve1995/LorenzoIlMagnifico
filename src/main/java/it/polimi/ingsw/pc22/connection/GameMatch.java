package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.adapters.SocketIOAdapter;
import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.exceptions.GenericException;
import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.messages.*;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.utils.*;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * This class represent the game match cycle.
 * It has methods to start game and to put all the things
 * that are needed on the player board, to set up player etc.
 * It also has methods to manage the excommunication,
 * and the end-game logic.
 * It also manages the fact that a player can be suspended for some reason.
 */

public class GameMatch implements Runnable, Serializable
{	
	private String gameName;

	private boolean started = false;

	private boolean serialized = false;

	private boolean stopped = false;

	private List<Player> players;

	private int playerCounter = 0;
	
	private int maxPlayersNumber;

	private GameBoard gameBoard;

	private List<DevelopmentCard> cards;

	private List<BonusTile> tiles;

	private List<ExcommunicationCard> excommunicationCards;

	private List<LeaderCard> leaderCards;

	private static Long timeout;

	private static Long timeForStart;
	
	private int turn = 0;
	
	private int era = 0;

	private Player currentPlayer;

	private GameBoard currentGameBoard;

	private Effect currEffect = null;

	private int currentRoundNumber = 0;

	private static final String BOARD_PATH = "boards/";

	private static final Logger LOGGER = Logger.getLogger(GameMatch.class.getName());

	private TimeOut timeOutFromFile;
	
	public GameMatch(int maxPlayersNumber)
	{

		this.maxPlayersNumber = maxPlayersNumber;
	}
	
	@Override
	public void run()
	{
		loadTimeOut();

		timeout = timeOutFromFile.getAction();

		timeForStart = timeOutFromFile.getGameStarting();

		if (serialized)
		{
			handleGame();

			try {
				endGame();
			} catch (IOException e) {
				throw new GenericException("Message: ", e);
			}

			return;
		}

		Long timeStamp = System.currentTimeMillis();

		Timer timer = new Timer();

		timer.schedule(new TimerTask()
		{
			public void run()
			{
				boolean isGameFull = playerCounter == maxPlayersNumber;

				boolean isTimeoutExpired =
						System.currentTimeMillis() > timeStamp + timeForStart;

				if (GameServer.isIsClosed())
				{
					handleStoppedServer();

					timer.cancel();
				}

				if (isTimeoutExpired || isGameFull)
				{
					try {
						startGame();
					} catch (IOException e) {
						throw new GenericException("Message: ", e);
					}

					timer.cancel();
				}
			}
		}, 5000, 5000);
	}

	private void startGame() throws IOException
	{
		for (Player player : players)
		{
			player.setInMatch(true);
		}

		this.started = true;

		loadGameBoard();

		loadCards();

		loadExcommunicationCards();

		loadBonusTiles();

		loadLeaderCards();

		resetPlayers();

		assignLeaderCards();

		assignExcommunicationCards();

		handleGame();

		if(GameServer.isIsClosed())
			return;
		
		endGame();
	}

	private void handleGame()
	{
		//=6 turni da 4 azioni l'una//=6 turni da 3 azioni l'una
        int turnNumber = playerCounter < 5 ? 24 : 18;

		GameBoardUtils.setUpPlayers(players, playerCounter, tiles);

		for (Player p : players)
		{
			IOAdapter adapter = p.getAdapter();
			//Serve per la GUI
			adapter.printMessage(new GameStatusMessage(gameBoard, p, "startGameBoard"));
		}
		
		for ( ; currentRoundNumber < turnNumber; currentRoundNumber++)
		{
			if (GameServer.isIsClosed())
				break;

			if (isNewTurn(currentRoundNumber))
				handleNewTurn(currentRoundNumber);

			for(Player player : players)
			{
				if (player.isSuspended())
				{
					PlayerNotify(player);
					continue;
				}

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
					new Thread(new ActionThread(gameName)).start();
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

					if (currentPlayer.isSuspended()) break;
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
				if (!currentPlayer.isSuspended())
					adapter.printMessage(new GameStatusMessage(gameBoard, player, "finished"));
			}

			GameBoardUtils.resetPlayerStatus(players);
		}

		if (GameServer.isIsClosed())
		{
			handleStoppedServer();

			return;
		}

		endGameExcommunicationHandling(players, excommunicationCards, gameBoard);
	}

	private void handleStoppedServer()
	{
		for (Player p : players)
		{
			IOAdapter adapter = p.getAdapter();

			adapter.printMessage(new StoppedServerMessage("SERVER STOPPED"));
		}

		this.stopped = true;
	}

	private void handleNewTurn(int currentRoundNumber)
	{
		turn++;

		if (turn%2==1) era++;

		excommunicationHandling(playerCounter, currentRoundNumber, era, excommunicationCards);

		addDices();

		addTowerCards(era);

		checkOrderTurn();

		resetLeaderCards(players);

		GameBoardUtils.purgeGameBoard(gameBoard);

		for (Player p : players)
		{
			IOAdapter adapter = p.getAdapter();
			
			for (CharacterCard c : p.getPlayerBoard().getCharacters())
			{
				executeOneTurnEffect(adapter, c.getPermanentEffects(), p);
			}

			adapter.printMessage(new CommunicationMessage("Turn " + turn + " now starting!"));

			addFamiliarsValue(p);
		}
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

	private void endGame() throws IOException
	{
		GameBoardUtils.sumFinalPoints(players, gameBoard);

		Player winner = selectWinner(players);

		for (Player p : players)
		{
			IOAdapter adapter = p.getAdapter();

			List<Player> standings = new ArrayList<>(GameServer.getPlayersMap().values());

			adapter.printMessage(new EndMatchMessage(standings, winner.getUsername(), winner.getVictoryPoints()));
			
			if (adapter instanceof SocketIOAdapter)
			{
				((SocketIOAdapter) adapter).setPlayerName(p.getUsername());
				new Thread((SocketIOAdapter) adapter).start();
			}
		}

		this.stopped = true;
		
		Map<String, Player> playerMap = GameServer.getPlayersMap();
        
        synchronized (playerMap) {
        	PlayerLoader.refreshJson(playerMap);
		}
	}

	private void loadGameBoard()
	{
		String path = BOARD_PATH + "GameBoard-" + playerCounter + ".json";

		String boardString = fileLoader(path);

		JSONObject jsonBoard = new JSONObject(boardString);

		gameBoard = BoardLoader.loadGameBoard(jsonBoard);

		gameBoard.setGameMatchName(gameName);
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

		while(i < players.size())
		{
			Player player = players.get(i);

			List<LeaderCard> cards = new ArrayList<>();

			for (int j=4*i; j<(4*(i+1)); j++)
			{
				LeaderCard currLeaderCard = leaderCards.get(j);

				cards.add(currLeaderCard);
			}

			player.setLeaderCards(cards);

			i++;
		}
	}

	private void resetPlayers()
	{
		int i=0;
		for (Player p : players)
		{
			p.setCoins(5+i);
			i++;
			p.setServants(2);
			p.setVictoryPoints(0);
			p.setFaithPoints(0);
			p.setMilitaryPoints(0);
			p.setWoods(2);
			p.setStones(2);
			p.setHarvestValueModifier(0);
			p.setProductionValueModifier(0);
			p.setMilitaryPointsMalus(false);
			p.setCoinMalus(false);
			p.setServantMalus(false);
			p.setWoodMalus(false);
			p.setStoneMalus(false);
			p.setFamilyMemberMalus(false);
			p.setDisableMarket(false);
			p.setNoFirstAction(false);
			p.setNoMilitaryPointsForTerritories(false);
			p.setDontPayThreeCoinsInTowers(false);
			p.setDontCareOccupiedPlaces(false);
			p.setPlayWithThePope(false);
			p.setSantaRita(false);
			p.setSistoIV(false);
			p.setNewAction(false);
			p.setRemoveTowerBonus(false);
			p.setFamilyMembers(new ArrayList<>());
			p.setPlayerBoard(new PlayerBoard());
		}
	}

	public void excommunicationHandling(int playerCounter, int currentRoundNumber,
			 int era, List<ExcommunicationCard> excommunicationCards)
	{
		boolean firstExcommunication =
				((playerCounter < 5) && (currentRoundNumber==8)) || ((playerCounter == 5) && (currentRoundNumber) == 6);

		boolean secondExcommunication =
				(playerCounter < 5 && currentRoundNumber == 16) ||  (playerCounter == 5 && currentRoundNumber == 12);

		if (firstExcommunication || secondExcommunication)
		{
			for (Player p : players)
			{
				currentPlayer = p;

				chooseExcommunication(era, excommunicationCards, gameBoard);
			}
		}
	}

	private void chooseExcommunication
			(int era, List<ExcommunicationCard> excommunicationCards,
			 GameBoard gameBoard)
	{
		int faithPoints = GameBoardUtils.calculateFaithPointsFromEra(era);

		if (currentPlayer.getFaithPoints() < faithPoints)
		{
			excommunicate(currentPlayer, excommunicationCards, era, gameBoard);

			currentPlayer.getAdapter().printMessage
					(new CommunicationMessage("For this era you have been excommunicated"));
		}
		else
		{
			IOAdapter adapter = currentPlayer.getAdapter();

			adapter.printMessage(new ExcommunicationMessage(excommunicationCards.get(era)));

			if (adapter instanceof SocketIOAdapter)
				new Thread(new ReceiveExcommunicationDecisionThread(gameName)).start();

			Long timestamp = System.currentTimeMillis();

			Long timeout = GameMatch.getTimeout();

			while (System.currentTimeMillis() < timestamp + timeout)
			{
				try
				{
					Thread.sleep(100L);
				} catch (InterruptedException e)
				{
					LOGGER.log(Level.WARNING, "Interrupted!", e);
					Thread.currentThread().interrupt();
				}

				if (currentPlayer.getExcommunicationChoice() != null)
				{
					break;
				}
			}

			if (currentPlayer.getExcommunicationChoice() == 1)
			{
				currentPlayer.setFaithPoints(0);

				if (currentPlayer.isSistoIV())
				{
					Asset victoryBonus = new Asset(5, AssetType.VICTORYPOINT);

					currentPlayer.addAsset(victoryBonus);
				}
			}
			else
			{
				excommunicate(currentPlayer, excommunicationCards, era, gameBoard);

				currentPlayer.getAdapter().printMessage
						(new CommunicationMessage("For this era you have been excommunicated"));
			}

			currentPlayer.setExcommunicationChoice(null);
		}

	}
	
	public void endGameExcommunicationHandling
	(List<Player> players, List<ExcommunicationCard> excommunicationCards, GameBoard gameBoard)
	{
		for (Player p : players)
		{
			currentPlayer = p;
		
			chooseExcommunication(4, excommunicationCards, gameBoard);
		}
	}

	private void excommunicate(Player p, List<ExcommunicationCard> e, int era, GameBoard gameBoard)
	{
		int eraNumberInCard = era -2;
		
		for (Effect eff : e.get(eraNumberInCard).getEffects())
		{
			eff.executeEffects(p, gameBoard);
		}

	}

	private void assignExcommunicationCards()
	{

		Collections.shuffle(excommunicationCards);

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

	private void loadTimeOut()
	{
		String path = "users/Timeout.json";

		String timeOutString = fileLoader(path);

		JSONObject timeOut = new JSONObject(timeOutString);

		try {
			timeOutFromFile = TimeOutLoader.loadTimeOut(timeOut);

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {

			LOGGER.log(Level.INFO, "TimeOut Not Loaded", e);
		}

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

	
	private void addFamiliarsValue(Player player)
	{
		player.setFamiliarToPlayer(gameBoard.getDices());
	}
	
	private Player selectWinner(List<Player> players)
	{
		Collections.sort(players, new PlayerComparator());

		for (int i  = 0; i < players.size(); i++)
		{
			Player player = players.get(i);

			if (i == 0)
			{
				int victories = player.getNumberOfMatchWon() + 1;

				player.setNumberOfMatchWon(victories);
			}
			else
			{
				int matchesLost = player.getNumberOfMatchLost() + 1;

				player.setNumberOfMatchLost(matchesLost);
			}

		}

		return players.get(0);
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

	public static Long getTimeout()
	{
		return timeout;
	}

	public boolean isStopped() {
		return stopped;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName)
	{
		this.gameName = gameName;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public GameBoard getCurrentGameBoard() {
		return currentGameBoard;
	}

	public Effect getCurrEffect() {
		return currEffect;
	}

	public void setCurrEffect(Effect currEffect) {
		this.currEffect = currEffect;
	}

	public boolean isSerialized() {
		return serialized;
	}

	public void setSerialized(boolean serialized) {
		this.serialized = serialized;
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

	public void PlayerNotify(Player playerSuspended)
	{
		for (Player p : players)
		{
			if (!(p.equals(playerSuspended)))
			{
				IOAdapter adapter = p.getAdapter();

				adapter.printMessage(new CommunicationMessage("Player " + playerSuspended.getPlayerColorsEnum() + " is suspended"));
			}

		}
	}
}
