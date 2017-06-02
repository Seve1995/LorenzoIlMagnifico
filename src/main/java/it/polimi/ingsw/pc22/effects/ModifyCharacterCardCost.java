package it.polimi.ingsw.pc22.effects;

import java.util.List;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.player.CardModifier;
import it.polimi.ingsw.pc22.player.Player;

public class ModifyCharacterCardCost implements Effect {
	
	private List<Asset> assetDiscount;

	@Override
	public boolean isLegal(Player player) {

		return true;
	}

	@Override
	public void executeEffect(Player player) {
		
		
		CardModifier currCardModifier = new CardModifier();
		currCardModifier.setAssetDiscount(assetDiscount);
		currCardModifier.setCardType(CardTypeEnum.CHARACTER);
		currCardModifier.setValueModifier(0);
		currCardModifier.setOnlyOneAsset(true);
		
		player.getCardModifier().add(currCardModifier);

	}
	

}
