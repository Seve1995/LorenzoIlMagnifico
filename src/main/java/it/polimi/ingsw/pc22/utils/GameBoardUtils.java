package it.polimi.ingsw.pc22.utils;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBoardUtils {


	//FORSE NON È MEGLIO GESIRE ANCHE QUESTA COME UNA ENUM?
	private static final Map<Integer, Integer> fromFaithToVictory = new HashMap<Integer, Integer>();

	static
	{
		fromFaithToVictory.put(0, 0);
		fromFaithToVictory.put(1, 1);
		fromFaithToVictory.put(2, 2);
		fromFaithToVictory.put(3, 3);
		fromFaithToVictory.put(4, 4);
		fromFaithToVictory.put(5, 5);
		fromFaithToVictory.put(6, 7);
		fromFaithToVictory.put(7, 9);
		fromFaithToVictory.put(8, 11);
		fromFaithToVictory.put(9, 13);
		fromFaithToVictory.put(10, 15);
		fromFaithToVictory.put(11, 17);
		fromFaithToVictory.put(12, 19);
		fromFaithToVictory.put(13, 22);
		fromFaithToVictory.put(14, 25);
	}

	public static int CalculateVictoryPointsForFaithPoints(int faithPoint)
	{
		if (faithPoint > 14)
			return 25;
		return fromFaithToVictory.get(faithPoint);
	}

	private static final Map<Integer, Integer> fromEraToFaithPoints = new HashMap<>();

	static
	{
		fromEraToFaithPoints.put(1,3);
		fromEraToFaithPoints.put(2,4);
		fromEraToFaithPoints.put(3,5);
	}

	public static int calculateFaithPointsFromEra(int era)
	{
		if (era < 1 || era > 3)
			return -1;

		return fromEraToFaithPoints.get(era);
	}

	public static void resetPlayerStatus(List<Player> players)
	{
		for (Player player : players)
		{
			player.setFamiliarPositioned(false);
			player.setHasPassed(false);
		}
	}


	private static void chooseExcommunication(Player player, int era, List<ExcommunicationCard> excommunicationCards,
									   GameBoard gameBoard)
	{


		int faithPoints = calculateFaithPointsFromEra(era);

		if (player.getFaithPoints() < faithPoints)
		{
			excommunicate(player, excommunicationCards, era, gameBoard);
		}

		else
		{
			int choice = player.getAdapter().askExcommunication();

			if (choice == 1)
			{
				player.setFaithPoints(0);

				if (player.isSistoIV())
				{
					Asset victoryBonus = new Asset(5, AssetType.VICTORYPOINT);

					player.addAsset(victoryBonus);
				}

			}

			else
			{
				excommunicate(player, excommunicationCards, era, gameBoard);
			}
		}

	}

	public static void excommunicationHandling(List<Player> players,
			 int playerCounter, int currentRoundNumber, int era, List<ExcommunicationCard> excommunicationCards,
												GameBoard gameBoard)
	{


		if (((playerCounter < 5) && ((currentRoundNumber==8)) || ((playerCounter == 5) && (currentRoundNumber) == 6)))
		{
			for (Player p : players)
			{
				chooseExcommunication(p, era, excommunicationCards, gameBoard);
			}
		}

		if ((playerCounter < 5 && currentRoundNumber == 16) ||  (playerCounter == 5 && currentRoundNumber == 12))
		{
			for (Player p : players)
			{
				chooseExcommunication(p, era, excommunicationCards, gameBoard);
			}
		}
	}

	
	public static void endGameExcommunicatonHandling(List<Player> players, List<ExcommunicationCard> excommunicationCards,
													 GameBoard gameBoard, int era)
	{

		for (Player p : players)
		{
			chooseExcommunication(p, era, excommunicationCards, gameBoard);
		}

	}

	public static void setUpPlayers
		(List<Player> players, int playerCounter, List<BonusTile> tiles)
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

	/*public static int getEra(int currentRoundNumber, int playerCounter)
	{
		return EraCalc.getEraNumber(playerCounter, currentRoundNumber);
	}
	*/
	public static void printToPlayers(Player currPlayer, List<Player> players, GameBoard gameBoard, int era, int currentRoundNumber)
	{
		for (Player player : players)
		{
			IOAdapter adapter = player.getAdapter();
			
			/*adapter.printMessage(currPlayer.toString());
			
			adapter.printMessage(gameBoard.toString());
			
			adapter.printMessage(player.getPlayerBoard().toString());

			adapter.printMessage(player.toString());
			
			adapter.printMessage("Number of round:" + currentRoundNumber + "|Number of era:" + era);
			adapter.printMessage("It's " + currPlayer.getUsername() + " turn.");
			*/
			
		}
	}

	public static void purgeGameBoard(GameBoard gameBoard)
	{
		for (Tower t : gameBoard.getTowers())
		{
			for (TowerCell tc : t.getTowerCells())
			{
				tc.setFamilyMember(null);
			}
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

	private static void excommunicate(Player p, List<ExcommunicationCard> e, int era, GameBoard gameBoard)
	{

		for (Effect eff : e.get(era).getEffects())
		{
			eff.executeEffects(p, gameBoard);
		}

	}

	public static void sumFinalPoints(List<Player> players, GameBoard gameBoard)
	{
		int militaryPoints1 = 0;

		int militaryPoints2 = 0;

		for (Player p : players)
		{
			if (p.getPlayerBoard().getTerritories() != null)
			{
				int value = p.getPlayerBoard().getTerritories().size();

				TerritoriesCalc territoriesCalc = TerritoriesCalc.getTerritoryCalcByValue(value);

				int victoryPoints =  territoriesCalc.getVictoryPoints();

				p.setVictoryPoints(p.getVictoryPoints() + victoryPoints);
			}

			if (p.getPlayerBoard().getCharacters() != null)
			{
				int value = p.getPlayerBoard().getCharacters().size();

				CharactersCalc charactersCalc = CharactersCalc.getCharacterCalcByValue(value);

				int victoryPoints = charactersCalc.getVictoryPoints();

				p.setVictoryPoints(p.getVictoryPoints() + victoryPoints);

			}

			if (p.getPlayerBoard().getVentures() != null)
			{
				for (VentureCard v : p.getPlayerBoard().getVentures())
				{
					for (Effect e : v.getPermanentEffects())
					{
						e.executeEffects(p, gameBoard);
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

	private static int sumFinalResources(Player p)
	{
		return p.getCoins() + p.getServants() + p.getWoods() + p.getStones();
	}

	private static void AssignMilitaryBonus(List <Player> players, int m1, int m2)
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

}
