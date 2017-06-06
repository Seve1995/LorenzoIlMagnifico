package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public class AddEndGameVictoryPoints implements Effect{

	private Asset asset;

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) 
	{
		return true;
	}

	@Override
	public void executeEffect(Player player, GameBoard gameBoard) {
		
		if (isLegal(player,gameBoard))
			player.setEndGameVictoryPoints(player.getEndGameVictoryPoints()+asset.getValue());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((asset == null) ? 0 : asset.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AddEndGameVictoryPoints))
			return false;
		AddEndGameVictoryPoints other = (AddEndGameVictoryPoints) obj;
		if (asset == null) {
			if (other.asset != null)
				return false;
		} else if (!asset.equals(other.asset))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AddEndGameVictoryPoints [asset=" + asset + "]";
	}
	
	
}
