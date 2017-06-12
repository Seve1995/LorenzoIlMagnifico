package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.actions.Action;
import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.utils.*;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
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

	private Long timeout;

	private static final String BOARD_PATH = "boards/";
	
	public GameMatch(Long timeOut, int maxPlayersNumber)
	{
		this.timeout = timeOut;
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

		//loadLeaderCards();
		
        int turnNumber = 6 * playerCounter;

		//setDellecartescomunica

		GameBoardUtils.setUpPlayers(players, playerCounter, tiles);

		for (int currentRoundNumber = 0; currentRoundNumber < turnNumber; currentRoundNumber++)
		{
			if (isNewTurn(currentRoundNumber))
			{
				addDices();

				int era = GameBoardUtils.getEra(currentRoundNumber, playerCounter);

				addTowerCards(era);

				checkOrderTurn();

				//resetLeaderCards(players);

				GameBoardUtils.purgeGameBoard(gameBoard);

				resetFamiliars(players);
			}

			for(Player player : players)
			{
				if (isNewTurn(currentRoundNumber))
				{
					player.setFamiliarToPlayer(gameBoard.getDices());
				}

				IOAdapter adapter = player.getAdapter();

				adapter.printMessage(gameBoard.toString());

				adapter.printMessage(player.getPlayerBoard().toString());
				
				adapter.printMessage(player.toString());

				Long timestamp = System.currentTimeMillis();
				
				while (System.currentTimeMillis() < timestamp + timeout)
				{
					Action action = adapter.askAction(gameBoard, player);

					System.out.println("Action: " + action);

					if (action == null) continue;

					boolean executed = action.executeAction(player, gameBoard);

					if (player.isHasPassed()) break;

					if (executed) break;
				}

				adapter.printMessage(player.getPlayerBoard().toString());
				
				adapter.printMessage(player.toString());
			}

			GameBoardUtils.resetPlayerStatus(players);

			GameBoardUtils.excommunicationHandling();

		}

		GameBoardUtils.endGameExcommunicatonHandling();

		GameBoardUtils.sumFinalPoints(players, gameBoard); //check excommunication

		selectWinner(players);

		endGame();
		
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
				e.printStackTrace();
			}
		}
	}

	private void loadGameBoard()
	{
		String path = BOARD_PATH + "GameBoard-" + playerCounter + ".json";

		String boardString = fileLoader(path);

		JSONObject jsonBoard = new JSONObject(boardString);

		gameBoard = BoardLoader.loadGameBoard(jsonBoard);

		System.out.println(gameBoard);
	}

	private void loadCards()
	{
		String path = BOARD_PATH + "cards.json";

		String cardString = fileLoader(path);

		JSONObject jsonCards = new JSONObject(cardString);

		cards = CardLoader.loadCards(jsonCards);
		
		Collections.shuffle(cards);
	}

	private void loadExcommunicationCards()
	{
		String path = BOARD_PATH + "ExcommunicationCards.json";

		String excommunicationString = fileLoader(path);

		JSONObject cards = new JSONObject(excommunicationString);

		excommunicationCards =
				ExcommunicationCardLoader.loadExcomunicationCards(cards);
	}

	private void loadBonusTiles()
	{
		String path = BOARD_PATH + "BonusTiles.json";

		String bonusTilesString = fileLoader(path);

		JSONObject bonusTiles = new JSONObject(bonusTilesString);

		tiles = BonusTileLoader.loadBonusTiles(bonusTiles);

		System.out.println(tiles);
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
			e.printStackTrace();
		}

		return builder.toString();
	}

	private boolean isNewTurn(int currentNumber)
	{
		if (currentNumber==0) 
			return true;
		
		if (this.playerCounter==5)
			return currentNumber%3==0;
		
		return currentNumber%4==0;
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
		
		List<DevelopmentCard> territoryCards = cards.parallelStream()
				.filter(devCard -> (devCard.getRoundNumber() == roundNumber && devCard instanceof TerritoryCard))
				.collect(Collectors.toList())
				.subList(0, territoryTowerCells.size());

		cards.removeAll(territoryCards);

		for (int i=0; i < territoryTowerCells.size(); i++)
		{
			territoryTowerCells.get(i).setDevelopmentCard(territoryCards.get(i));
		}
	
		List<TowerCell> characterTowerCells = towers[1].getTowerCells();
		
		List<DevelopmentCard> characterCards = cards.parallelStream()
				.filter(devCard -> (devCard.getRoundNumber() == roundNumber && devCard instanceof CharacterCard))
				.collect(Collectors.toList())
				.subList(0, characterTowerCells.size());

		cards.removeAll(characterCards);

		for (int i=0; i<characterTowerCells.size(); i++)
		{
			characterTowerCells.get(i).setDevelopmentCard(characterCards.get(i));
		}

		List<TowerCell> buildingTowerCells = towers[2].getTowerCells();
		
		List<DevelopmentCard> buildingCards = cards.parallelStream()
				.filter(devCard -> (devCard.getRoundNumber() == roundNumber && devCard instanceof BuildingCard))
				.collect(Collectors.toList())
				.subList(0, buildingTowerCells.size());

		cards.removeAll(buildingCards);

		for (int i=0; i<buildingTowerCells.size(); i++)
		{
			buildingTowerCells.get(i).setDevelopmentCard(buildingCards.get(i));
		}
		
		List<TowerCell> ventureTowerCells = towers[3].getTowerCells();
		
		List<DevelopmentCard> ventureCards = cards.parallelStream()
				.filter(devCard -> (devCard.getRoundNumber() == roundNumber && devCard instanceof VentureCard))
				.collect(Collectors.toList())
				.subList(0, ventureTowerCells.size());

		cards.removeAll(ventureCards);
		
		for (int i=0; i<ventureTowerCells.size(); i++)
		{
			ventureTowerCells.get(i).setDevelopmentCard(ventureCards.get(i));
		}
	}

	private void resetFamiliars(List<Player> players)
	{
		for (Player p : players)
		{
			for (FamilyMember f : p.getFamilyMembers())
			{
				f.setPlayed(false);
			}
		}
	}
	
	private void resetLeaderCards(List<Player> players)
	{
		for (Player p : players)
		{
			for (LeaderCard l : p.getLeaderCards())
			{
				l.setFaceUp(true);
			}
		
		}
	}

	private void checkOrderTurn()
	{
		CouncilPalace palace = gameBoard.getCouncilPalace();

		List<Player> tempPlayers = palace.getPlayersInCouncilPalace();

		for (Player player : players)
		{
			if(tempPlayers.contains(player)) continue;

			tempPlayers.add(player);
		}

		players = tempPlayers;
	}

	private String selectWinner(List<Player> players)
	{
		int winner=0;
		
		String winnerName = null;
		
		for (Player p : players)
		{
			if(p.getVictoryPoints() > winner)
			{
				
				winner = p.getVictoryPoints(); 
			
				winnerName = p.getName(); 
			}
		}
		
		return winnerName;
		
		//stampalo a tutti i giocatori
	}

	public int getMaxPlayersNumber()
	{
		return maxPlayersNumber;
	}

	public Boolean getStarted() {
		return started;
	}

	public void setStarted(Boolean started) {
		this.started = started;
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
