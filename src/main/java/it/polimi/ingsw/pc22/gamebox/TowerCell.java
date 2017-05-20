package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.player.Player;

public class TowerCell extends Cell{
	private DevelopmentCard developmentCard;
	private Asset resourceBonus;
	
	public TowerCell(Asset resourceBonus, int requiredDiceValue) {
		this.resourceBonus = resourceBonus;
		super.setRequiredDiceValue(requiredDiceValue);
	}
	
	public DevelopmentCard getDevelopmentCard() {
		return developmentCard;
	}

	public void setDevelopmentCard(DevelopmentCard developmentCard) {
		this.developmentCard = developmentCard;
	}

	public Asset getResourceBonus() {
		return resourceBonus;
	}

	public void setResourceBonus(Asset resource) {
		this.resourceBonus = resource;
	}

	@Override
	public void executeEffect(Player player) {
	}
}
