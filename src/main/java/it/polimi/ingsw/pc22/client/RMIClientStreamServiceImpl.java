package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.rmi.RMIClientStreamService;

import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * Created by fandroid95 on 30/05/2017.
 */
public class RMIClientStreamServiceImpl implements RMIClientStreamService
{
    @Override
    public String getMessage() throws RemoteException
    {
        Scanner scanner = new Scanner(System.in);

        return scanner.nextLine();
    }

    @Override
    public void printMessage(String message) throws RemoteException
    {
        System.out.println(message);
    }
}
