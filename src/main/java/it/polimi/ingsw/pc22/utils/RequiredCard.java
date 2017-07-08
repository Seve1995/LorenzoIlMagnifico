package it.polimi.ingsw.pc22.utils;

import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;

import java.io.Serializable;

/**
 * This class models the concept of required card
 * that continuously arises in the game.
 * It has two attributes: the number of cards required,
 * and their type.
 */
public class RequiredCard implements Serializable
{
    private int value;
    private CardTypeEnum type;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public CardTypeEnum getType() {
        return type;
    }

    public void setType(CardTypeEnum type) {
        this.type = type;
    }

    public RequiredCard(int value, CardTypeEnum type) {
        this.value = value;
        this.type = type;
    }
}