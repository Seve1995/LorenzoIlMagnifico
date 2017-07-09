package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.CardModifier;
import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

/**
 * The activation of this effect will add to a certain player
 * some discounts when he/she will take a certain cardType from the tower.
 * It will discount assets cost (=assetDiscounts) or 
 * the required value (=diceValueDiscount). If onlyOneAsset attribute
 * is true, the player will have to choose only one asset
 * from the list of assetDiscounts as discount.
 */

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
