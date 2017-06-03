package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.adapters.RMIGameAdapter;
import it.polimi.ingsw.pc22.rmi.RMIAuthenticationService;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;

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
