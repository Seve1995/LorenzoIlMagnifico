package it.polimi.ingsw.pc22.adapters;

import it.polimi.ingsw.pc22.client.Client;
import it.polimi.ingsw.pc22.exceptions.GenericException;
import it.polimi.ingsw.pc22.messages.Message;
import it.polimi.ingsw.pc22.rmi.RMIClientStreamService;

import java.rmi.RemoteException;

/**
 * This class is the implementation of RMI-side of the IO-Adapter
 * It has a method to "send" a message to the client, which is actually
 * a remote-method-call, to a special function on the user-side (please
 * refer to MessageHandler class in package "Client")
 *
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
