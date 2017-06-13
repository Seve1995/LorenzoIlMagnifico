package it.polimi.ingsw.pc22.adapters;

import it.polimi.ingsw.pc22.connection.GameServer;
import it.polimi.ingsw.pc22.connection.User;
import it.polimi.ingsw.pc22.exceptions.GenericException;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.utils.ConsoleReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by fandroid95 on 30/05/2017.
 */
public class SocketIOAdapter extends IOAdapter implements Runnable
{
    private Socket socket;

    private BufferedReader in;
    private PrintWriter out;

    private static final Logger LOGGER = Logger.getLogger(SocketIOAdapter.class.getName());

    public SocketIOAdapter(Socket socket, Long timeout)
    {
        this.socket = socket;
        super.setTimeout(timeout);

        try {

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream(), true);

        }
            catch (IOException e)
        {
            throw new GenericException(e);
        }
    }

    @Override
    public void run()
    {
        try
        {
            String choice = getMessage();

            authenticate(choice);

            printMessage("true");

        }
            catch (IOException e)
        {
            throw new GenericException("Cannot authenticate client", e);
        }
    }

    @Override
    public void endConnection(Player player) throws IOException
    {
        if (player != null)
        {
            String userName = player.getName();

            User user = GameServer.getUsersMap().get(userName);

            user.setLogged(false);
        }

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
        ExecutorService ex = Executors.newSingleThreadExecutor();

        String answer;

        Future<String> result = ex.submit(new ConsoleReader(in));

        try
        {
            answer = result.get(this.getTimeout(), TimeUnit.MILLISECONDS);

        }
            catch (ExecutionException | InterruptedException e)
        {
            answer = null;

            LOGGER.log(Level.INFO, "Cannot read from input", e);

        }
            catch (TimeoutException e)
        {
            result.cancel(true);

            answer = null;

            LOGGER.log(Level.INFO, "Timeout EXPIRED", e);
        }

        return  answer;
    }


}
