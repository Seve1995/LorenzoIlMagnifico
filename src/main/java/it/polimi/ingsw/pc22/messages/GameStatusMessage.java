package it.polimi.ingsw.pc22.messages;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

/**
 * This message informs the player about the status
 * of the match. It provides updated player, updated gameboard.
 * In so doing the user is informed, practically in real time,
 * about the match is going.
 */

public class GameStatusMessage extends  Message
{
    private GameBoard gameBoard;
    private Player player;

    private String state;

    public GameStatusMessage(GameBoard gameBoard, Player player, String state)
    {
        this.gameBoard = gameBoard;
        this.player = player;
        this.state = state;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public Player getPlayer() {
        return player;
    }

    public String getState()
    {
        return state;
    }

    @Override
    public String toString()
    {
        return "GameStatusMessage{" +
                "gameBoard=" + gameBoard +
                ", player=" + player +
                '}';
    }
}
