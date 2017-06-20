package it.polimi.ingsw.pc22.states;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.gamebox.PlayerBoard;
import it.polimi.ingsw.pc22.utils.PositionUtils;

/**
 * Created by fandroid95 on 20/06/2017.
 */
public class PlayState implements GenericState
{
    @Override
    public void printState()
    {
        System.out.println("Choose an action to execute:");

        //if (!player.isFamiliarPositioned())
        /*
        {

            this.printMessage("Available servants: " + player.getServants());

            StringBuilder availableFamiliarsString = new StringBuilder("Available familiars: ");

            for (FamilyMember f : player.getUnusedFamiliarMembers())
                availableFamiliarsString.append(f.toString() + " ");

            this.printMessage(availableFamiliarsString.toString());

            String actions = PositionUtils.getActionAvailableString(gameBoard);

            this.printMessage(actions);
        }
        */

        System.out.println
                ("- play card <index>" + '\n' +
                "- discard card <index>" + '\n' +
                "- activate card <index>" + '\n' +
                "- pass" + '\n' +
                "- show cards" + '\n' + //questa in realtà si può sempre fare
                "- end game / exit game" + '\n'+
                "- show board"); //questa in realtà si può sempre fare

        //COMMMENTO AI POSTERI V2 QUESTA COSA MI FA CACARE MA SINCERCAMENTE
        //PER ORA NON HA SENSO CREAE DELLE ACTIONS CHE FACCIANO STE COSE


    }

    @Override
    public boolean validate(String string)
    {
        return false;
    }
}
