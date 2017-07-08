package it.polimi.ingsw.pc22.utils;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.effects.PickCouncilPrivilege;
import it.polimi.ingsw.pc22.gamebox.*;
import it.polimi.ingsw.pc22.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This class has several useful methods for the game board and
 * for the calculation of the final victory points.
 * It maps the number of a single player faith points
 * to victory points he/she will gain at the end of the match.
 * It also has methods to set up all the things for the game
 * and to purge the game board at the end of every single turn.
 *
 */

public class GameBoardUtils
{
	private static Player currentPlayer;

	public static Player getCurrentPlayer()
	{
		return currentPlayer;
	}

	private static final Logger LOGGER = Logger.getLogger(GameBoardUtils.class.getName());

	private static final Map<Integer, Integer> fromFaithToVictory = new HashMap<>();

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
		fromEraToFaithPoints.put(2,3);
		fromEraToFaithPoints.put(3,4);

		//TODO SISTAMERE STA COSA
		fromEraToFaithPoints.put(4,5);
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




	public static void endGameExcommunicationHandling
			(List<Player> players, List<ExcommunicationCard> excommunicationCards, GameBoard gameBoard, int era)
	{
		for (Player p : players)
		{
			currentPlayer = p;

			//chooseExcommunication(era, excommunicationCards, gameBoard);
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

	public static void purgeGameBoard(GameBoard gameBoard)
	{
		for (Tower t : gameBoard.getTowers())
		{
			t.setListPlayers(new ArrayList<>());

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

			List<Effect> effects = cp.getEffects();

			for (Effect effect : effects)
			{
				if (effect instanceof PickCouncilPrivilege)
				{
					((PickCouncilPrivilege) effect).setChosenAssets(null);
				}
			}
		}

		gameBoard.getCouncilPalace().setPlayersInCouncilPalace(new ArrayList<>());
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
