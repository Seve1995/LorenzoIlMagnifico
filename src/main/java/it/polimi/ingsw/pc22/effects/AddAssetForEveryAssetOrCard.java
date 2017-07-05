package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public class AddAssetForEveryAssetOrCard implements Effect{
	
	private Asset paidAsset;
	private CardTypeEnum paidCardType;
	private Asset gainedAsset;
	
	public Asset getPaidAsset() {
		return paidAsset;
	}
	public void setPaidAsset(Asset paidAsset) {
		this.paidAsset = paidAsset;
	}
	public CardTypeEnum getPaidCardType() {
		return paidCardType;
	}
	public void setPaidCardType(CardTypeEnum paidCardType) {
		this.paidCardType = paidCardType;
	}
	public Asset getGainedAsset() {
		return gainedAsset;
	}
	public void setGainedAsset(Asset gainedAsset) {
		this.gainedAsset = gainedAsset;
	}
	
	@Override
	public boolean isLegal(Player player,GameBoard gameBoard) 
	{
		return true;
	}
	
	@Override
	public boolean executeEffects(Player player, GameBoard gameBoard) {
		
		if (paidCardType != null) 
		{
			int totalCards;
			switch (paidCardType) {
			
			case TERRITORY:
				totalCards = player.getPlayerBoard().getTerritories().size();
				for (int i=0; i<totalCards; i++)
					player.addAsset(gainedAsset);
				break;
				
			case VENTURE:
				totalCards = player.getPlayerBoard().getVentures().size();
				for (int i=0; i<totalCards; i++)
					player.addAsset(gainedAsset);
				break;
				
			case CHARACTER:
				totalCards = player.getPlayerBoard().getCharacters().size();
				for (int i=0; i<totalCards; i++)
					player.addAsset(gainedAsset);
				break;
				
			case BUILDING:
				totalCards = player.getPlayerBoard().getBuildings().size();
				for (int i=0; i<totalCards; i++)
					player.addAsset(gainedAsset);
				break;
				
			default:
				break;
			
			}
			
			return true;
		}
		
		else if (gainedAsset!=null)
		{
			int totalAsset;
			
			totalAsset = player.getAsset(paidAsset.getType());
			
			if (paidAsset.getValue()>0)
				{
					totalAsset = totalAsset/paidAsset.getValue(); 
					for (int i=0; i<totalAsset; i++)
						player.addAsset(gainedAsset);
				}
			
			return true;
			
		}
		
		return false;
	}
	
}
