package it.polimi.ingsw.pc22.adapters;

import it.polimi.ingsw.pc22.client.Client;
import it.polimi.ingsw.pc22.connection.GameServer;
import it.polimi.ingsw.pc22.exceptions.GenericException;
import it.polimi.ingsw.pc22.messages.Message;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.rmi.RMIClientStreamService;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * Created by fandroid95 on 30/05/2017.
 */
public class RMIIOAdapter extends IOAdapter
{
    private RMIClientStreamService streamService;

    public RMIIOAdapter(RMIClientStreamService streamService, Long timeout)
    {
        super.setTimeout(timeout);

        this.streamService = streamService;
    }

    @Override
    public void endConnection(Player player) throws IOException
    {
        String userName = player.getUsername();

        Player user = GameServer.getPlayersMap().get(userName);

        user.setLogged(false);
    }

    @Override
    public void printMessage(Message message)
    {
        if (Client.isStopped())
            return;

        try
        {
            streamService.printMessage(message);
        }
            catch (RemoteException e)
        {
            throw new GenericException("cannot write to RMI", e);
        }
    }

    @Override
    public String getMessage()
    {
        return null;
    }
}
