package it.polimi.ingsw.pc22.adapters;

import java.io.IOException;
import java.io.PrintWriter;
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
    public void endConnection() throws IOException
    {
        PrintWriter printWriter =
                new PrintWriter(socket.getOutputStream(), true);

        printWriter.println("EXIT");

        socket.close();
    }
}
