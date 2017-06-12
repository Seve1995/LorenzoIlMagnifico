package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.rmi.RMIClientStreamService;
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

        String message;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Future<String> result = ex.submit(new ConsoleReader(reader));

        try
        {
            message = result.get(this.timeout, TimeUnit.MILLISECONDS);

        }
        catch (ExecutionException | InterruptedException e)
        {
            message = null;

            LOGGER.log(Level.INFO, "Cancelling reading task", e);

        }
        catch (TimeoutException e)
        {
            result.cancel(true);

            message = null;

            LOGGER.log(Level.INFO, "Timeout EXPIRED", e);
        }

        return message;
    }

    @Override
    public void printMessage(String message) throws RemoteException
    {
        System.out.println(message);
    }
}
