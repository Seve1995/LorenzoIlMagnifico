package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.adapters.RMIGameAdapter;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.rmi.RMIAuthenticationService;
import it.polimi.ingsw.pc22.rmi.RMIClientStreamService;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Logger;

public class RMIAuthenticationServiceImpl extends AuthenticationHandler implements RMIAuthenticationService
{

    private Registry registry;

    private RMIGameAdapter adapter;

    public RMIAuthenticationServiceImpl(Registry registry)
    {
        this.registry = registry;
    }

    @Override
    public void login() throws RemoteException
    {
        adapter = new RMIGameAdapter(registry);

        authentication(adapter);
    }


}
