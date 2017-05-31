package it.polimi.ingsw.pc22.adapters;

import java.io.IOException;
import java.rmi.registry.Registry;

/**
 * Created by fandroid95 on 30/05/2017.
 */
public class RMIGameAdapter implements GameAdapter
{
    Registry registry;

    public RMIGameAdapter(Registry registry)
    {
        this.registry = registry;
    }

    @Override
    public void endConnection() throws IOException
    {

    }
}
