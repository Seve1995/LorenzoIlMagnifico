package it.polimi.ingsw.pc22.gamebox;

import it.polimi.ingsw.pc22.player.Player;

public class TowerCell extends Cell
{
	private DevelopmentCard developmentCard;
	public TowerCell(int requiredDiceValue)
	{
		super(requiredDiceValue);
		this.developmentCard = null;
	}

	public DevelopmentCard getDevelopmentCard() {
		return developmentCard;
	}

	public void setDevelopmentCard(DevelopmentCard developmentCard) {
		this.developmentCard = developmentCard;
	}
	
	@Override
	public void executeEffect(Player player) {
	}
}
