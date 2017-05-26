package it.polimi.ingsw.pc22.effects;

import java.util.List;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.player.Player;

public class AddTowerCardDiscount implements Effect{
	
	private CardTypeEnum cardType;
	private int diceValue;
	private List<Asset> discounts; 
	private boolean onlyOneAsset;
	
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
	public List<Asset> getDiscounts() {
		return discounts;
	}
	public void setDiscounts(List<Asset> discounts) {
		this.discounts = discounts;
	}
	public boolean isOnlyOneAsset() {
		return onlyOneAsset;
	}
	public void setOnlyOneAsset(boolean onlyOneAsset) {
		this.onlyOneAsset = onlyOneAsset;
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
		result = prime * result + ((cardType == null) ? 0 : cardType.hashCode());
		result = prime * result + diceValue;
		result = prime * result + ((discounts == null) ? 0 : discounts.hashCode());
		result = prime * result + (onlyOneAsset ? 1231 : 1237);
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
		AddTowerCardDiscount other = (AddTowerCardDiscount) obj;
		if (cardType != other.cardType)
			return false;
		if (diceValue != other.diceValue)
			return false;
		if (discounts == null) {
			if (other.discounts != null)
				return false;
		} else if (!discounts.equals(other.discounts))
			return false;
		if (onlyOneAsset != other.onlyOneAsset)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "AddTowerCardDiscount [cardType=" + cardType + ", diceValue=" + diceValue + ", discounts=" + discounts
				+ ", onlyOneAsset=" + onlyOneAsset + "]";
	}
	
}
