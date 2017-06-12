package it.polimi.ingsw.pc22.actions;

import it.polimi.ingsw.pc22.gamebox.CouncilPalace;
import it.polimi.ingsw.pc22.gamebox.CouncilPalaceCell;
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
		
		if (familyMember.getValue()<1) 
			
			return false;
		
		return true;
	}

	@Override
	public boolean executeAction(Player player, GameBoard gameBoard)
	{
		CouncilPalace councilPalace = gameBoard.getCouncilPalace(); 
		
		if (!isLegal(player, gameBoard)) return false;

		List<Player> playersInCouncilPalace = councilPalace.getPlayersInCouncilPalace();

		//Aggiunge il player alla lista se non aveva ancora
		//messo alcun familiare all'interno del council palace

		if(!playersInCouncilPalace.contains(player))
			playersInCouncilPalace.add(player);

		int firstCellFree = councilPalace.firstCellFree();

		CouncilPalaceCell cell = councilPalace.getCouncilPalaceCells()[firstCellFree];

		cell.setFamilyMember(familyMember);

		familyMember.setPlayed(true);

		cell.executeEffects(player);

		player.setFamiliarPositioned(true);

		return true;

	}
	

}
