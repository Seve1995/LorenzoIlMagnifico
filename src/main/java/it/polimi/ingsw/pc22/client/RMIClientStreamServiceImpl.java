package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.rmi.RMIClientStreamService;
import it.polimi.ingsw.pc22.utils.ConsoleReader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.concurrent.*;

/**
 * Created by fandroid95 on 30/05/2017.
 */
public class RMIClientStreamServiceImpl implements RMIClientStreamService
{
    private Long timeout;

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
        catch (ExecutionException e)
        {
            System.out.println("Exception");

            return null;
        }
        catch (TimeoutException e)
        {
            System.out.println("Cancelling reading task");

            result.cancel(true);

            return null;
        }
        catch (InterruptedException e)
        {
            System.out.println("ConsoleInputReadTask() cancelled");

            return null;
        }

        return message;
    }

    @Override
    public void printMessage(String message) throws RemoteException
    {
        System.out.println(message);
    }
}
