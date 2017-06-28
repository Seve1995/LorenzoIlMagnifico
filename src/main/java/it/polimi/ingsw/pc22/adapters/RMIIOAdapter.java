package it.polimi.ingsw.pc22.adapters;

import it.polimi.ingsw.pc22.actions.Action;
import it.polimi.ingsw.pc22.actions.ActionFactory;
import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.connection.GameServer;
import it.polimi.ingsw.pc22.connection.User;
import it.polimi.ingsw.pc22.exceptions.GenericException;
import it.polimi.ingsw.pc22.messages.ErrorMessage;
import it.polimi.ingsw.pc22.messages.ExecutedAction;
import it.polimi.ingsw.pc22.messages.LoginMessage;
import it.polimi.ingsw.pc22.messages.Message;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.rmi.RMIClientStreamService;
import it.polimi.ingsw.pc22.rmi.RMIServerInterface;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by fandroid95 on 30/05/2017.
 */
public class RMIIOAdapter extends IOAdapter implements RMIServerInterface
{
    private Registry registry;

    private RMIClientStreamService streamService;

    private static final Logger LOGGER = Logger.getLogger(RMIIOAdapter.class.getName());

    public RMIIOAdapter(Registry registry, Long timeout)
    {
        this.registry = registry;
        super.setTimeout(timeout);
    }

    @Override
    public void login(String loginMessage) throws RemoteException
    {
        Player player;

        try
        {
            streamService = (RMIClientStreamService) registry.lookup("client");

            player = authenticate(loginMessage);

            if (player != null)
            {
                LoginMessage message = new LoginMessage(true, false, player);

                System.out.println(message);

                printMessage(message);
            }
        }
        catch (NotBoundException | IOException e)
        {
            throw new GenericException("Cannot find client in registry", e);
        }
    }

    @Override
    public void matchHandling(String matchString) throws RemoteException
    {
        boolean started;

        try
        {
            started = gameHandling(matchString);

            if (started)
            {
                LoginMessage message = new LoginMessage(true, true, null);

                printMessage(message);
            }
        }
        catch (IOException e)
        {
            throw new GenericException("Cannot find client in registry", e);
        }
    }

    @Override
    public void doAction(String actionMessage) throws RemoteException
    {
        if (actionMessage == null)
        {
            printMessage(new ErrorMessage("Action Not received"));

            return;
        }

        Action action = ActionFactory.createAction(actionMessage, GameMatch.getCurrentPlayer());

        System.out.println("Action: " + action);

        if (action == null)
        {
            printMessage(new ErrorMessage("Action Not Valid"));

            return;
        }

        boolean executed = action.executeAction
                (GameMatch.getCurrentPlayer(), GameMatch.getCurrentGameBoard());

        System.out.println(executed + " - " +  GameMatch.getCurrentPlayer().isHasPassed());

        if (!executed) return;

        if (GameMatch.getCurrentPlayer().isFamiliarPositioned())
        {
            printMessage(new ExecutedAction("Action Performed"));

            return;
        }

        if (GameMatch.getCurrentPlayer().isHasPassed())
            return;
    }

    @Override
    public void endConnection(Player player) throws IOException
    {
        String userName = player.getName();

        User user = GameServer.getUsersMap().get(userName);

        user.setLogged(false);
    }

    @Override
    public void printMessage(Message message)
    {
        try
        {
            streamService.printMessage(message);

            System.out.println(streamService);
        }
            catch (RemoteException e)
        {
            throw new GenericException("cannot write to RMI", e);
        }
    }

    @Override
    public String getMessage()
    {
        String message;

        try
        {
            message = streamService.getMessage();
        }
            catch (RemoteException e)
        {
            message = null;

            LOGGER.log(Level.INFO, "cannot read from RMI", e);

        }

        return message;
    }
}
