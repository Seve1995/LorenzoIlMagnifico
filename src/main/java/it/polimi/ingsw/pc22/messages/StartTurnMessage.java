package it.polimi.ingsw.pc22.messages;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

/**
 * This message informs the player that the turn is started.
 *
 */

public class StartTurnMessage extends Message
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
