package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

/**
 * This class is used to model the effect of the Leader Sisto IV:
 *
 * so it set to the value "true" the flag of the player.
 *
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
