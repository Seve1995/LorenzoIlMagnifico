package it.polimi.ingsw.pc22.messages;

import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.gamebox.GameBoard;

/**
 * Created by fandroid95 on 27/06/2017.
 */
public class ChooseCardMessage extends Message
{
    private CardTypeEnum cardType;
    private GameBoard gameBoard;

    public ChooseCardMessage(CardTypeEnum cardType, GameBoard gameBoard)
    {
        this.cardType = cardType;
        this.gameBoard = gameBoard;
    }

    public CardTypeEnum getCardType() {
        return cardType;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}
