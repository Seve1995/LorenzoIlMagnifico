package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.adapters.RMIGameAdapter;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.rmi.RMIAuthenticationService;
import it.polimi.ingsw.pc22.rmi.RMIClientStreamService;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Logger;

public class RMIAuthenticationServiceImpl extends AuthenticationHandler implements RMIAuthenticationService
{
    RMIClientStreamService streamService;
    Registry registry;

    private static final Logger logger = Logger.getLogger(RMIAuthenticationServiceImpl.class.getName());

    public RMIAuthenticationServiceImpl(Registry registry)
    {
        this.registry = registry;
    }

    @Override
    public void login() throws RemoteException
    {
        try
        {
            streamService = (RMIClientStreamService) registry.lookup("client");

            User user = null;

            while (user == null)
            {
                streamService.printMessage("Sign up or Login?");

                String answer = streamService.getMessage();

                switch (answer)
                {
                    case "login":

                        user = loginUser();

                        break;
                    case "sign up":

                        user = signUp();

                        break;

                    default:

                        streamService.printMessage("Non-valid input. Please retry... ");

                        break;
                }
            }


            String playerName = user.getUsername();

            Player player = new Player();

            player.setName(playerName);


            boolean gameHandling = false;

            while (!gameHandling)
            {
                streamService.printMessage("Choose an operation:\n"
                        + "(1) Create new game match\n"
                        + "(2) Join a friend's game match\n"
                        + "(3) Join a random game match"
                );

                String userChoice = streamService.getMessage();

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
        catch (NotBoundException | IOException e)
        {
           logger.info(e.getMessage());
        }

    }

    @Override
    protected void checkPassword(User user) throws IOException
    {
        String password = streamService.getMessage();

        if (user.getPassword().equals(password))
        {
            streamService.printMessage("Successful logged in!");

            user.setLogged(true);

            return;
        }

        streamService.printMessage("Error: wrong password! Please retry.");

        user.setLogged(false);
    }

    @Override
    protected User getUserFromUserName() throws IOException
    {
        streamService.printMessage("Type an existing username:");

        String username = streamService.getMessage();

        User user = existingUsername(username);

        if (user != null)
        {
            streamService.printMessage("Username OK. Now type the password:");

            return user;
        }

        streamService.printMessage("Error: username not found! Please retry.");

        return null;
    }

    @Override
    protected User registerNewUser() throws IOException
    {
        streamService.printMessage("Type an username:");
        String username = streamService.getMessage();

        streamService.printMessage("Type a password");

        String password = streamService.getMessage();

        Boolean invalidUsername =
                GameServer.getUsersMap().containsKey(username);

        if (invalidUsername)
        {
            streamService.printMessage("The specified username already exist! Please type a new username.");
            return null;
        }

        streamService.printMessage("User created!");

        return new User(username, password, true);
    }

    protected boolean createNewGame(Player player) throws IOException
    {
        streamService.printMessage("Type a name for the new game match:");

        String gameName = streamService.getMessage();

        streamService.printMessage("Game name: " + gameName);

        boolean existingGameMatch =
                GameServer.getGameMatchMap().containsKey(gameName);

        if (existingGameMatch)
        {
            streamService.printMessage("A game match with the specified name already exists.");

            return false;
        }

        RMIGameAdapter adapter = new RMIGameAdapter(registry);

        startNewGameMatch(gameName, player, adapter);

        streamService.printMessage("Player: " + player.getName() + " created GameMatch - " + gameName);

        return true;
    }

    protected boolean checkExistingGame(Player player) throws IOException
    {
        streamService.printMessage("Type the name of the chosen game match:");

        String gameName = streamService.getMessage();

        streamService.printMessage("Game name: " + gameName);

        boolean existingGameMatch =
                GameServer.getGameMatchMap().containsKey(gameName);

        if (!existingGameMatch) {
            streamService.printMessage("Game match not found.");

            return false;
        }

        RMIGameAdapter adapter = new RMIGameAdapter(registry);

        insertIntoExistingGameMatch(gameName, player, adapter);

        streamService.printMessage("Player: " + player.getName() + " joined GameMatch - " + gameName);

        return true;
    }
}
