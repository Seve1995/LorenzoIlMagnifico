package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.CardModifier;
import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

public class AddTowerCardDiscount implements Effect{

	private CardTypeEnum cardType;
	private int diceValueDiscount;
	private List<Asset> assetDiscounts; 
	private boolean onlyOneAsset;
	
	public CardTypeEnum getCardType() {
		return cardType;
	}
	public void setCardType(CardTypeEnum cardType) {
		this.cardType = cardType;
	}
	public int getDiceValueDiscount() {
		return diceValueDiscount;
	}
	public void setDiceValueDiscount(int diceValueDiscount) {
		this.diceValueDiscount = diceValueDiscount;
	}
	public List<Asset> getDiscounts() {
		return assetDiscounts;
	}
	public void setDiscounts(List<Asset> discounts) {
		this.assetDiscounts = discounts;
	}
	public boolean isOnlyOneAsset() {
		return onlyOneAsset;
	}
	public void setOnlyOneAsset(boolean onlyOneAsset) {
		this.onlyOneAsset = onlyOneAsset;
	}
	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) 
	{
		return true;
	}
	@Override
	public boolean executeEffects(Player player, GameBoard gameBoard) {
		
		if (isLegal(player,gameBoard))
		{
			List<CardModifier> playerCardModifiers;
			playerCardModifiers = player.getCardModifiers();
			CardModifier cardModifier = new CardModifier();
			cardModifier.setValueModifier(diceValueDiscount);
			cardModifier.setAssetDiscount(assetDiscounts);
			cardModifier.setOnlyOneAsset(onlyOneAsset);
			playerCardModifiers.add(cardModifier);
			return true;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cardType == null) ? 0 : cardType.hashCode());
		result = prime * result + diceValueDiscount;
		result = prime * result + ((assetDiscounts == null) ? 0 : assetDiscounts.hashCode());
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
		if (diceValueDiscount != other.diceValueDiscount)
			return false;
		if (assetDiscounts == null) {
			if (other.assetDiscounts != null)
				return false;
		} else if (!assetDiscounts.equals(other.assetDiscounts))
			return false;
		if (onlyOneAsset != other.onlyOneAsset)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "AddTowerCardDiscount [cardType=" + cardType + ", diceValue=" + diceValueDiscount + ", discounts=" + assetDiscounts
				+ ", onlyOneAsset=" + onlyOneAsset + "]";
	}
	
}
