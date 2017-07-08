package it.polimi.ingsw.pc22.utils;


import java.io.Serializable;

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
