package it.polimi.ingsw.pc22.messages;

import java.io.Serializable;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public class GameStatusMessage extends  Message implements Serializable
{
    private GameBoard gameBoard;
    private Player player;

    public GameStatusMessage(GameBoard gameBoard, Player player)
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
}
