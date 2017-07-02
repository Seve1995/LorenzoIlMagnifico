package it.polimi.ingsw.pc22.adapters;

import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.connection.GameServer;
import it.polimi.ingsw.pc22.gamebox.ColorsEnum;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.messages.CommunicationMessage;
import it.polimi.ingsw.pc22.messages.ErrorMessage;
import it.polimi.ingsw.pc22.messages.Message;
import it.polimi.ingsw.pc22.messages.TimerMessage;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.utils.PlayerLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fandroid95 on 30/05/2017.
 */
public abstract class IOAdapter
{
    private Long timeout;

    public abstract void endConnection(Player player) throws IOException;

    public abstract void  printMessage(Message message);

    public abstract String getMessage();

    public Long getTimeout()
    {
        return timeout;
    }

    public void setTimeout(Long timeout)
    {
        this.timeout = timeout;
    }

    public FamilyMember askFamiliarMemberForBonus(Player player)
    {
        List<FamilyMember> familyMembers =
                player.getFamilyMembers();

        Long maxTimeStamp = System.currentTimeMillis() + timeout;

        while(System.currentTimeMillis() < maxTimeStamp)
        {
 		   StringBuilder sb = new StringBuilder("Choose the familiar member for the bonus:\n");

		   for (FamilyMember f : familyMembers)
			   sb.append(f.toString());

		    String choice = this.getMessage();

		    if (choice == null)
		        continue;

		    ColorsEnum color = ColorsEnum.getColorFromString(choice);

		    if (color == null)
		        continue;

		    FamilyMember member = player.getFamilyMemberByColor(color);

		    if (member == null)
		        continue;

		    return member;

        }

        printMessage(new TimerMessage("Timeout Azione terminato"));

        return null;
    }

    public Player authenticate(String authentication) throws IOException
    {
        Pattern loginPattern = Pattern.compile("(^(\\w+) (\\w+) (L|S|l|s)$)");

        Matcher matcher = loginPattern.matcher(authentication);

        if (!matcher.find())
        {
            printMessage(new ErrorMessage("Invalid INPUT"));

            return null;
        }

        String[] login = authentication.split(" ");

        Player player = null;

        if ("L".equalsIgnoreCase(login[2]))
        {
            player = loginPlayer(login[0],login[1]);
        }

        if ("S".equalsIgnoreCase(login[2]))
        {
            player = signUp(login[0], login[1]);

            updateJson();
        }

        if (player == null) return null;

        return player;
    }

    public boolean gameHandling(String choice) throws IOException
    {
        String[] playerString = choice.split(":");

        Map<String, Player> playerMap = GameServer.getPlayersMap();

        Player player = playerMap.get(playerString[0]);

        if (player == null)
        {
            printMessage(new ErrorMessage("Invalid INPUT no user found"));

            return false;
        }

        Pattern gameMatcher = Pattern.compile("(^(\\w+) (C|c|J|j|R|r)$)");

        Matcher matcher = gameMatcher.matcher(playerString[1]);

        if (!matcher.find())
        {
            printMessage(new ErrorMessage("Invalid INPUT"));

            return false;
        }

        String[] matchStrings = playerString[1].split(" ");

        Map<String, GameMatch> gameMatchMap = GameServer.getGameMatchMap();

        if("C".equalsIgnoreCase(matchStrings[1]))
        {
            return createNewGame
                    (gameMatchMap, matchStrings[0], player);
        }

        if("J".equalsIgnoreCase(matchStrings[1]))
        {
            return addToExistingGame
                    (gameMatchMap, matchStrings[0], player);
        }

        if ("R".equalsIgnoreCase(matchStrings[1]))
        {
            if (firstMatchFree(gameMatchMap).equals(null))
            {
                printMessage(new ErrorMessage("No matches available."));
            }

            return addToExistingGame(gameMatchMap, this.firstMatchFree(gameMatchMap), player);
        }

        printMessage(new ErrorMessage("Non-valid input. Please retry... "));

       return false;
    }

