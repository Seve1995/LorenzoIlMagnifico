package it.polimi.ingsw.pc22.rmi;

import it.polimi.ingsw.pc22.messages.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface of the RMI-client.
 */
public interface RMIClientStreamService extends Remote
{
    void printMessage(Message message) throws RemoteException;
}
