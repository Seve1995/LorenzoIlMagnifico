package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.messages.Message;
import it.polimi.ingsw.pc22.rmi.RMIClientStreamService;

import java.rmi.RemoteException;

/**
 * It is the simple implementation of RMI-side of client.
 */
public class RMIClientStreamServiceImpl implements RMIClientStreamService
{
    @Override
    public void printMessage(Message message) throws RemoteException
    {
        MessageHandler.handleMessage(message);
    }
}
