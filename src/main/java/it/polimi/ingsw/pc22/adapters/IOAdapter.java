package it.polimi.ingsw.pc22.adapters;

import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.connection.GameServer;
import it.polimi.ingsw.pc22.messages.CommunicationMessage;
import it.polimi.ingsw.pc22.messages.ErrorMessage;
import it.polimi.ingsw.pc22.messages.Message;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.utils.PlayerLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is an abstract class. To manage the double protocol
 * of connection we used this adapter, that stands between server and
 * client.
 * In particular, in this class we included some functionalities, that are the same,
 * even for socket-connection and rmi-connection, such as:
 * authentication (sign up and login), join and creation of a game.
 * Also we included a method that handles the starting of a new match.
 */
public abstract class IOAdapter
{
    private Long timeout;

    private Long timeForStart;

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
            if (firstMatchFree(gameMatchMap) == null)
            {
                printMessage(new ErrorMessage("No matches available."));

                return false;
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

        player.setSuspended(false);

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

        boolean contained = gameMatch.getPlayers().contains(player);

        if (gameMatch.getStarted() && !contained)
        {
            printMessage(new ErrorMessage("Game already started"));

            return false;
        }

        if (!contained && gameMatch.getMaxPlayersNumber() == gameMatch.getPlayerCounter())
        {
            printMessage(new ErrorMessage("Game is full"));

            return false;
        }

        if (contained && !gameMatch.isSerialized())
        {
            player.setSuspended(false);

            return true;
        }

        if (contained && gameMatch.isSerialized())
        {
            player.setAdapter(this);

            player.setSuspended(false);

            Long count = gameMatch.getPlayers().stream().filter(p-> !p.isSuspended()).count();

            if (count == gameMatch.getPlayerCounter())
            {
                new Thread(gameMatch).start();
            }

            return true;
        }

        player.setAdapter(this);
        player.setSuspended(false);

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



    private void startNewGameMatch(String gameName, Player player)
    {
        GameMatch gameMatch = new GameMatch(timeout, 5, timeForStart);

        Map<String, GameMatch> gameMatchMap = GameServer.getGameMatchMap();

        gameMatchMap.put(gameName, gameMatch);

        gameMatch.setGameName(gameName);

        player.setAdapter(this);

        List<Player> players = new ArrayList<>();

        players.add(player);

        gameMatch.setPlayers(players);

        int counter = gameMatch.getPlayerCounter() + 1;

        gameMatch.setPlayerCounter(counter);
    }

    public void updateJson() throws IOException
    {
        Map<String, Player> playerMap = GameServer.getPlayersMap();
        
        synchronized (playerMap) {
        	PlayerLoader.refreshJson(playerMap);
		}
    }

}