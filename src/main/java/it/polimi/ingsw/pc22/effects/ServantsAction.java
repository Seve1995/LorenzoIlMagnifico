package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.gamebox.Asset;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by fandroid95 on 29/06/2017.
 */
public class ServantsAction
{
    private Asset servants = null;

    private static final Logger LOGGER = Logger.getLogger(ServantsAction.class.getName());

    public boolean waitForResult()
    {
        Long timestamp = System.currentTimeMillis();

        Long timeout = GameMatch.getTimeout();

        while (System.currentTimeMillis() < timestamp + timeout)
        {
            try
            {
                Thread.sleep(100L);
            }
                catch (InterruptedException e)
            {
                LOGGER.log(Level.WARNING, "Interrupted!", e);
                Thread.currentThread().interrupt();
            }

            if (servants != null)
            {
                System.out.println(servants);

                return true;
            }
        }

        return false;
    }

    public void setServants(Asset servants)
    {
        this.servants = servants;
    }

    public Asset getServants() {
        return servants;
    }
}
