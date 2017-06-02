package it.polimi.ingsw.pc22.effects;

import java.util.List;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.AssetType;
import it.polimi.ingsw.pc22.gamebox.BuildingCard;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.player.Player;

public class EndGameMalus implements Effect{
	
	private CardTypeEnum noCardVictoryPoint;
	private List<Asset> assetsMalus; //Per ogni asset della lista in possesso del giocatore perdi un punto vittoria 
	private boolean loseOneVictoryPointEveryFiveVictoryPoints;
	private boolean buildingCardsMalus; //TRUE = Perdi un punto vittoria per ogni legno e pietra raffigurata sui costi delle carte edificio in possesso
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
	public boolean isBuildingCardsMalus() {
		return buildingCardsMalus;
	}
	public void setBuildingCardsMalus(boolean territoryCardsMalus) {
		this.buildingCardsMalus = territoryCardsMalus;
	}
	@Override
	public boolean isLegal(Player player) {
		
		return true;
	}
	@Override
	public void executeEffect(Player player) {
		
		if (loseOneVictoryPointEveryFiveVictoryPoints)
		{
			player.setVictoryPoints(player.getVictoryPoints() - player.getVictoryPoints() % 5);
		}
		
		if (assetsMalus != null)
		{
			int sum=0;
			
			for (Asset a : assetsMalus)
			{
					
					sum += player.getAsset(a.getType());
					
			}
			
			player.setVictoryPoints(player.getVictoryPoints() - sum);
		}
		
		if (buildingCardsMalus)
		{
			int sum = 0;
			for (BuildingCard b : player.getPlayerBoard().getBuildings())
			{
				for (Asset a : b.getCosts())
					
					if (a.getType().equals(AssetType.WOOD) || a.getType().equals(AssetType.STONE))
						
						sum += a.getValue();
			}
			
			player.setVictoryPoints(player.getVictoryPoints() - sum);
			
		}
		
		if (noCardVictoryPoint.equals(CardTypeEnum.CHARACTER))
		
			player.getPlayerBoard().setCharacters(null);
			
		if (noCardVictoryPoint.equals(CardTypeEnum.VENTURE))
			
			player.getPlayerBoard().setVentures(null);
			
		if (noCardVictoryPoint.equals(CardTypeEnum.TERRITORY))
			
			player.getPlayerBoard().setTerritories(null);
			
	}
}
