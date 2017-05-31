package it.polimi.ingsw.pc22.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by fandroid95 on 26/05/2017.
 */
public interface RMIAuthenticationService extends Remote
{
    void login() throws RemoteException;
}
