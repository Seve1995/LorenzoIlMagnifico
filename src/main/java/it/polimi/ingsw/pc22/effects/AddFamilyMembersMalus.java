package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public class AddFamilyMembersMalus extends Effect{

	public AddFamilyMembersMalus(GameBoard gameBoard) {
		super(gameBoard);
	}

	private int diceValueMalus;
	
	public int getDiceValueMalus() {
		return diceValueMalus;
	}

	public void setDiceValueMalus(int diceValueMalus) {
		this.diceValueMalus = diceValueMalus;
	}

	@Override
	public boolean isLegal(Player player) {
		return true;
	}

	@Override
	public void executeEffect(Player player) {
		
		for(FamilyMember f : player.getFamilyMember())
		{
			f.setFamiliarValue(f.getFamiliarValue()+diceValueMalus);
		}
		
	}

}
