package it.polimi.ingsw.pc22.messages;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

import java.io.Serializable;

/**
 * Created by fandroid95 on 20/06/2017.
 */
public class StartTurnMessage extends  Message implements Serializable
{
    private GameBoard gameBoard;
    private Player player;

    public StartTurnMessage(GameBoard gameBoard, Player player)
    {
        this.gameBoard = gameBoard;
        this.player = player;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return "StartTurnMessage{" +
                "gameBoard=" + gameBoard +
                ", player=" + player +
                '}';
    }
}
