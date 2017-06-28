package it.polimi.ingsw.pc22.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by fandroid95 on 26/05/2017.
 */
public interface RMIServerInterface extends Remote
{
    void login(String loginMessage) throws RemoteException;

    void matchHandling(String matchString) throws RemoteException;

    void doAction(String actionMessage) throws RemoteException;

}
