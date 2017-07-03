package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.adapters.SocketIOAdapter;
import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.gamebox.TerritoryCard;
import it.polimi.ingsw.pc22.messages.ChooseServantsMessage;
import it.polimi.ingsw.pc22.player.Player;

public class DoHarvestAction extends ServantsAction implements Effect
{
	private int value;

	public int getValue()
	{
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) {
		
		if (value < player.getPlayerBoard().getBonusTile().getHarvestActivationValue())
			return false;
		
		return true;
	}

	@Override
	public boolean executeEffects(Player player, GameBoard gameBoard)
	{
		GameMatch.getCurrentGameBoard().setCurreEffect(this);

		IOAdapter adapter = player.getAdapter();

		adapter.printMessage(new ChooseServantsMessage(player));

		if (adapter instanceof SocketIOAdapter)
			new Thread(new ReceiveServantsDecisionThread()).start();

		if (!super.waitForResult()) return false;

		//Serve per gestire il malus dell'excommunication card
		value += player.getHarvestValueModifier();

		if (isLegal(player,gameBoard))
			return false;

		if (!super.waitForResult()) return false;

		value += super.getServants().getValue();
		player.addAsset(player.getPlayerBoard().getBonusTile().getHarvestServantBonus());
		player.addAsset(player.getPlayerBoard().getBonusTile().getHarvestCoinsBonus());
		player.addAsset(player.getPlayerBoard().getBonusTile().getHarvestMilitaryPointsBonus());
		player.addAsset(player.getPlayerBoard().getBonusTile().getHarvestStonesBonus());
		player.addAsset(player.getPlayerBoard().getBonusTile().getHarvestWoodsBonus());


		for (TerritoryCard card : player.getPlayerBoard().getTerritories())
		{
			if (value < card.getPermanentEffectActivationCost())
				continue;

			for (Effect effect : card.getPermanentEffects())
			{
				effect.executeEffects(player, gameBoard);
			}
		}

		return true;
	}
		
	
	
}
