package it.polimi.ingsw.pc22.rmi;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

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

    void takeAssetDecision(String assetDecision, Long key, List<Asset> payedAssets)
            throws RemoteException;

    void takeCardDecision(String cardMessage, Long key, CardTypeEnum currCardType)
            throws RemoteException;

    void takeCostsDecision(String costMessage, Long key)
            throws RemoteException;

    void takeServantsDecision(String servantsMessage, Long key)
            throws RemoteException;

    void takeExcommunicationDecision(String servantsMessage, Long key)
            throws RemoteException;

    void takeFamiliarDecision(String familiarMessage, Long key)
            throws RemoteException;

}
