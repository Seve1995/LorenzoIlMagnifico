package it.polimi.ingsw.pc22.adapters;

import it.polimi.ingsw.pc22.connection.GameServer;

import java.net.Socket;

/**
 * Created by fandroid95 on 30/05/2017.
 */
public class SocketGameAdapter implements GameAdapter
{
    public Socket socket;

    public SocketGameAdapter(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void endConnection()
    {

    }
}
