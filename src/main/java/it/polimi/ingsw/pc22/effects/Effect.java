package it.polimi.ingsw.pc22.effects;import it.polimi.ingsw.pc22.gamebox.GameBoard;import it.polimi.ingsw.pc22.player.Player;import java.io.Serializable;public interface Effect extends Serializable{	boolean isLegal(Player player, GameBoard gameBoard);	boolean executeEffects(Player player, GameBoard gameBoard);}