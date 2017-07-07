package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.messages.StoppedServerMessage;
import it.polimi.ingsw.pc22.player.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by fandroid95 on 13/06/2017.
 */
public class ExitThread implements Runnable
{
    private BufferedReader console;

    private static final Logger LOGGER = Logger.getLogger(ExitThread.class.getName());

    private ServerSocket serverSocket;

    public ExitThread(ServerSocket serverSocket)
    {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run()
    {
        console = new BufferedReader(new InputStreamReader(System.in));

        try
        {
            while(true)
            {
                if (!console.ready()) Thread.sleep(100);

                String msgReceived = console.readLine();

                if (!"EXIT".equalsIgnoreCase(msgReceived))
                    continue;

                System.out.println(msgReceived);

                GameServer.setIsClosed(true);

                break;
            }
        }
        catch (IOException | InterruptedException e)
        {
            LOGGER.log(Level.INFO, "ERROR RECEIVE THREAD", e);
        }

        Integer currentSize = GameServer.getGameMatchMap().size();

        List<GameMatch> stoppedGames = new ArrayList<>();

        while(stoppedGames.size() < currentSize)
        {
            for (GameMatch gameMatch  : GameServer.getGameMatchMap().values())
            {
                if (!gameMatch.getStarted() && !stoppedGames.contains(gameMatch))
                {
                    stoppedGames.add(gameMatch);

                    System.out.println("GAMENAME: " + gameMatch.getGameName());

                    List<Player> players = gameMatch.getPlayers();

                    for (Player p : players)
                    {
                        IOAdapter adapter = p.getAdapter();

                        adapter.printMessage(new StoppedServerMessage("SERVER STOPPED"));
                    }
                }

                if ((gameMatch.isStopped() && !stoppedGames.contains(gameMatch)))
                {
                    stoppedGames.add(gameMatch);

                    System.out.println("GAMENAME: " + gameMatch.getGameName());
                }
            }
        }

        try
        {
            serverSocket.close();
        }
            catch (IOException e)
        {
            LOGGER.log(Level.INFO, "ERROR RECEIVE THREAD", e);
        }
    }
}
