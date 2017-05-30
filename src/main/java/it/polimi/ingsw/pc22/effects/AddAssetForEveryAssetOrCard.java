package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.AssetType;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.player.Player;

public class AddAssetForEveryAssetOrCard implements Effect{
	
	private Asset paidAsset;
	private CardTypeEnum paidCardType;
	private Asset gainedAsset;
	
	public Asset getPayedAsset() {
		return paidAsset;
	}
	public void setPayedAsset(Asset payedAsset) {
		this.paidAsset = payedAsset;
	}
	public CardTypeEnum getGainedCardType() {
		return paidCardType;
	}
	public void setGainedCardType(CardTypeEnum gainedCardType) {
		this.paidCardType = gainedCardType;
	}
	public Asset getGainedAsset() {
		return gainedAsset;
	}
	public void setGainedAsset(Asset gainedAsset) {
		this.gainedAsset = gainedAsset;
	}
	
	@Override
	public boolean isLegal(Player player) 
	{
		return true;
	}
	
	@Override
	public void executeEffect(Player player) 
	{
		if (paidCardType != null) 
		{
			int totalCards = 0;
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
		}
		
		else if (gainedAsset!=null)
		{
			int totalAsset = 0;
			totalAsset = player.getAsset(gainedAsset.getType());
			totalAsset = totalAsset/paidAsset.getValue(); 
			//Perché se ad esempio ho un guadagno di 1 moneta "OGNI 2" punti militari e il player ha 10 punti military, devo aggiungerli un
			//coin ogni 2 miltary point! Quindi faccio, nell'esempio, 10/2 = 5 
			for (int i=0; i<totalAsset; i++)
				player.addAsset(gainedAsset);
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gainedAsset == null) ? 0 : gainedAsset.hashCode());
		result = prime * result + ((paidCardType == null) ? 0 : paidCardType.hashCode());
		result = prime * result + ((paidAsset == null) ? 0 : paidAsset.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AddAssetForEveryAssetOrCard))
			return false;
		AddAssetForEveryAssetOrCard other = (AddAssetForEveryAssetOrCard) obj;
		if (gainedAsset == null) {
			if (other.gainedAsset != null)
				return false;
		} else if (!gainedAsset.equals(other.gainedAsset))
			return false;
		if (paidCardType != other.paidCardType)
			return false;
		if (paidAsset == null) {
			if (other.paidAsset != null)
				return false;
		} else if (!paidAsset.equals(other.paidAsset))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "AddAssetForEveryCard [payedAsset=" + paidAsset + ", gainedCardType=" + paidCardType
				+ ", gainedAsset=" + gainedAsset + "]";
	}
	
	
}
