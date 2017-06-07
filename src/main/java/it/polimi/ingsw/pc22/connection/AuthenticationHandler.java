package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.adapters.GameAdapter;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.utils.UserLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fandroid95 on 30/05/2017.
 */
public abstract class AuthenticationHandler
{
    protected void authentication(GameAdapter adapter)
    {
        try
        {
            User user = null;

            while (user == null)
            {
                adapter.printMessage("Sign up or Login?");

                String answer = adapter.getMessage();

                if (answer == null)
                {
                    adapter.endConnection(null);

                    return;
                }

                switch (answer)
                {
                    case "login":

                        user = loginUser(adapter);

                        break;
                    case "sign up":

                        user = signUp(adapter);

                        break;

                    default:

                        adapter.printMessage("Non-valid input. Please retry... ");

                        break;
                }
            }

            String playerName = user.getUsername();

            Player player = new Player();

            player.setName(playerName);


            boolean gameHandling = false;

            while (!gameHandling)
            {
                adapter.printMessage("Choose an operation:\n"
                        + "(1) Create new game match\n"
                        + "(2) Join a friend's game match\n"
                        + "(3) Join a random game match"
                );

                String userChoice = adapter.getMessage();

                if ("1".equals(userChoice))
                {
                    gameHandling = createNewGame(player, adapter);
                }

                if ("2".equals(userChoice))
                {
                    gameHandling = checkExistingGame(player, adapter);
                }

                if ("3".equals(userChoice))
                {

                }
            }

            updateJson();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void checkPassword(User user, GameAdapter adapter) throws IOException
    {
        String password = adapter.getMessage();

        if (password == null)
        {
            adapter.printMessage("Timeout expired retry");

            user.setLogged(false);
        }

        if (user.getPassword().equals(password))
        {
            adapter.printMessage("Successful logged in!");

            user.setLogged(true);

            return;
        }

        adapter.printMessage("Error: wrong password! Please retry.");

        user.setLogged(false);
    }

    private User getUserFromUserName(GameAdapter adapter) throws IOException
    {
        adapter.printMessage("Type an existing username:");

        String username = adapter.getMessage();

        User user = existingUsername(username);

        if (user != null)
        {
            adapter.printMessage("Username OK. Now type the password:");

            return user;
        }

        adapter.printMessage("Error: username not found! Please retry.");

        return null;
    }

    private User registerNewUser(GameAdapter adapter) throws IOException
    {
        adapter.printMessage("Type an username:");
        String username = adapter.getMessage();

        adapter.printMessage("Type a password");

        String password = adapter.getMessage();

        Boolean invalidUsername =
                GameServer.getUsersMap().containsKey(username);

        if (invalidUsername)
        {
            adapter.printMessage("The specified username already exist! Please type a new username.");
            return null;
        }

        adapter.printMessage("User created!");

        return new User(username, password, true);
    }

    private boolean createNewGame(Player player, GameAdapter adapter) throws IOException
    {
        adapter.printMessage("Type a name for the new game match:");

        String gameName = adapter.getMessage();

        if (gameName == null) return false;

        adapter.printMessage("Game name: " + gameName);

        boolean existingGameMatch =
                GameServer.getGameMatchMap().containsKey(gameName);

        if (existingGameMatch)
        {
            adapter.printMessage("A game match with the specified name already exists.");

            return false;
        }

        startNewGameMatch(gameName, player, adapter);

        adapter.printMessage("Player: " + player.getName() + " created GameMatch - " + gameName);

        return true;
    }

    private boolean checkExistingGame(Player player, GameAdapter adapter) throws IOException
    {
        adapter.printMessage("Type the name of the chosen game match:");

        String gameName = adapter.getMessage();

        if (gameName == null) return false;

        adapter.printMessage("Game name: " + gameName);

        boolean existingGameMatch =
                GameServer.getGameMatchMap().containsKey(gameName);

        if (!existingGameMatch) {
            adapter.printMessage("Game match not found.");

            return false;
        }

        insertIntoExistingGameMatch(gameName, player, adapter);

        adapter.printMessage("Player: " + player.getName() + " joined GameMatch - " + gameName);

        return true;
    }


    private User loginUser(GameAdapter adapter) throws IOException
    {
        User user = null;

        while(user == null)
        {
            user = getUserFromUserName(adapter);
        }

        while(!user.isLogged())
        {
            checkPassword(user, adapter);
        }

        return user;
    }

    synchronized private User existingUsername(String username)
    {
        Map<String, User> usersMap = GameServer.getUsersMap();

        User user = usersMap.get(username);

        if (user == null) return null;

        if (user.isLogged()) return null;

        return  user;
    }

    private User signUp(GameAdapter adapter) throws IOException
    {
        User user = null;

        while (user == null)
        {
            user = registerNewUser(adapter);
        }

        Map<String, User> usersMap = GameServer.getUsersMap();

        synchronized (usersMap)
        {
            usersMap.put(user.getUsername(), user);
        }

        return user;
    }

    //TODO FAR SI CHE I VALORI VENGANO GESTITI DAL PARSER JSON
    private void startNewGameMatch(String gameName, Player player, GameAdapter gameAdapter)
    {
        GameMatch gameMatch = new GameMatch(15000L, 4);

        Map<String, GameMatch> gameMatchMap = GameServer.getGameMatchMap();

        gameMatchMap.put(gameName, gameMatch);

        player.setAdapter(gameAdapter);

        List<Player> players = new ArrayList<>();

        players.add(player);

        gameMatch.setPlayers(players);

        int counter = gameMatch.getPlayerCounter() + 1;

        gameMatch.setPlayerCounter(counter);
    }


    synchronized private void insertIntoExistingGameMatch
            (String gameName, Player player, GameAdapter gameAdapter)
    {
        Map<String, GameMatch> gameMatchMap = GameServer.getGameMatchMap();

        GameMatch gameMatch = gameMatchMap.get(gameName);

        player.setAdapter(gameAdapter);

        List<Player> players = gameMatch.getPlayers();

        players.add(player);

        gameMatch.setPlayers(players);

        int counter = gameMatch.getPlayerCounter() + 1;

        gameMatch.setPlayerCounter(counter);

        if (gameMatch.getPlayerCounter() == 2)
            new Thread(gameMatch).start();

    }

    synchronized private void updateJson() throws IOException
    {
        Map<String, User> usersMap = GameServer.getUsersMap();

        UserLoader.refreshJson(usersMap);
    }

}
