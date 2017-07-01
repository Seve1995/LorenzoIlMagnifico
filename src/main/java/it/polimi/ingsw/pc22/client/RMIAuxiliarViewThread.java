package it.polimi.ingsw.pc22.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by fandroid95 on 01/07/2017.
 */
public class RMIAuxiliarViewThread implements Runnable
{
    private BufferedReader inKeyboard = null;

    private static final Logger LOGGER = Logger.getLogger(ViewThread.class.getName());

    private boolean printed = false;

    public void run()
    {
        try
        {
            inKeyboard = new BufferedReader(new InputStreamReader(System.in));

            while(true)
            {
                if (printed && Client.isStateChanged()) break;

                if (!printed)
                {
                    Client.getGenericState().printState();

                    Client.setStateChanged(false);

                    printed = true;
                }

                if (!inKeyboard.ready())
                {
                    Thread.sleep(100);

                    continue;
                }

                String msgToServer = inKeyboard.readLine();

                if (!Client.getGenericState().validate(msgToServer))
                    continue;

                Client.getGenericState().sendToServer(msgToServer);
            }

            System.out.println("Shutting down output");
        }
        catch(Exception e)
        {
            LOGGER.log(Level.INFO, "ERROR RECEIVE THREAD", e);
        }
    }
}
