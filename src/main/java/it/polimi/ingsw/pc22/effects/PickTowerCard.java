package it.polimi.ingsw.pc22.effects;

import java.util.List;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.player.Player;

public class PickTowerCard implements Effect{
	
	private CardTypeEnum cardType;
	private int diceValue;
	private List<Asset> cardCostDiscount;
	
	public CardTypeEnum getCardType() {
		return cardType;
	}
	public void setCardType(CardTypeEnum cardType) {
		this.cardType = cardType;
	}
	public int getDiceValue() {
		return diceValue;
	}
	public void setDiceValue(int diceValue) {
		this.diceValue = diceValue;
	}
	public List<Asset> getCardCostDiscount() {
		return cardCostDiscount;
	}
	public void setCardCostDiscount(List<Asset> cardCostDiscount) {
		this.cardCostDiscount = cardCostDiscount;
	}
	

	@Override
	public boolean isLegal(Player player) 
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void executeAction(Player player) 
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cardCostDiscount == null) ? 0 : cardCostDiscount.hashCode());
		result = prime * result + ((cardType == null) ? 0 : cardType.hashCode());
		result = prime * result + diceValue;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PickTowerCard other = (PickTowerCard) obj;
		if (cardCostDiscount == null) {
			if (other.cardCostDiscount != null)
				return false;
		} else if (!cardCostDiscount.equals(other.cardCostDiscount))
			return false;
		if (cardType != other.cardType)
			return false;
		if (diceValue != other.diceValue)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "PickTowerCard [cardType=" + cardType + ", diceValue=" + diceValue + ", cardCostDiscount="
				+ cardCostDiscount + "]";
	}
	
}
