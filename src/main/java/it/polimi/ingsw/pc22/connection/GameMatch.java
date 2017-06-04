package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.adapters.GameAdapter;
import it.polimi.ingsw.pc22.gamebox.BonusTile;
import it.polimi.ingsw.pc22.gamebox.BuildingCard;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.gamebox.CharacterCard;
import it.polimi.ingsw.pc22.gamebox.ColorsEnum;
import it.polimi.ingsw.pc22.gamebox.DevelopmentCard;
import it.polimi.ingsw.pc22.gamebox.Dice;
import it.polimi.ingsw.pc22.gamebox.ExcommunicationCard;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.gamebox.PlayerColorsEnum;
import it.polimi.ingsw.pc22.gamebox.TerritoryCard;
import it.polimi.ingsw.pc22.gamebox.Tower;
import it.polimi.ingsw.pc22.gamebox.TowerCell;
import it.polimi.ingsw.pc22.gamebox.VentureCard;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.utils.BoardLoader;
import it.polimi.ingsw.pc22.utils.BonusTileLoader;
import it.polimi.ingsw.pc22.utils.CardLoader;
import it.polimi.ingsw.pc22.utils.ExcommunicationCardLoader;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameMatch implements Runnable
{	
	private String gameName;

	private List<Player> players;
	
	private int playerCounter = 0;
	
	private int maxPlayersNumber;

	private GameBoard gameBoard;

	private List<DevelopmentCard> cards;

	private List<BonusTile> tiles;

	private List<ExcommunicationCard> excommunicationCards;

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
		
		startGame();

		endGame();
	}

	private void startGame()
	{
		loadGameBoard();

		loadCards();

		loadExcommunicationCards();

		loadBonusTiles();

		//loadLeaderCards();
		
        //int turnNumber = 6 *

		//loadresourcestoplayers

		//loadBonusTilestoplayers

		//setDellecartescomunica

		//INIZIO PARTITA SU BASE CONNESSIONE
		
		int coins = 5;
		for (int i=0; i<playerCounter; i++)
		{
			Player p = players.get(i);
			p.setCoins(coins);
			p.setWoods(2);
			p.setStones(2);
			p.setServants(3);
			p.setFamilyMember(new ArrayList<FamilyMember>());
			p.setPriority(i);
			coins++;
			switch (i) {
			case 0:
				p.setPlayerColorsEnum(PlayerColorsEnum.RED);
				break;
			case 1:
				p.setPlayerColorsEnum(PlayerColorsEnum.BLUE);
				break;
			case 2:
				p.setPlayerColorsEnum(PlayerColorsEnum.YELLOW);
				break;
			case 3:
				p.setPlayerColorsEnum(PlayerColorsEnum.GREEN);
				break;
			case 4:
				p.setPlayerColorsEnum(PlayerColorsEnum.BROWN);
				break;
			default:
				break;
			}
		}
		
		for (int currentRoundNumber=0; currentRoundNumber < 24; currentRoundNumber++)
		{
			if (isNewTurn(currentRoundNumber)) 
				{
				addDices();
				addTowerCards(getEra(currentRoundNumber));
				//Controlla il council palace 
				//ordina l'array in base a player.priority
				}
			
			for(Player player : players)
			{
				
				
				//pensare a timeout

				while(true)
				{

					//stampa gameBoard

					//stampa plancia

					player.getAdapter().askAction(); //Richiesta: che razza di azione vuoi fare??

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
		String path = BOARD_PATH + "VentureCards.json";

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
		ArrayList<Dice> dices = new ArrayList<Dice>();
		Dice blackDice = new Dice(ColorsEnum.BLACK);
		blackDice.rollingDice();
		dices.add(blackDice);
		Dice whiteDice = new Dice(ColorsEnum.WHITE);
		whiteDice.rollingDice();
		dices.add(whiteDice);
		if(this.playerCounter==5)
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
		
		List<DevelopmentCard> territoryCards = cards.parallelStream()
				.filter(devCard -> (devCard.getRoundNumber() == roundNumber && devCard instanceof TerritoryCard))
				.collect(Collectors.toList())
				.subList(0, 3);
		cards.removeAll(territoryCards);
		List<TowerCell> territoryTowerCells = towers[0].getTowerCells();
		for (int i=0; i<territoryTowerCells.size(); i++)
			{
			territoryTowerCells.get(i).setDevelopmentCard(territoryCards.get(i));
			}
		
		List<DevelopmentCard> characterCards = cards.parallelStream()
				.filter(devCard -> (devCard.getRoundNumber() == roundNumber && devCard instanceof CharacterCard))
				.collect(Collectors.toList());;
		cards.removeAll(characterCards);
		List<TowerCell> characterTowerCells = towers[0].getTowerCells();
		for (int i=0; i<characterTowerCells.size(); i++)
			{
			characterTowerCells.get(i).setDevelopmentCard(characterCards.get(i));
			}

		List<DevelopmentCard> buildingCards = cards.parallelStream()
				.filter(devCard -> (devCard.getRoundNumber() == roundNumber && devCard instanceof BuildingCard))
				.collect(Collectors.toList());;
		cards.removeAll(buildingCards);
		List<TowerCell> buildingTowerCells = towers[0].getTowerCells();
		for (int i=0; i<buildingTowerCells.size(); i++)
			{
			buildingTowerCells.get(i).setDevelopmentCard(buildingCards.get(i));
			}
		
		List<DevelopmentCard> ventureCards = cards.parallelStream()
				.filter(devCard -> (devCard.getRoundNumber() == roundNumber && devCard instanceof VentureCard))
				.collect(Collectors.toList());;
		cards.removeAll(ventureCards);
		List<TowerCell> ventureTowerCells = towers[0].getTowerCells();
		for (int i=0; i<ventureTowerCells.size(); i++)
			{
			ventureTowerCells.get(i).setDevelopmentCard(ventureCards.get(i));
			}
		
	}
		
		
	
	
	private int getEra(int currentRoundNumber)
	{
		//Valori di currentRoundNumber associati alle ere:
		//FINO A 4 GIOCATORI:
		//0,4 = 1^ era
		//8, 12 = 2^ era
		//16, 20 = 3^ era
		//A 5 GIOCATORI:
		//0,3 = 1^ era
		//6,9 = 2^ era
		//12,15 = 3^ era
		if(playerCounter < 5)
			{
			if(currentRoundNumber==0 || currentRoundNumber==4)
				return 1;
			if(currentRoundNumber==8 || currentRoundNumber==12)
				return 2;
			if(currentRoundNumber==16 || currentRoundNumber==20)
				return 3;
			}
		else
			if(currentRoundNumber==0 || currentRoundNumber==3)
				return 1;
			if(currentRoundNumber==6 || currentRoundNumber==9)
				return 2;
			if(currentRoundNumber==12 || currentRoundNumber==15)
				return 3;
		return -1;
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
