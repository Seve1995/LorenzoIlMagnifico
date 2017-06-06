package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public class AddFamilyMembersMalus implements Effect{

	private int diceValueMalus;
	
	public int getDiceValueMalus() {
		return diceValueMalus;
	}

	public void setDiceValueMalus(int diceValueMalus) {
		this.diceValueMalus = diceValueMalus;
	}

	@Override
	public boolean isLegal(Player player, GameBoard gameBoard) {
		return true;
	}

	//TODO: Bisogna renderlo un effetto permanente! I family member vengono rimossi ogni volta, quindi non va bene!
	@Override
	public void executeEffect(Player player, GameBoard gameBoard) {
		
		if (isLegal(player,gameBoard))
			for(FamilyMember f : player.getFamilyMembers())
			{
				f.setValueModifier(diceValueMalus);
			}
		
	}

}
