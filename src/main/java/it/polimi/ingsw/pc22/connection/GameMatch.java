package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.actions.Action;
import it.polimi.ingsw.pc22.adapters.GameAdapter;
import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.utils.BoardLoader;
import it.polimi.ingsw.pc22.utils.BonusTileLoader;
import it.polimi.ingsw.pc22.utils.CardLoader;
import it.polimi.ingsw.pc22.utils.ExcommunicationCardLoader;
import it.polimi.ingsw.pc22.utils.GameBoardUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
		
		while(true)
		{
			try
			{
				Thread.sleep(1000L);
			}
				catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			boolean isTimeoutExpired =
					System.currentTimeMillis() > timeStamp + timeout;

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
		
        int turnNumber = 6 * playerCounter;

		//setDellecartescomunica

		setUpPlayers();

		for (int currentRoundNumber=0; currentRoundNumber < turnNumber; currentRoundNumber++)
		{
			if (isNewTurn(currentRoundNumber))
			{
				addDices();

				addTowerCards(getEra(currentRoundNumber));

				checkOrderTurn();

				//resetLeaderCards(players);

				epurateGameBoard(gameBoard);

				resetFamiliars(players);
			}

			for(Player player : players)
			{
				if (isNewTurn(currentRoundNumber))
				{
					player.setFamiliarToPlayer(gameBoard.getDices());
				}

				GameAdapter adapter = player.getAdapter();

				adapter.printMessage(gameBoard.toString());

				adapter.printMessage(player.getPlayerBoard().toString());
				
				adapter.printMessage(player.toString());

				Long timestamp = System.currentTimeMillis();

				while (System.currentTimeMillis() < timestamp + timeout)
				{
					FamilyMember familyMember = adapter.askFamiliarMember(player, timeout);

					System.out.println("Familiar: " + familyMember);

					if (familyMember == null) continue;

					Asset servants = adapter.askServants(player, timeout);

					System.out.println("Familiar: " + servants);

					if (servants == null) continue;

					Action action = adapter.askAction(familyMember, servants, timeout);

					System.out.println("Familiar: " + action);

					boolean executed = action.executeAction(player, gameBoard);
					
					

					if (!executed) continue;
				}

				adapter.printMessage(player.getPlayerBoard().toString());
			}


			// se é la fine di una era
			if (((playerCounter < 5) && ((currentRoundNumber==8)) || ((playerCounter == 5) && (currentRoundNumber) == 6)))
			{
				for (Player p : players)
				{
					if ((p.getFaithPoints() < 3))
						
						excommunicate(p, excommunicationCards, 1);
					
					else 
					{
						//ask if they want to be excommunicated
						
						if (true) //se accetta la scomunica
						{
							p.setFaithPoints(0);	
						}

						else 
						{
							//excommunicate(p, )
						}
					}
				}
			}

			if ((playerCounter < 5 && currentRoundNumber == 16) ||  (playerCounter == 5 && currentRoundNumber == 12))
			{
				for (Player p : players)
				{
					if (p.getFaithPoints() < 4)

						excommunicate(p, excommunicationCards, 2);

					else
					{
						//ask if they want to be excommunicated

						if (true) //se accetta la scomunica
						{
							p.setFaithPoints(0);
						}

						else
						{
							//excommunicate(p, )
						}
					}
				}
			}
		}

		for (Player p : players)
		{
			if (p.getFaithPoints() < 5)

				excommunicate(p, excommunicationCards, 3);

			else //ask if you want to be excommunicated
			{
				if (true) //se accetta la scomunica
				{
					p.setFaithPoints(0);
				}

				else
				{
					//excommunicate..

				}

			}

		}
	
		sumFinalPoints(players); //check excommunication
		
		selectWinner(players);

		endGame();
		
	}


	private void setUpPlayers()
	{
		int coins = 5;

		for (int i=0; i < playerCounter; i++)
		{
			Player player = players.get(i);
			player.setCoins(coins);

			PlayerBoard playerBoard = player.getPlayerBoard();

			playerBoard.setBonusTile(tiles.get(i));

			player.setPriority(i);

			PlayerColorsEnum color =
					PlayerColorsEnum.getColorByValue(i);

			player.setPlayerColorsEnum(color);

			System.out.println(color.toString());

			coins++;
		}
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
		
		List<DevelopmentCard> territoryCards = cards.parallelStream()
				.filter(devCard -> (devCard.getRoundNumber() == roundNumber && devCard instanceof TerritoryCard))
				.collect(Collectors.toList())
				.subList(0, 4);

		cards.removeAll(territoryCards);

		List<TowerCell> territoryTowerCells = towers[0].getTowerCells();

		for (int i=0; i < territoryTowerCells.size(); i++)
		{
			territoryTowerCells.get(i).setDevelopmentCard(territoryCards.get(i));
		}
		
		List<DevelopmentCard> characterCards = cards.parallelStream()
				.filter(devCard -> (devCard.getRoundNumber() == roundNumber && devCard instanceof CharacterCard))
				.collect(Collectors.toList())
				.subList(0, 4);

		cards.removeAll(characterCards);

		List<TowerCell> characterTowerCells = towers[1].getTowerCells();
		for (int i=0; i<characterTowerCells.size(); i++)
			{
			characterTowerCells.get(i).setDevelopmentCard(characterCards.get(i));
			}

		List<DevelopmentCard> buildingCards = cards.parallelStream()
				.filter(devCard -> (devCard.getRoundNumber() == roundNumber && devCard instanceof BuildingCard))
				.collect(Collectors.toList())
				.subList(0, 4);

		cards.removeAll(buildingCards);

		List<TowerCell> buildingTowerCells = towers[2].getTowerCells();
		for (int i=0; i<buildingTowerCells.size(); i++)
			{
			buildingTowerCells.get(i).setDevelopmentCard(buildingCards.get(i));
			}
		
		List<DevelopmentCard> ventureCards = cards.parallelStream()
				.filter(devCard -> (devCard.getRoundNumber() == roundNumber && devCard instanceof VentureCard))
				.collect(Collectors.toList())
				.subList(0, 4);

		cards.removeAll(ventureCards);

		List<TowerCell> ventureTowerCells = towers[3].getTowerCells();
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
	
	
	private void epurateGameBoard(GameBoard gameBoard)
	{
		for (Tower t : gameBoard.getTowers())
			for(TowerCell tc : t.getTowerCells())
			{
				tc.setFamilyMember(null);
			}
		
		for (HarvestCell hc : gameBoard.getHarvest().getHarvestCell())
		{
			hc.setFamilyMember(null);
		}
		
		for (ProductionCell p : gameBoard.getProduction().getProductionCell())
		{
			p.setFamilyMember(null);
		}
		
		for (MarketCell m : gameBoard.getMarket().getMarketCells())
		{
			m.setFamilyMember(null);
		}
		
		for (CouncilPalaceCell cp : gameBoard.getCouncilPalace().getCouncilPalaceCells())
		{
			cp.setFamilyMember(null);
		}

		gameBoard.getCouncilPalace().setPlayersInCouncilPalace(new ArrayList<>());
	
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
	
	private void excommunicate(Player p, List<ExcommunicationCard> e, int era)
	{
		
		for (Effect eff : e.get(era-1).getEffects())
		{
			eff.executeEffect(p, gameBoard);
		}
	
	}
	
	private int sumFinalResources(Player p)
	{
		
		return p.getCoins()+p.getServants()+p.getWoods()+p.getStones();
		
	}
	
	
	private void sumFinalPoints(List<Player> players)
	{
		int militaryPoints1 = 0;

		int militaryPoints2 = 0;

		for (Player p : players)
		{

			if (p.getPlayerBoard().getTerritories() != null )
			{

				if (p.getPlayerBoard().getTerritories().size() == 3)

					p.setVictoryPoints(p.getVictoryPoints() + 1);

				if (p.getPlayerBoard().getTerritories().size() == 4)

					p.setVictoryPoints(p.getVictoryPoints() + 4);

				if (p.getPlayerBoard().getTerritories().size() == 5)

					p.setVictoryPoints(p.getVictoryPoints() + 10);

				if (p.getPlayerBoard().getTerritories().size() == 6)

					p.setVictoryPoints(p.getVictoryPoints() + 20);

			}
			
			if (p.getPlayerBoard().getCharacters() != null)
			{

				if (p.getPlayerBoard().getCharacters().size() == 1)

					p.setVictoryPoints(p.getVictoryPoints() + 1);

				if (p.getPlayerBoard().getCharacters().size() == 2)

					p.setVictoryPoints(p.getVictoryPoints() + 3);

				if (p.getPlayerBoard().getCharacters().size() == 3)

					p.setVictoryPoints(p.getVictoryPoints() +6);

				if (p.getPlayerBoard().getCharacters().size() == 4)

					p.setVictoryPoints(p.getVictoryPoints() + 10);

				if (p.getPlayerBoard().getCharacters().size() == 5)

					p.setVictoryPoints(p.getVictoryPoints() + 15);

				if (p.getPlayerBoard().getCharacters().size() == 6)

					p.setVictoryPoints(p.getVictoryPoints() + 21);
			}

			if (p.getPlayerBoard().getVentures() != null)
			{
			
				for (VentureCard v : p.getPlayerBoard().getVentures())
				{
					for (Effect e : v.getPermanentEffects())
					{
						e.executeEffect(p, gameBoard);
					}

				}
			
			}

			p.setVictoryPoints(p.getVictoryPoints() + p.getEndGameVictoryPoints());

			p.setVictoryPoints(p.getVictoryPoints()+(sumFinalResources(p)/5));

			if (p.getMilitaryPoints() >= militaryPoints1)

				militaryPoints1 = p.getMilitaryPoints();

			if (p.getMilitaryPoints() >= militaryPoints2 && p.getMilitaryPoints() < militaryPoints1)
				
				militaryPoints2 = p.getMilitaryPoints();

			p.setVictoryPoints(p.getVictoryPoints() +  GameBoardUtils.CalculateVictoryPointsForFaithPoints(p.getFaithPoints()));

		}
		
		AssignMilitaryBonus(players, militaryPoints1, militaryPoints2);

	}

	private void AssignMilitaryBonus(List <Player> players, int m1, int m2)
	{
		for (Player p : players)
		{
			if (p.getMilitaryPoints() == m1)
			{
				p.setVictoryPoints(p.getVictoryPoints() + 5);
			}

			if (p.getMilitaryPoints() == m2)
			{
				p.setVictoryPoints(p.getVictoryPoints() + 2);
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
