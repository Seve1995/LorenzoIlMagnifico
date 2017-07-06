package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.adapters.SocketIOAdapter;
import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.connection.GameServer;
import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.messages.ChooseAssetsMessage;
import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

public class FromAssetToAssetOrEffect extends ChooseAsset implements Effect
{
	private transient List<Asset> paiedAssets = null;

	private List<Asset> gainedAssets;
	private boolean onlyOneAsset;
	private Effect gainedEffect;

	public void setPaiedAssets(List<Asset> paiedAssets) {
		this.paiedAssets = paiedAssets;
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
	public void setGainedEffect(Effect gainedEffect) {
		this.gainedEffect = gainedEffect;
	}

	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) 
	{
		if (getChosenAssetsToPay() != null)
		{
			Asset payedAsset = paiedAssets.get(getChosenAssetsToPay());

			if (player.getAsset(payedAsset.getType()) < payedAsset.getValue())
				return false;

			return true;
		}

		for (Asset a : paiedAssets)
		{
			if(a.getValue() > player.getAsset(a.getType()))
			{
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public boolean executeEffects(Player player, GameBoard gameBoard)
	{
		String gameName = gameBoard.getGameMatchName();

		GameMatch gameMatch = GameServer.getGameMatchMap().get(gameName);

		gameMatch.setCurrEffect(this);

		if (isOnlyOneAsset())
		{
			IOAdapter adapter = player.getAdapter();

			adapter.printMessage(new ChooseAssetsMessage(paiedAssets));

			if (adapter instanceof SocketIOAdapter)
				new Thread(new ReceiveAssetDecisionThread(paiedAssets, gameName)).start();

			if (!super.waitForResult())
				return false;
		}

		if (!isLegal(player, gameBoard))
			return false;

		if (gainedEffect != null)
		{
			boolean executed = gainedEffect.executeEffects(player, gameBoard);

			if (!executed)
				return false;
		}

		if (isOnlyOneAsset())
		{
			Asset payedAsset = paiedAssets.get(getChosenAssetsToPay());

			int value = payedAsset.getValue() * -1;

			payedAsset.setValue(value);

			player.addAsset(payedAsset);
		}

		if (gainedAssets == null)
			return true;

		if (gainedAssets.size() == 1)
			player.addAsset(gainedAssets.get(0));

		else
		{
			Asset asset = gainedAssets.get(getChosenAssetsToPay());

			System.out.println(asset.getValue());

			player.addAsset(asset);
		}

		super.setChosenAssetsToPay(null);

		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gainedAssets == null) ? 0 : gainedAssets.hashCode());
		result = prime * result + ((gainedEffect == null) ? 0 : gainedEffect.hashCode());
		result = prime * result + (onlyOneAsset ? 1231 : 1237);
		result = prime * result + ((paiedAssets == null) ? 0 : paiedAssets.hashCode());
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
		if (paiedAssets == null) {
			if (other.paiedAssets != null)
				return false;
		} else if (!paiedAssets.equals(other.paiedAssets))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "FromAssetToAssetOrEffect [paiedAssets=" + paiedAssets + ", gainedAssets=" + gainedAssets
				+ ", onlyOneAsset=" + onlyOneAsset + ", gainedEffect=" + gainedEffect + "]";
	}
}
