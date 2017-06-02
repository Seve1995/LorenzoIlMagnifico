package it.polimi.ingsw.pc22.adapters;

import it.polimi.ingsw.pc22.player.Player;

import java.io.IOException;

/**
 * Created by fandroid95 on 30/05/2017.
 */
public interface GameAdapter
{
    void endConnection(Player player) throws IOException;

    void printMessage(String message);

    String getMessage();
}
