package it.polimi.ingsw.pc22.gamebox;

import java.util.List;

public class LeaderCard {
	private String name;
	private List<Asset> requiredAssets;
	private List<DevelopmentCard> requiredCard;
	private boolean faceUp; 
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Asset> getRequiredAssets() {
		return requiredAssets;
	}
	public void setRequiredAssets(List<Asset> requiredAssets) {
		this.requiredAssets = requiredAssets;
	}
	public List<DevelopmentCard> getRequiredCard() {
		return requiredCard;
	}
	public void setRequiredCard(List<DevelopmentCard> requiredCard) {
		this.requiredCard = requiredCard;
	}
}
