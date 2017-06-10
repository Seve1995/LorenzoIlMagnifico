package it.polimi.ingsw.pc22.adapters;

import it.polimi.ingsw.pc22.actions.Action;
import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.connection.GameServer;
import it.polimi.ingsw.pc22.connection.User;
import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.utils.UserLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fandroid95 on 30/05/2017.
 */
public abstract class IOAdapter
{
    public abstract void endConnection(Player player) throws IOException;

    public abstract void  printMessage(String message);

    public abstract String getMessage();

    public abstract Action askAction(FamilyMember familyMember, Asset servant ,Long timeout);

    public abstract int askFloor();

    public abstract CardTypeEnum askForCardType();

    public abstract FamilyMember askFamiliarMember(Player player, Long timeout);

    public abstract Asset askServants(Player player, Long timeout);

    public abstract List<Asset> chooseAssets(int numberOfAssets);
    
    protected void authentication()
    {
        try
        {
            User user = null;

            while (user == null)
            {
                printMessage("Sign up or Login?");

                String answer = getMessage();

                if (answer == null)
                {
                    endConnection(null);

                    return;
                }

                switch (answer)
                {
                    case "login":

                        user = loginUser();

                        break;
                    case "sign up":

                        user = signUp();

                        break;

                    default:

                        printMessage("Non-valid input. Please retry... ");

                        break;
                }
            }

            String playerName = user.getUsername();

            Player player = new Player();

            player.setName(playerName);


            boolean gameHandling = false;

            while (!gameHandling)
            {
                printMessage("Choose an operation:\n"
                        + "(1) Create new game match\n"
                        + "(2) Join a friend's game match\n"
                        + "(3) Join a random game match"
                );

                String userChoice = getMessage();

                if ("1".equals(userChoice))
                {
                    gameHandling = createNewGame(player);
                }

                if ("2".equals(userChoice))
                {
                    gameHandling = checkExistingGame(player);
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

    protected void checkPassword(User user) throws IOException
    {
        String password = getMessage();

        if (password == null)
        {
            printMessage("Timeout expired retry");

            user.setLogged(false);
        }

        if (user.getPassword().equals(password))
        {
            printMessage("Successful logged in!");

            user.setLogged(true);

            return;
        }

        printMessage("Error: wrong password! Please retry.");

        user.setLogged(false);
    }

    protected User getUserFromUserName() throws IOException
    {
        printMessage("Type an existing username:");

        String username = getMessage();

        User user = existingUsername(username);

        if (user != null)
        {
            printMessage("Username OK. Now type the password:");

            return user;
        }

        printMessage("Error: username not found! Please retry.");

        return null;
    }

    protected User registerNewUser() throws IOException
    {
        printMessage("Type an username:");
        String username = getMessage();

        printMessage("Type a password");

        String password = getMessage();

        Boolean invalidUsername =
                GameServer.getUsersMap().containsKey(username);

        if (invalidUsername)
        {
            printMessage("The specified username already exist! Please type a new username.");
            return null;
        }

        printMessage("User created!");

        return new User(username, password, true);
    }

    protected boolean createNewGame(Player player) throws IOException
    {
        printMessage("Type a name for the new game match:");

        String gameName = getMessage();

        if (gameName == null) return false;

        printMessage("Game name: " + gameName);

        boolean existingGameMatch =
                GameServer.getGameMatchMap().containsKey(gameName);

        if (existingGameMatch)
        {
            printMessage("A game match with the specified name already exists.");

            return false;
        }

        startNewGameMatch(gameName, player);

        printMessage("Player: " + player.getName() + " created GameMatch - " + gameName);

        return true;
    }

    protected boolean checkExistingGame(Player player) throws IOException
    {
        printMessage("Type the name of the chosen game match:");

        String gameName = getMessage();

        if (gameName == null) return false;

        printMessage("Game name: " + gameName);

        boolean existingGameMatch =
                GameServer.getGameMatchMap().containsKey(gameName);

        if (!existingGameMatch) {
            printMessage("Game match not found.");

            return false;
        }

        insertIntoExistingGameMatch(gameName, player);

        printMessage("Player: " + player.getName() + " joined GameMatch - " + gameName);

        return true;
    }


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
    protected void startNewGameMatch(String gameName, Player player)
    {
        GameMatch gameMatch = new GameMatch(15000L, 4);

        Map<String, GameMatch> gameMatchMap = GameServer.getGameMatchMap();

        gameMatchMap.put(gameName, gameMatch);

        player.setAdapter(this);

        List<Player> players = new ArrayList<>();

        players.add(player);

        gameMatch.setPlayers(players);

        int counter = gameMatch.getPlayerCounter() + 1;

        gameMatch.setPlayerCounter(counter);
    }


    synchronized protected void insertIntoExistingGameMatch
            (String gameName, Player player)
    {
        Map<String, GameMatch> gameMatchMap = GameServer.getGameMatchMap();

        GameMatch gameMatch = gameMatchMap.get(gameName);

        player.setAdapter(this);

        List<Player> players = gameMatch.getPlayers();

        players.add(player);

        gameMatch.setPlayers(players);

        int counter = gameMatch.getPlayerCounter() + 1;

        gameMatch.setPlayerCounter(counter);

        if (gameMatch.getPlayerCounter() == 2)
            new Thread(gameMatch).start();

    }

    synchronized protected void updateJson() throws IOException
    {
        Map<String, User> usersMap = GameServer.getUsersMap();

        UserLoader.refreshJson(usersMap);
    }
}
