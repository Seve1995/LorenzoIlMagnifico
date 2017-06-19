package it.polimi.ingsw.pc22.adapters;

import it.polimi.ingsw.pc22.connection.GameServer;
import it.polimi.ingsw.pc22.connection.User;
import it.polimi.ingsw.pc22.exceptions.GenericException;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.rmi.RMIAuthenticationService;
import it.polimi.ingsw.pc22.rmi.RMIClientStreamService;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by fandroid95 on 30/05/2017.
 */
public class RMIIOAdapter extends IOAdapter implements RMIAuthenticationService
{
    Registry registry;

    RMIClientStreamService streamService;

    private static final Logger LOGGER = Logger.getLogger(RMIIOAdapter.class.getName());

    public RMIIOAdapter(Registry registry, Long timeout)
    {
        this.registry = registry;
        super.setTimeout(timeout);
    }

    @Override
    public void login() throws RemoteException
    {
        try
        {
            streamService = (RMIClientStreamService) registry.lookup("client");

            authenticate("prova");
        }
        catch (NotBoundException | IOException e)
        {
            throw new GenericException("Cannot find client in registry", e);
        }

    }

    @Override
    public void register() throws RemoteException
    {
        try
        {
            streamService = (RMIClientStreamService) registry.lookup("client");
        }
        catch (RemoteException | NotBoundException e)
        {
            throw new GenericException("Cannot find client in registry",e);
        }
    }


    @Override
    public void endConnection(Player player) throws IOException
    {
        String userName = player.getName();

        User user = GameServer.getUsersMap().get(userName);

        user.setLogged(false);
    }

    @Override
    public void printMessage(String message)
    {
        try
        {
            streamService.printMessage(message);
        }
            catch (RemoteException e)
        {
            throw new GenericException("cannot write from RMI", e);
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
