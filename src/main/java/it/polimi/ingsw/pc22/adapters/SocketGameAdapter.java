package it.polimi.ingsw.pc22.adapters;

import it.polimi.ingsw.pc22.connection.GameServer;
import it.polimi.ingsw.pc22.connection.User;
import it.polimi.ingsw.pc22.player.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by fandroid95 on 30/05/2017.
 */
public class SocketGameAdapter implements GameAdapter
{
    public Socket socket;

    private BufferedReader in;
    private PrintWriter out;

    public SocketGameAdapter(Socket socket)
    {
        this.socket = socket;

        try {

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream(), true);

        }
            catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void endConnection(Player player) throws IOException
    {
        String userName = player.getName();

        User user = GameServer.getUsersMap().get(userName);

        user.setLogged(false);

        PrintWriter printWriter =
                new PrintWriter(socket.getOutputStream(), true);

        printWriter.println("EXIT");

        socket.close();
    }

    @Override
    public void printMessage(String message)
    {
        out.println(message);
    }

    @Override
    public String getMessage()
    {
        String answer = null;

        try
        {
            answer = in.readLine();

        }
            catch (IOException e)
        {
            e.printStackTrace();
        }

        return  answer;
    }
}
