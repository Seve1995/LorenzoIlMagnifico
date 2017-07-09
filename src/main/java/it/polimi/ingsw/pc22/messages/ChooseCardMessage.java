package it.polimi.ingsw.pc22.messages;

import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.gamebox.GameBoard;

/**
 * This message provides the player the card type (if "any"
 * the player must specify the type he/she wants), and the updated gameboard.
 *
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
