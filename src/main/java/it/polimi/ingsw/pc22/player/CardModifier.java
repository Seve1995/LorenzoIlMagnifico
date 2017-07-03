package it.polimi.ingsw.pc22.player;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;

import java.util.List;

public class CardModifier
{
	private CardTypeEnum cardType; //puo essere any
	private int valueModifier; // +qualcosa -qualcosa
	private List<Asset> assetDiscount;
	private boolean onlyOneAsset;

	public CardModifier() {
		
	}

	public CardTypeEnum getCardType() {
		return cardType;
	}

	public void setCardType(CardTypeEnum cardType) {
		this.cardType = cardType;
	}

	public int getValueModifier() {
		return valueModifier;
	}

	public void setValueModifier(int valueModifier) {
		this.valueModifier = valueModifier;
	}

	public List<Asset> getAssetDiscount() {
		return assetDiscount;
	}

	public void setAssetDiscount(List<Asset> assetDiscount) {
		this.assetDiscount = assetDiscount;
	}

	public boolean isOnlyOneAsset() {
		return onlyOneAsset;
	}

	public void setOnlyOneAsset(boolean onlyOneAsset) {
		this.onlyOneAsset = onlyOneAsset;
	}
	
}
