package it.polimi.ingsw.pc22.adapters;

import it.polimi.ingsw.pc22.connection.GameServer;
import it.polimi.ingsw.pc22.exceptions.GenericException;
import it.polimi.ingsw.pc22.messages.LoginMessage;
import it.polimi.ingsw.pc22.messages.Message;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.utils.ConsoleReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is the implementation of Socket-side of the IO-Adapter
 * It has a method to send a true message to the client, and to get messages
 * from the buffer, to make the communication work.
 * 
 */
public class SocketIOAdapter extends IOAdapter implements Runnable
{
    private Socket socket;

    private String playerName = null;

    private BufferedReader in;
    private ObjectOutputStream out;

    private static final Logger LOGGER = Logger.getLogger(SocketIOAdapter.class.getName());

    public SocketIOAdapter(Socket socket, Long timeout)
    {
        this.socket = socket;
        super.setTimeout(timeout);

        try
        {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new ObjectOutputStream(socket.getOutputStream());
        }
            catch (IOException e)
        {
            throw new GenericException(e);
        }
    }

    public SocketIOAdapter() {}

    @Override
    public void run()
    {
        Player player = null;

        if (playerName != null)
            player = GameServer.getPlayersMap().get(playerName);

        boolean started = false;

        try
        {
            while (player == null)
            {
                String authentication = getMessage();

                if (authentication == null)
                    continue;

                player = authenticate(authentication);
            }

            if (playerName == null)
            {
                printMessage(new LoginMessage(true, false, player));
            }

            while (!started)
            {
                String match = getMessage();

                if (match == null)
                    continue;

                started = gameHandling(match);
            }

            printMessage(new LoginMessage(true, true, player));

        }
            catch (IOException e)
        {
            throw new GenericException("Cannot authenticate client", e);
        }
    }

    @Override
    public void printMessage(Message message)
    {
        try
        {
            out.writeUnshared(message);

            out.flush();

            out.reset();
        }
            catch (IOException e)
        {
            LOGGER.log(Level.INFO, "CANNOT WRITE OBJECT", e);
        }
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

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }
}
