package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public class AddAsset extends Effect
{
	public AddAsset(GameBoard gameBoard) {
		super(gameBoard);
	}

	private Asset asset;

	public Asset getAsset()
	{
		return asset;
	}

	public void setAsset(Asset asset)
	{
		this.asset = asset;
	}

	@Override
	public String toString()
	{
		return this.getAsset().toString();
	}

	@Override
	public boolean isLegal(Player player) {
		return true;
	}

	@Override
	public void executeEffect(Player player) {
		
		if (isLegal(player))
			
			player.addAsset(asset);
			
	}
	
	/*
	public static void main(String[] args) {
		AddAsset add = new AddAsset();
		add.setAsset(new Asset(3, AssetType.COIN));
		Player p1 = new Player();
		add.executeEffect(p1);
		System.out.println(p1.getAsset(AssetType.COIN));
	}
	*/
}	
	
	
	
	