package it.polimi.ingsw.pc22.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

/**
 * Created by fandroid95 on 26/05/2017.
 */
public interface RMIServerInterface extends Remote
{
    Long registerClient(RMIClientStreamService streamService) throws RemoteException;

    void login(String loginMessage, Long key) throws RemoteException;

    void matchHandling(String matchString, Long key) throws RemoteException;

    void doAction(String actionMessage, Long key) throws RemoteException;

    void takeCouncilDecision(String councilMessage, Long key, int numberOfBonus)
            throws RemoteException;

}
