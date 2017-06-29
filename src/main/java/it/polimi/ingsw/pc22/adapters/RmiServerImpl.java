package it.polimi.ingsw.pc22.adapters;

import it.polimi.ingsw.pc22.actions.Action;
import it.polimi.ingsw.pc22.actions.ActionFactory;
import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.exceptions.GenericException;
import it.polimi.ingsw.pc22.messages.ErrorMessage;
import it.polimi.ingsw.pc22.messages.ExecutedAction;
import it.polimi.ingsw.pc22.messages.LoginMessage;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.rmi.RMIClientStreamService;
import it.polimi.ingsw.pc22.rmi.RMIServerInterface;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by fandroid95 on 29/06/2017.
 */
public class RmiServerImpl implements RMIServerInterface
{
    private Map<Long, RMIIOAdapter> rmiAdapters;

    private Long timeout;

    public RmiServerImpl(Long timeout)
    {
        this.rmiAdapters = new ConcurrentHashMap<>();
        this.timeout = timeout;
    }

    @Override
    public synchronized Long registerClient(RMIClientStreamService streamService) throws RemoteException
    {
        RMIIOAdapter adapter = new RMIIOAdapter(streamService, timeout);

        Long mapKey = rmiAdapters.size() + 1L;

        rmiAdapters.put(mapKey, adapter);

        return mapKey;
    }

    @Override
    public void login(String loginMessage, Long key) throws RemoteException
    {
        IOAdapter adapter = rmiAdapters.get(key);

        Player player;

        try
        {
            player = adapter.authenticate(loginMessage);

            if (player != null)
            {
                LoginMessage message = new LoginMessage(true, false, player);

                System.out.println(message);

                adapter.printMessage(message);
            }
        }
        catch (IOException e)
        {
            throw new GenericException("Cannot find client in registry", e);
        }
    }

    @Override
    public void matchHandling(String matchString, Long key) throws RemoteException
    {
        IOAdapter adapter = rmiAdapters.get(key);

        try
        {
            if (adapter.gameHandling(matchString))
            {
                LoginMessage message = new LoginMessage(true, true, null);

                adapter.printMessage(message);
            }
        }
        catch (IOException e)
        {
            throw new GenericException("Cannot find client in registry", e);
        }
    }

    @Override
    public void doAction(String actionMessage, Long key) throws RemoteException
    {
        IOAdapter adapter = rmiAdapters.get(key);

        if (actionMessage == null)
        {
            adapter.printMessage(new ErrorMessage("Action Not received"));

            return;
        }

        Action action = ActionFactory.createAction(actionMessage, GameMatch.getCurrentPlayer());

        System.out.println("Action: " + action);

        if (action == null)
        {
            adapter.printMessage(new ErrorMessage("Action Not Valid"));

            return;
        }

        boolean executed = action.executeAction
                (GameMatch.getCurrentPlayer(), GameMatch.getCurrentGameBoard());

        System.out.println(executed + " - " +  GameMatch.getCurrentPlayer().isHasPassed());

        if (!executed) return;

        if (GameMatch.getCurrentPlayer().isFamiliarPositioned())
        {
            adapter.printMessage(new ExecutedAction("Action Performed"));

            return;
        }

        if (GameMatch.getCurrentPlayer().isHasPassed())
            return;
    }
}
