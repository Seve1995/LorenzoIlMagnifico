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
	public boolean executeEffects(Player player, GameBoard gameBoard) 
	{
		List<CardModifier> playerCardModifiers;
		
		playerCardModifiers = player.getCardModifiers();
		
		CardModifier cardModifier = new CardModifier();
		
		cardModifier.setCardType(cardType);

		cardModifier.setValueModifier(diceValueDiscount);
		
		cardModifier.setAssetDiscount(assetDiscounts);
		
		cardModifier.setOnlyOneAsset(onlyOneAsset);
		
		playerCardModifiers.add(cardModifier);
		
		return true;
	}
	
}
