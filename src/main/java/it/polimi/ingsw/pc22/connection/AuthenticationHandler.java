package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.adapters.GameAdapter;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.utils.UserLoader;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by fandroid95 on 30/05/2017.
 */
public abstract class AuthenticationHandler
{
    protected abstract void checkPassword(User user) throws IOException;

    protected abstract User getUserFromUserName() throws IOException;

    protected abstract User registerNewUser() throws IOException;

    protected User loginUser() throws IOException
    {
        User user = null;

        while(user == null)
        {
            user = getUserFromUserName();
        }

        while(!user.isLogged())
        {
            checkPassword(user);
        }

        return user;
    }

    synchronized protected User existingUsername(String username)
    {
        Map<String, User> usersMap = GameServer.getUsersMap();

        User user = usersMap.get(username);

        if (user == null) return null;

        if (user.isLogged()) return null;

        return  user;
    }

    protected User signUp() throws IOException
    {
        User user = null;

        while (user == null)
        {
            user = registerNewUser();
        }

        Map<String, User> usersMap = GameServer.getUsersMap();

        synchronized (usersMap)
        {
            usersMap.put(user.getUsername(), user);
        }

        return user;
    }

    //TODO FAR SI CHE I VALORI VENGANO GESTITI DAL PARSER JSON
    protected void startNewGameMatch(String gameName, Player player, GameAdapter gameAdapter)
    {
        GameMatch gameMatch = new GameMatch(15000L, 4);

        Map<String, GameMatch> gameMatchMap = GameServer.getGameMatchMap();

        gameMatchMap.put(gameName, gameMatch);

        Map<Player, GameAdapter> players = new TreeMap<>(new SocketAuthenticationHandler.PlayerComparator());

        players.put(player, gameAdapter);

        gameMatch.setPlayers(players);

        int counter = gameMatch.getPlayerCounter() + 1;

        gameMatch.setPlayerCounter(counter);

        new Thread(gameMatch).start();
    }

    synchronized protected void insertIntoExistingGameMatch
            (String gameName, Player player, GameAdapter gameAdapter)
    {
        Map<String, GameMatch> gameMatchMap = GameServer.getGameMatchMap();

        GameMatch gameMatch = gameMatchMap.get(gameName);

        Map<Player, GameAdapter> players = gameMatch.getPlayers();

        players.put(player, gameAdapter);

        gameMatch.setPlayers(players);

        int counter = gameMatch.getPlayerCounter() + 1;

        gameMatch.setPlayerCounter(counter);
    }

    synchronized protected void updateJson() throws IOException
    {
        Map<String, User> usersMap = GameServer.getUsersMap();

        UserLoader.refreshJson(usersMap);
    }

    public class PlayerComparator implements Comparator<Player>
    {
        @Override
        public int compare(Player o1, Player o2)
        {
            int value = Integer.compare(o1.getPriority(), o2.getPriority());

            if (value == 0) value = o1.getName().compareTo(o2.getName());

            return value;
        }
    }


}
