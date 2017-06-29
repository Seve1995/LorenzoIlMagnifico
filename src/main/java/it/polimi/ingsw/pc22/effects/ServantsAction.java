package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.gamebox.Asset;

/**
 * Created by fandroid95 on 29/06/2017.
 */
public class ServantsAction
{
    private Asset servants = null;

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
                e.printStackTrace();
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
