package it.polimi.ingsw.pc22.adapters;

import it.polimi.ingsw.pc22.actions.Action;
import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.gamebox.FamilyMember;
import it.polimi.ingsw.pc22.player.Player;

import java.io.IOException;

/**
 * Created by fandroid95 on 30/05/2017.
 */
public interface GameAdapter
{
    void endConnection(Player player) throws IOException;

    void printMessage(String message);

    String getMessage();
    
    Action askAction(FamilyMember familyMember, Asset servant ,Long timeout);
    
    int askFloor();
    
    CardTypeEnum askForCardType();

    FamilyMember askFamiliarMember(Player player, Long timeout);

    Asset askServants(Player player, Long timeout);
}
