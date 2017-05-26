package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.player.Player;

public class AddAssetForEveryCard implements Effect{
	
	private Asset payedAsset;
	private CardTypeEnum gainedCardType;
	private Asset gainedAsset;
	
	public Asset getPayedAsset() {
		return payedAsset;
	}
	public void setPayedAsset(Asset payedAsset) {
		this.payedAsset = payedAsset;
	}
	public CardTypeEnum getGainedCardType() {
		return gainedCardType;
	}
	public void setGainedCardType(CardTypeEnum gainedCardType) {
		this.gainedCardType = gainedCardType;
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
		result = prime * result + ((gainedAsset == null) ? 0 : gainedAsset.hashCode());
		result = prime * result + ((gainedCardType == null) ? 0 : gainedCardType.hashCode());
		result = prime * result + ((payedAsset == null) ? 0 : payedAsset.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AddAssetForEveryCard))
			return false;
		AddAssetForEveryCard other = (AddAssetForEveryCard) obj;
		if (gainedAsset == null) {
			if (other.gainedAsset != null)
				return false;
		} else if (!gainedAsset.equals(other.gainedAsset))
			return false;
		if (gainedCardType != other.gainedCardType)
			return false;
		if (payedAsset == null) {
			if (other.payedAsset != null)
				return false;
		} else if (!payedAsset.equals(other.payedAsset))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "AddAssetForEveryCard [payedAsset=" + payedAsset + ", gainedCardType=" + gainedCardType
				+ ", gainedAsset=" + gainedAsset + "]";
	}
	
	
}
