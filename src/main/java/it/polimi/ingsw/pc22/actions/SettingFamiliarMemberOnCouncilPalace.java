package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.CouncilPalace;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.GameBoard;
import it.polimi.ingsw.pc22.player.Player;

import java.util.List;

public class SettingFamiliarMemberOnCouncilPalace extends Action {

	public SettingFamiliarMemberOnCouncilPalace(FamilyMember familyMember) {
		super(familyMember);
	}

    public SettingFamiliarMemberOnCouncilPalace() {

    }

    @Override
	protected boolean isLegal(Player player, GameBoard gameBoard) {
		
		if (super.getFamilyMember().getValue()<1) 
			return false;
		
		return true;
	}

	@Override
	public boolean executeAction(Player player, GameBoard gameBoard) {
		
		CouncilPalace councilPalace = this.gameBoard.getCouncilPalace(); 
		
		if (isLegal(player, gameBoard))
		{
			List<Player> playersInCouncilPalace = councilPalace.getPlayersInCouncilPalace();
			if(!playersInCouncilPalace.contains(player)) //Aggiunge il player alla lista se non aveva ancora messo alcun familiare all'interno del council palace
				playersInCouncilPalace.add(player);
			councilPalace.getCouncilPalaceCells()[councilPalace.firstCellFree()].setFamilyMember(this.getFamilyMember());
			familyMember.setPlayed(true);
			councilPalace.getCouncilPalaceCells()[councilPalace.firstCellFree()].executeEffect(player);
			
			return true;
		}
		
		else {
			
			return false;
		}

	}
	

}
