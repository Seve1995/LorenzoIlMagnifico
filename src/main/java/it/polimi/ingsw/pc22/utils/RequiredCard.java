package it.polimi.ingsw.pc22.utils;

import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;

/**
 * Created by matteo on 12/06/17.
 */
public class RequiredCard {

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