    private synchronized String firstMatchFree(Map<String, GameMatch> gameMatchMap)
    {
        for (Map.Entry<String, GameMatch> entry : gameMatchMap.entrySet())
        {
            if (entry.getValue().getPlayerCounter() < 5)
            {
                return entry.getKey();
            }

        }

        return null;
    }

    private synchronized boolean createNewGame
        (Map<String, GameMatch> gameMatchMap, String gameName, Player player)
            throws IOException
    {
        boolean existingGameMatch = gameMatchMap.containsKey(gameName);

        if (existingGameMatch)
        {
            printMessage(new ErrorMessage("A game match with the specified name already exists."));

            return false;
        }

        startNewGameMatch(gameName, player);

        printMessage(new CommunicationMessage("Player: " + player.getUsername() + " created GameMatch - " + gameName));

        return true;
    }

    private synchronized boolean addToExistingGame
        (Map<String, GameMatch> gameMatchMap, String gameName, Player player)
            throws IOException
    {
        boolean existingGameMatch = gameMatchMap.containsKey(gameName);

        if (!existingGameMatch)
        {
            printMessage(new ErrorMessage("Game match not found."));

            return false;
        }

        GameMatch gameMatch = gameMatchMap.get(gameName);

        //TODO FARE IN MODO CHE SI POSSA RILOGGARE

        if (gameMatch.getStarted())
        {
            printMessage(new ErrorMessage("Game already started"));

            return false;
        }

        if (gameMatch.getMaxPlayersNumber() == gameMatch.getPlayerCounter())
        {
            printMessage(new ErrorMessage("Game is full"));

            return false;
        }

        player.setAdapter(this);

        List<Player> players = gameMatch.getPlayers();

        players.add(player);

        gameMatch.setPlayers(players);

        int counter = gameMatch.getPlayerCounter() + 1;

        gameMatch.setPlayerCounter(counter);

        if (gameMatch.getPlayerCounter() == 2)
            new Thread(gameMatch).start();

        printMessage(new CommunicationMessage("Player: " + player.getUsername()
                + " joined GameMatch - " + gameName));

        return true;
    }


    private synchronized Player loginPlayer(String username, String password)
    {
        Map<String, Player> playerMap = GameServer.getPlayersMap();

        Player player = playerMap.get(username);

        if (player == null)
        {
            printMessage(new ErrorMessage("User not found"));

            return null;
        }

        if (!player.getPassword().equals(password))
        {
            printMessage(new ErrorMessage("Wrong password"));

            return null;
        }

        if (player.isLogged() && !player.isSuspended())
        {
            printMessage(new ErrorMessage("Invalid login"));

            return null;
        }

        player.setLogged(true);
        player.setSuspended(false);

        return player;
    }

    private synchronized Player signUp(String username, String password) throws IOException
    {
        Map<String, Player> playerMap = GameServer.getPlayersMap();

        Player player = playerMap.get(username);

        if (player != null)
        {
            printMessage(new ErrorMessage("Invalid login"));

            return null;
        }

        player = new Player(username, password, true);

        playerMap.put(player.getUsername(), player);

        return player;
    }

    //TODO FAR SI CHE I VALORI VENGANO GESTITI DAL PARSER JSON
    //TODO2 DISTINGUERE IL TIMEOUT DELL'AVVIO PARTITA DAL TIMEOUT DELL'AZIONE (ce ne vogliono 2 distinti)
    private void startNewGameMatch(String gameName, Player player)
    {
        GameMatch gameMatch = new GameMatch(40000L, 4); 

        Map<String, GameMatch> gameMatchMap = GameServer.getGameMatchMap();

        gameMatchMap.put(gameName, gameMatch);

        player.setAdapter(this);

        List<Player> players = new ArrayList<>();

        players.add(player);

        gameMatch.setPlayers(players);

        int counter = gameMatch.getPlayerCounter() + 1;

        gameMatch.setPlayerCounter(counter);
    }

    synchronized private void updateJson() throws IOException
    {
        Map<String, Player> playerMap = GameServer.getPlayersMap();

        PlayerLoader.refreshJson(playerMap);
    }

}