package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.rmi.RMIClientStreamService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        String message = null;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try
        {
            message =  reader.readLine();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return message;
    }

    @Override
    public void printMessage(String message) throws RemoteException
    {
        System.out.println(message);
    }

    public void exit()
    {

    }
}
