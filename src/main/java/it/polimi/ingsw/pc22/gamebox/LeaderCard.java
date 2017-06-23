package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.effects.Effect;
import it.polimi.ingsw.pc22.utils.RequiredCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class LeaderCard implements Serializable
{
	private String name;
	private int number;
	private List<Asset> requiredAssets;
	private List<RequiredCard> requiredCards = new ArrayList<RequiredCard>();
	private boolean faceUp;
	private boolean isPlayed;
	private List<Effect> effects;

	public boolean isPlayed() {
		return isPlayed;
	}
	public void setPlayed(boolean isPlayed) {
		this.isPlayed = isPlayed;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public List<Asset> getRequiredAssets() {
		return requiredAssets;
	}
	public void setRequiredAssets(List<Asset> requiredAssets) {
		this.requiredAssets = requiredAssets;
	}
	public List<RequiredCard> getRequiredCards() {
		return requiredCards;
	}
	public void setRequiredCards(List<RequiredCard> requiredCards) {
		this.requiredCards = requiredCards;
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

	public String toString(){

		String output = name + " |";

		return output;

	}
}
