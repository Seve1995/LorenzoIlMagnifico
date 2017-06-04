package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.ColorsEnum;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public class FamilyMemberModifier extends Effect
{
	public FamilyMemberModifier(GameBoard gameBoard) {
		super(gameBoard);
	}

	private ColorsEnum familyMemberColor;
	private int diceValueSet;
	private int diceValueBonus;
	private boolean toColoured;
	private boolean isABonus;
	private boolean toAll;
	
	//se toCOloured=true && toAll=false richiede un input-> familyMemberColor

	@Override
	public boolean isLegal(Player player) {
		
		if(this.familyMemberColor.equals(ColorsEnum.NEUTER))
		{
			return false;
		}
		
		return true;
		
	}

	@Override
	public void executeEffect(Player player) {
		if (!toColoured){
			for (FamilyMember f : player.getFamilyMember())
			{
				if (f.getColor().equals(ColorsEnum.NEUTER))
				{
					if (isABonus)
					{
						f.setFamiliarValue(f.getFamiliarValue() + this.diceValueBonus);
					}
				}
			}
		}
		
		if (toColoured && toAll){
			for (FamilyMember f : player.getFamilyMember())
			{
				if (!(f.getColor().equals(ColorsEnum.NEUTER)))
				{
					if (!isABonus && toAll)
					{
						f.setFamiliarValue(diceValueSet);
					}
					
					if(isABonus && toAll)
					{
						f.setFamiliarValue(f.getFamiliarValue() + this.diceValueBonus);
					}
					
					
				}	
			}
		}
			
		if (toColoured && !isABonus && !toAll)
			{
				if(isLegal(player))
				{
					
					player.getAdapter().printMessage("Which Familiar Color Do You Want?");
					
					player.getAdapter().getMessage();
					
					/*if (player.getAdapter().getMessage().equals("B"))
					{
						this.familyMemberColor = ColorsEnum.BLACK;
					}
					
					if (player.getAdapter().getMessage().equals("W"))
					{
						this.familyMemberColor = ColorsEnum.WHITE;
					}
					
					if (player.getAdapter().getMessage().equals("O"))
					{
						this.familyMemberColor = ColorsEnum.ORANGE;
					}*/
					
					
					for (FamilyMember f : player.getFamilyMember())
					{
						if (f.getColor().equals(familyMemberColor))
							f.setFamiliarValue(diceValueSet);
					}
		
				}
			}
	}
}
			
			
		
