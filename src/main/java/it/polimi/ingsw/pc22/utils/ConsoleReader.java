package it.polimi.ingsw.pc22.utils;

import java.io.BufferedReader;
import java.util.concurrent.Callable;

/**
 * This class reads the command line in synchronized way.
 */
public class ConsoleReader implements Callable<String>
{
    private BufferedReader in;

    public ConsoleReader(BufferedReader in)
    {
        this.in = in;
    }

    @Override
    public String call() throws Exception
    {
        while (!in.ready())
        {
            Thread.sleep(200);
        }

        return in.readLine();
    }
}
