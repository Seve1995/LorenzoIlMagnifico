package it.polimi.ingsw.pc22.rmi;

import java.rmi.Remote;

/**
 * Created by fandroid95 on 30/05/2017.
 */
public interface RMIClientStreamService extends Remote
{
    String getMessage();

    void printMessage(String message);
}
