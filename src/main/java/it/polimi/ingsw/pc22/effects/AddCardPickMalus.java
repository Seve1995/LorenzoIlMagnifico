package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.player.CardModifier;
import it.polimi.ingsw.pc22.player.Player;

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
	public boolean isLegal(Player player) {
		return true;
	}

	@Override
	public void executeEffect(Player player) {
		if (isLegal(player))
		{
			CardModifier currCardModifier = new CardModifier();
			currCardModifier.setValueModifier(malusValue);
			currCardModifier.setCardType(cardType);
		
			player.getCardModifier().add(currCardModifier);
		}
		
	}
	
}