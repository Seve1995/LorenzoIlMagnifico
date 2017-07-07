package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.adapters.SocketIOAdapter;
import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.gamebox.ColorsEnum;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.messages.ChooseFamiliarMessage;
import it.polimi.ingsw.pc22.player.Player;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is used to store bonus/malus associated to familiarMembers.
 * It is useful for Leader Cards effects.
 */

public class FamilyMemberModifier implements Effect
{

	private ColorsEnum familyMemberColor = null;
	private int diceValueSet;
	private int diceValueBonus;
	private boolean toColoured;
	private boolean isABonus;
	private boolean toAll;

	private static final Logger LOGGER = Logger.getLogger(PickTowerCard.class.getName());

	public void setFamilyMemberColor(ColorsEnum familyMemberColor) {
		this.familyMemberColor = familyMemberColor;
	}

	public void setDiceValueSet(int diceValueSet) {
		this.diceValueSet = diceValueSet;
	}

	public void setDiceValueBonus(int diceValueBonus) {
		this.diceValueBonus = diceValueBonus;
	}

	public void setToColoured(boolean toColoured) {
		this.toColoured = toColoured;
	}

	public void setABonus(boolean ABonus) {
		isABonus = ABonus;
	}

	public void setToAll(boolean toAll) {
		this.toAll = toAll;
	}
	
	//se toCOloured=true && toAll=false richiede un input-> familyMemberColor

	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) {
		
		if(this.familyMemberColor.equals(ColorsEnum.NEUTER))
		{
			return false;
		}
		
		return true;
		
	}

	@Override
	public boolean executeEffects(Player player, GameBoard gameBoard) {

		if (!isLegal(player, gameBoard))
			return false;

		if (!toColoured)
		{
			for (FamilyMember f : player.getFamilyMembers())
			{
				if (f.getColor().equals(ColorsEnum.NEUTER) && isABonus)
				{
					f.setValueModifier(f.getValueModifier() + this.diceValueBonus);
				}
			}
		}

		if (toColoured && toAll)
		{
			for (FamilyMember f : player.getFamilyMembers())
			{
				if ((f.getColor().equals(ColorsEnum.NEUTER)))
					continue;

				if (!isABonus)
				{
					f.setFamiliarValue(diceValueSet);
				}

				if(isABonus)
				{
					f.setValueModifier(f.getValueModifier() + this.diceValueBonus);
				}
			}
		}

		if (toColoured && !isABonus && !toAll)
		{
			if(isLegal(player, gameBoard))
			{

				IOAdapter adapter = player.getAdapter();

				adapter.printMessage(new ChooseFamiliarMessage(player));

				if (adapter instanceof SocketIOAdapter)
					new Thread(new ReceiveFamiliarDecisionThread(gameBoard.getGameMatchName())).start();

				Long timestamp = System.currentTimeMillis();

				Long timeout = GameMatch.getTimeout();

				while (System.currentTimeMillis() < timestamp + timeout)
				{
					try
					{
						Thread.sleep(100L);
					}
					catch (InterruptedException e)
					{
						LOGGER.log(Level.WARNING, "Interrupted, not completed!", e);
						Thread.currentThread().interrupt();
					}
					if (familyMemberColor != null)
					{
						break;
					}
				}

				for (FamilyMember f : player.getFamilyMembers())
				{
					if (f.getColor().equals(familyMemberColor))
						f.setFamiliarPermanentValue(diceValueSet);

				}

				familyMemberColor = null;
			}
		}

		return true;
	}
}
			
			
		
