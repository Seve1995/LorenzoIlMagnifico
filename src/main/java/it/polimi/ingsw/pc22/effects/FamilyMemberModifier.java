package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.gamebox.ColorsEnum;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.player.Player;

public class FamilyMemberModifier implements Effect
{
	private FamilyMember familyMember;
	private int diceValueSet;
	private int diceValueBonus;
	private boolean toColoured;
	private boolean isABonus;
	private boolean toAll;

	@Override
	public boolean isLegal(Player player) {
		
		if(this.familyMember.getColor().equals(ColorsEnum.NEUTER))
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
		
		if (toColoured){
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
					
					if (!isABonus && !toAll)
					{
						if(isLegal(player))
						{
							this.familyMember.setFamiliarValue(diceValueSet);
						}
					}
				}	
			}
		}
		
		
	}
}
			
			
		
