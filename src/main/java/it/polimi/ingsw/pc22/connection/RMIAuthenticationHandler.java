package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.rmi.RMIAuthenticationService;
import it.polimi.ingsw.pc22.rmi.RMIClientAuthenticator;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Map;
import java.util.Scanner;

public class RMIAuthenticationHandler implements RMIAuthenticationService, AuthenticationHandler
{
    @Override
    public void login(Registry registry) throws RemoteException
    {
        Map<String, User> usesrMap = GameServer.getUsersMap();

        try
        {
            RMIClientAuthenticator authenticator = (RMIClientAuthenticator)
                    registry.lookup("client");

            User user = null;

            while(true)
            {
                String answer = authenticator.authenticationChioce();

                if (user == null) continue;

                break;
            }


        }
        catch (NotBoundException e)
        {
            e.printStackTrace();
        }

    }
}
