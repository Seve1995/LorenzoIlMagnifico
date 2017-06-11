package it.polimi.ingsw.pc22.adapters;

import it.polimi.ingsw.pc22.connection.GameServer;
import it.polimi.ingsw.pc22.connection.User;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.utils.ConsoleReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.MalformedParametersException;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * Created by fandroid95 on 30/05/2017.
 */
public class SocketIOAdapter extends IOAdapter implements Runnable
{
    public Socket socket;

    private BufferedReader in;
    private PrintWriter out;

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
            e.printStackTrace();
        }
    }

    public void run()
    {
        authentication();
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
    public String getMessage() throws IOException 
    {
        ExecutorService ex = Executors.newSingleThreadExecutor();

        String answer;

        Future<String> result = ex.submit(new ConsoleReader(in));

        try
        {
            answer = result.get(this.getTimeout(), TimeUnit.MILLISECONDS);

        }
            catch (ExecutionException e)
        {
            System.out.println("Exception");

            return null;
        }
            catch (TimeoutException e)
        {
            System.out.println("Cancelling reading task");

            result.cancel(true);

            return null;
        }
            catch (InterruptedException e)
        {
            System.out.println("ConsoleInputReadTask() cancelled");

            return null;
        }


        return  answer;
    }


}
