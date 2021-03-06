package it.polimi.ingsw.pc22.connection;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.messages.StoppedServerMessage;
import it.polimi.ingsw.pc22.player.Player;

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This thread is useful to manage the closure of the server.
 * It stops all the games (saving them for the persistence)
 * and closes all socket-connection (if any).
 * Every player is notified that the server is shutting down
 * and they go to a suspended-state.
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
                if (!console.ready())
                    Thread.sleep(100);

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

        ArrayList<GameMatch> stoppedGames = new ArrayList<>();

        ClassLoader classLoader = ExitThread.class.getClassLoader();

        File playerFile = new File(classLoader.getResource("savedGames.txt").getFile());

        FileOutputStream fileOut = null;

        try
        {
            fileOut = new FileOutputStream(playerFile.getPath());

            if (fileOut == null)
                return;

            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            while(stoppedGames.size() < currentSize)
            {
                for (GameMatch gameMatch  : GameServer.getGameMatchMap().values())
                {
                    if (!gameMatch.getStarted() && !stoppedGames.contains(gameMatch))
                    {
                        List<Player> players = gameMatch.getPlayers();

                        for (Player p : players)
                        {
                            IOAdapter adapter = p.getAdapter();

                            adapter.printMessage(new StoppedServerMessage("SERVER STOPPED"));

                            p.setSuspended(true);
                        }

                        gameMatch.setSerialized(true);

                        stoppedGames.add(gameMatch);

                    }

                    if ((gameMatch.isStopped() && !stoppedGames.contains(gameMatch)))
                    {
                        List<Player> players = gameMatch.getPlayers();

                        for (Player p : players)
                        {
                            p.setSuspended(true);
                        }

                        gameMatch.setSerialized(true);

                        stoppedGames.add(gameMatch);
                    }
                }
            }

            out.writeObject(stoppedGames);

            out.close();

        } catch (IOException e)
        {
            LOGGER.log(Level.INFO, "CANNOT WRITE ON FILE", e);
        }
        finally
        {

            if (fileOut != null)
            {
                try
                {
                    fileOut.close();
                }
                    catch (IOException e)
                {
                    LOGGER.log(Level.INFO, "ERROR RECEIVE THREAD", e);
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
