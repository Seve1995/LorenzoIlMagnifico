package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.CardModifier;
import it.polimi.ingsw.pc22.player.Player;

/**
 * This class handle this effect of some excommunication cards:
 * each time the player take a card of the type specified in the attribute
 * cardType (through the action space or as a Card effect), his/her action receives 
 * a -4 reduction of its value
 */	

public class AddCardPickMalus implements Effect{

	private CardTypeEnum cardType;
	private int malusValue;
	
	public CardTypeEnum getCardType() {
		return cardType;
	}

	public void setCardType(CardTypeEnum cardType) {
		this.cardType = cardType;
	}

	public int getMalusValue() {
		return malusValue;
	}

	public void setMalusValue(int malusValue) {
		this.malusValue = malusValue;
	}

	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) {
		return true;
	}

	@Override
	public boolean executeEffects(Player player, GameBoard gameBoard) {
		
		CardModifier currCardModifier = new CardModifier();
		
		currCardModifier.setValueModifier(malusValue);
		
		currCardModifier.setCardType(cardType);
		
		player.getCardModifiers().add(currCardModifier);
		
		return true;
		
	}
	
}
