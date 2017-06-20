package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.rmi.RMIClientStreamService;
import it.polimi.ingsw.pc22.states.StartMatchState;
import it.polimi.ingsw.pc22.states.WaitingState;
import it.polimi.ingsw.pc22.utils.ConsoleReader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by fandroid95 on 30/05/2017.
 */
public class RMIClientStreamServiceImpl implements RMIClientStreamService
{
    private Long timeout;

    private static final Logger LOGGER = Logger.getLogger(RMIClientStreamServiceImpl.class.getName());

    public RMIClientStreamServiceImpl(Long timeout)
    {
        this.timeout = timeout;
    }

    @Override
    public String getMessage() throws RemoteException
    {
        ExecutorService ex = Executors.newSingleThreadExecutor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        String message;

        while (true)
        {
            if (Client.isStateChanged())
            {
                Client.getGenericState().printState();

                Client.setStateChanged(false);
            }

            Future<String> result = ex.submit(new ConsoleReader(reader));

            try
            {
                message = result.get(this.timeout, TimeUnit.MILLISECONDS);

            }
            catch (ExecutionException | InterruptedException e)
            {
                LOGGER.log(Level.INFO, "Cancelling reading task", e);

                return null;
            }
            catch (TimeoutException e)
            {
                result.cancel(true);

                LOGGER.log(Level.INFO, "Timeout EXPIRED", e);

                return null;
            }

            if (!Client.getGenericState().validate(message))
                continue;

            return message;
        }


    }

    @Override
    public void changeState(String state) throws RemoteException
    {
        //TODO SISTEMARE STO SCHIFO
        if ("logged".equalsIgnoreCase(state))
        {
            System.out.println("prova andata");

            Client.setGenericState(new StartMatchState());

            Client.setStateChanged(true);
        }

        if ("started".equalsIgnoreCase(state))
        {
            System.out.println("prova andata");

            Client.setGenericState(new WaitingState());

            Client.setStateChanged(true);
        }
    }

    @Override
    public void printMessage(String message) throws RemoteException
    {
        System.out.println(message);
    }
}
