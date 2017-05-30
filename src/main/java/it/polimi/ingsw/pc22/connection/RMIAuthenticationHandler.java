package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.rmi.RMIAuthenticationService;
import it.polimi.ingsw.pc22.rmi.RMIClientStreamService;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Map;

public class RMIAuthenticationHandler extends AuthenticationHandler implements RMIAuthenticationService
{
    @Override
    public void login(Registry registry) throws RemoteException
    {
        try
        {
            RMIClientStreamService streamService = (RMIClientStreamService)
                    registry.lookup("client");

            User user = null;

            while (user == null)
            {
                streamService.printMessage("Choose an operation:\n"
                        + "(1) Create new game match\n"
                        + "(2) Join a friend's game match\n"
                        + "(3) Join a random game match");

                String answer = streamService.getMessage();

                switch (answer)
                {
                    case "login":

                        user = login();

                        break;
                    case "sign up":

                        user = signUp();

                        break;

                    default:

                        streamService.printMessage("Non-valid input. Please retry... ");

                        break;
                }
            }


        }
        catch (NotBoundException | IOException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    protected void checkPassword(User user) throws IOException
    {

    }

    @Override
    protected User getUserFromUserName() throws IOException
    {
        return null;
    }

    @Override
    protected User registerNewUser() throws IOException
    {
        return null;
    }
}
