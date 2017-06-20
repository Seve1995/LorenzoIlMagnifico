package it.polimi.ingsw.pc22.rmi;

import it.polimi.ingsw.pc22.messages.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by fandroid95 on 30/05/2017.
 */
public interface RMIClientStreamService extends Remote
{
    String getMessage() throws RemoteException;

    void printMessage(Message message) throws RemoteException;

    void changeState(String state) throws RemoteException;
}
