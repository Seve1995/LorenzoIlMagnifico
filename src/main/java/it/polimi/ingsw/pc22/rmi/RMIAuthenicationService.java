package it.polimi.ingsw.pc22.rmi;

import java.rmi.Remote;

/**
 * Created by fandroid95 on 26/05/2017.
 */
public interface RMIAuthenicationService extends Remote
{
    boolean login();
}
