package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.messages.ErrorMessage;
import it.polimi.ingsw.pc22.utils.CouncilPrivilege;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fandroid95 on 27/06/2017.
 */
public class ReceiceCouncilDecisionThread implements Runnable
{
    private int numberOfBonus;

    public ReceiceCouncilDecisionThread(int numberOfBonus)
    {
        this.numberOfBonus = numberOfBonus;
    }

    @Override
    public void run()
    {
        Long timeout = GameMatch.getTimeout();

        Long timestamp = System.currentTimeMillis();

        IOAdapter adapter = GameMatch.getCurrentPlayer().getAdapter();

        while (System.currentTimeMillis() < timestamp + timeout)
        {
            String councilMessage = adapter.getMessage();

            if (councilMessage == null)
            {
                adapter.printMessage(new ErrorMessage("Council decision Not received"));

                continue;
            }

            CouncilPrivilege privileges = new CouncilPrivilege();

            boolean valid = privileges.validateBonusDecision(councilMessage, numberOfBonus);

            if (!valid)
            {
                adapter.printMessage(new ErrorMessage("Invalid message received"));

                continue;
            }


            String[] bonuses = councilMessage.split("-");

            List<Asset> assets = new ArrayList<>();

            for (String bonus : bonuses)
            {
                assets.addAll(privileges.getBonusFromNumberString(bonus));
            }

            PickOneCouncilPrivilege effect =
                    (PickOneCouncilPrivilege) GameMatch.getCurrentGameBoard().getCurreEffect();

            effect.setChosenAsset(assets);

            break;
        }

        System.out.println("TURN FINISHED");
    }
}
