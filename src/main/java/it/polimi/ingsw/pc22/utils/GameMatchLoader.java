package it.polimi.ingsw.pc22.utils;

import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.connection.GameServer;
import it.polimi.ingsw.pc22.player.Player;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by fandroid95 on 07/07/2017.
 */
public class GameMatchLoader
{
    private static final String FILEPATH = "savedGames.txt";

    private static final Logger LOGGER = Logger.getLogger(PlayerLoader.class.getName());

    public static Map<String, GameMatch> loadGameMatches()
    {
        Map<String, Player> players = GameServer.getPlayersMap();

        Map<String, GameMatch>  gamesMap = new ConcurrentHashMap<>();

        FileInputStream file = null;

        try
        {
            ClassLoader classLoader = GameMatchLoader.class.getClassLoader();

            File gameMatches = new File(classLoader.getResource(FILEPATH).getFile());

            if (gameMatches.length() == 0)
                return gamesMap;

            file = new FileInputStream(gameMatches.getPath());

            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            //deserialize the List
            List<GameMatch> recoveredGames = (List<GameMatch>)input.readObject();

            if (recoveredGames.isEmpty())
                return gamesMap;

            for (GameMatch match :recoveredGames)
            {
                gamesMap.put(match.getGameName(), match);

                for (Player player : match.getPlayers())
                {
                    players.put(player.getUsername(), player);
                }
            }

        }
        catch (ClassNotFoundException | IOException e)
        {
            LOGGER.log(Level.INFO, "PROBLEMI DESERIALIZZAZIONE MATCHES", e);
        }
        finally
        {

            if (file != null)
                try
                {
                    file.close();
                } catch (IOException e)
                {
                    LOGGER.log(Level.INFO, "PROBLEMI DESERIALIZZAZIONE MATCHES", e);
                }
        }

        return gamesMap;
    }
}

