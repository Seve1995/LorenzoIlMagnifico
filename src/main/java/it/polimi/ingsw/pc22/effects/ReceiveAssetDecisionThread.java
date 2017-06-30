package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.AssetType;
import it.polimi.ingsw.pc22.messages.ErrorMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fandroid95 on 29/06/2017.
 */
public class ReceiveAssetDecisionThread implements Runnable
{
    private List<Asset> payedAssets;

    public ReceiveAssetDecisionThread(List<Asset> payedAssets)
    {
        this.payedAssets = payedAssets;
    }

    @Override
    public void run()
    {
        Long timeout = GameMatch.getTimeout();

        Long timestamp = System.currentTimeMillis();

        IOAdapter adapter = GameMatch.getCurrentPlayer().getAdapter();

        while (System.currentTimeMillis() < timestamp + timeout)
        {
            String assetMessage = adapter.getMessage();

            Integer choiceInt = Integer.parseInt(assetMessage);

            if (choiceInt < 0 || choiceInt > payedAssets.size())
            {
                adapter.printMessage(new ErrorMessage("Numero inserito non valido"));

                continue;
            }

            ChooseAsset effect =
                    (ChooseAsset) GameMatch.getCurrentGameBoard().getCurreEffect();

            effect.setChosenAssetsToPay(choiceInt);

            break;
        }
    }
}
