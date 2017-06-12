package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.effects.Effect;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderCard {
	private String name;
	private List<Asset> requiredAssets;
	private Map <CardTypeEnum, Integer> requiredCard = new EnumMap<>(CardTypeEnum.class);
	private boolean faceUp;
	private boolean isPlayed;
	public boolean isPlayed() {
		return isPlayed;
	}
	public void setPlayed(boolean isPlayed) {
		this.isPlayed = isPlayed;
	}
	private List<Effect> effects;
	
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
	public Map<CardTypeEnum, Integer> getRequiredCard() {
		return requiredCard;
	}
	public void setRequiredCard(Map<CardTypeEnum, Integer> requiredCard) {
		this.requiredCard = requiredCard;
	}
	public boolean isFaceUp() {
		return faceUp;
	}
	public void setFaceUp(boolean faceUp) {
		this.faceUp = faceUp;
	}
	public List<Effect> getEffects() {
		return effects;
	}
	public void setEffects(List<Effect> effects) {
		this.effects = effects;
	}
}
