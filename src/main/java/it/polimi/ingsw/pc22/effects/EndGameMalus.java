package it.polimi.ingsw.pc22.effects;

import java.util.List;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.player.Player;

public class EndGameMalus implements Effect{
	
	private CardTypeEnum noCardVictoryPoint;
	private List<Asset> assetsMalus; //Per ogni asset della lista in possesso del giocatore perdi un punto vittoria 
	private boolean loseOneVictoryPointEveryFiveVictoryPoints;
	private boolean territoryCardsMalus; //TRUE = Perdi un punto vittoria per ogni legno e pietra raffigurata sui costi delle carte edificio in possesso
	public CardTypeEnum getNoCardVictoryPoint() {
		return noCardVictoryPoint;
	}
	public void setNoCardVictoryPoint(CardTypeEnum noCardVictoryPoint) {
		this.noCardVictoryPoint = noCardVictoryPoint;
	}
	public List<Asset> getAssetsMalus() {
		return assetsMalus;
	}
	public void setAssetsMalus(List<Asset> assetsMalus) {
		this.assetsMalus = assetsMalus;
	}
	public boolean isLoseOneVictoryPointEveryFiveVictoryPoints() {
		return loseOneVictoryPointEveryFiveVictoryPoints;
	}
	public void setLoseOneVictoryPointEveryFiveVictoryPoints(boolean loseOneVictoryPointEveryFiveVictoryPoints) {
		this.loseOneVictoryPointEveryFiveVictoryPoints = loseOneVictoryPointEveryFiveVictoryPoints;
	}
	public boolean isTerritoryCardsMalus() {
		return territoryCardsMalus;
	}
	public void setTerritoryCardsMalus(boolean territoryCardsMalus) {
		this.territoryCardsMalus = territoryCardsMalus;
	}
	@Override
	public boolean isLegal(Player player) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void executeEffect(Player player) {
		// TODO Auto-generated method stub
		
	}
}
