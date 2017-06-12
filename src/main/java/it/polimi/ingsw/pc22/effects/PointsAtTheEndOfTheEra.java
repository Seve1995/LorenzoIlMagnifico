package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

/**
 * Created by matteo on 12/06/17.
 */
public class PointsAtTheEndOfTheEra implements Effect {

    @Override
    public boolean isLegal(Player player, GameBoard gameBoard) {
        return true;
    }

    @Override
    public boolean executeEffects(Player player, GameBoard gameBoard) {

        player.setSistoIV(true);

        return true;
    }
}
