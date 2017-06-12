package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.ColorsEnum;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

public class FamilyMemberModifier implements Effect
{

	private ColorsEnum familyMemberColor;
	private int diceValueSet;
	private int diceValueBonus;
	private boolean toColoured;
	private boolean isABonus;
	private boolean toAll;
	
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
		
		
		if (isLegal(player, gameBoard))
		{
		
			if (!toColoured){
				for (FamilyMember f : player.getFamilyMembers())
				{
					if (f.getColor().equals(ColorsEnum.NEUTER))
					{
						if (isABonus)
						{
							f.setValueModifier(f.getValueModifier() + this.diceValueBonus);
						}
					}
				}
			}
			
			if (toColoured && toAll){
				for (FamilyMember f : player.getFamilyMembers())
				{
					if (!(f.getColor().equals(ColorsEnum.NEUTER)))
					{
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
			}
				
			if (toColoured && !isABonus && !toAll)
				{
					if(isLegal(player, gameBoard))
					{
						
						player.getAdapter().printMessage("Which familiar color do you want?");
						
						FamilyMember familyMember = player.getAdapter().askFamiliarMemberForBonus(player);
						
						if (familyMember==null) return false;
						
						familyMemberColor = familyMember.getColor();
						
						for (FamilyMember f : player.getFamilyMembers())
						{
							if (f.getColor().equals(familyMemberColor))
								
								f.setFamiliarPermanentValue(diceValueSet);
							
						}
			
					}
				}
			
			return true;
		}
		
		return false;
	}
}
			
			
		
