package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.messages.Message;
import it.polimi.ingsw.pc22.rmi.RMIClientStreamService;

import java.rmi.RemoteException;

/**
 * Created by fandroid95 on 30/05/2017.
 */
public class RMIClientStreamServiceImpl implements RMIClientStreamService
{
    @Override
    public void printMessage(Message message) throws RemoteException
    {
        MessageHandler.handleMessage(message);
    }
}
