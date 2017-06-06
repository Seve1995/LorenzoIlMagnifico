package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

public class FromAssetToAssetOrEffect implements Effect{

	private List<Asset> paidAssets;
	private List<Asset> gainedAssets;
	private boolean onlyOneAsset;
	private Effect gainedEffect;
	
	public List<Asset> getPaiedAssets() {
		return paidAssets;
	}
	public void setPaiedAssets(List<Asset> paiedAssets) {
		this.paidAssets = paiedAssets;
	}
	public List<Asset> getGainedAssets() {
		return gainedAssets;
	}
	public void setGainedAssets(List<Asset> gainedAssets) {
		this.gainedAssets = gainedAssets;
	}
	public boolean isOnlyOneAsset() {
		return onlyOneAsset;
	}
	public void setOnlyOneAsset(boolean onlyOneAsset) {
		this.onlyOneAsset = onlyOneAsset;
	}
	public Effect getGainedEffect() {
		return gainedEffect;
	}
	public void setGainedEffect(Effect gainedEffect) {
		this.gainedEffect = gainedEffect;
	}
	
	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) 
	{
		for (Asset a : paidAssets){
			if(a.getValue() > player.getAsset(a.getType()))
			{
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public boolean executeEffect(Player player, GameBoard gameBoard) 
	{
		if (isLegal(player, gameBoard)){
			if (gainedEffect != null)
			{
				gainedEffect.executeEffect(player, gameBoard);
				
			}
			if (gainedAssets != null){
				for (Asset a : gainedAssets)
				{
					player.addAsset(a);
				}
			}
			return true;
		}
		
		return false;
				
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gainedAssets == null) ? 0 : gainedAssets.hashCode());
		result = prime * result + ((gainedEffect == null) ? 0 : gainedEffect.hashCode());
		result = prime * result + (onlyOneAsset ? 1231 : 1237);
		result = prime * result + ((paidAssets == null) ? 0 : paidAssets.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof FromAssetToAssetOrEffect))
			return false;
		FromAssetToAssetOrEffect other = (FromAssetToAssetOrEffect) obj;
		if (gainedAssets == null) {
			if (other.gainedAssets != null)
				return false;
		} else if (!gainedAssets.equals(other.gainedAssets))
			return false;
		if (gainedEffect == null) {
			if (other.gainedEffect != null)
				return false;
		} else if (!gainedEffect.equals(other.gainedEffect))
			return false;
		if (onlyOneAsset != other.onlyOneAsset)
			return false;
		if (paidAssets == null) {
			if (other.paidAssets != null)
				return false;
		} else if (!paidAssets.equals(other.paidAssets))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "FromAssetToAssetOrEffect [paiedAssets=" + paidAssets + ", gainedAssets=" + gainedAssets
				+ ", onlyOneAsset=" + onlyOneAsset + ", gainedEffect=" + gainedEffect + "]";
	} 
	
	
}
