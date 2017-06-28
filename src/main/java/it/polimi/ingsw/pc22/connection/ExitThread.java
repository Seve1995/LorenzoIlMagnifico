package it.polimi.ingsw.pc22.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by fandroid95 on 13/06/2017.
 */
public class ExitThread implements Callable<Boolean>
{
    private BufferedReader console;

    private static final Logger LOGGER = Logger.getLogger(ExitThread.class.getName());

    @Override
    public Boolean call() throws Exception
    {
        console = new BufferedReader(new InputStreamReader(System.in));

        try
        {
            while(true)
            {
                if (!console.ready()) Thread.sleep(100);

                String msgReceived = console.readLine();

                if(!"EXIT".equalsIgnoreCase(msgReceived)) continue;

                return true;
            }

        }
        catch (IOException | InterruptedException e)
        {
            LOGGER.log(Level.INFO, "ERROR RECEIVE THREAD", e);

            return true;
        }
    }
}
