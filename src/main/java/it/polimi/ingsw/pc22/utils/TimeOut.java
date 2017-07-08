package it.polimi.ingsw.pc22.utils;


import java.io.Serializable;

/**
 * Simple class that represents the concept of timeout.
 * It is composed by two numeric attributes.
 * One referred to the time that a player has to complete an
 * action (set familiar + eventual dialogs + leader-actions)
 * and one associated to the time that has to pass before starting
 * a match (if the players are more than two)
 */

public class TimeOut implements Serializable
{
    private Long action;
    private Long gameStarting;

    public Long getAction() {
        return action;
    }

    public void setAction(Long action) {
        this.action = action;
    }

    public Long getGameStarting() {
        return gameStarting;
    }

    public void setGameStarting(Long gameStarting) {
        this.gameStarting = gameStarting;
    }
}
