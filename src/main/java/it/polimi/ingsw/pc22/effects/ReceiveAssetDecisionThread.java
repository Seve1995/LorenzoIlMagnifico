package it.polimi.ingsw.pc22.effects;

import it.polimi.ingsw.pc22.adapters.IOAdapter;
import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.connection.GameServer;
import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.messages.ErrorMessage;

import java.util.List;


public class ReceiveAssetDecisionThread implements Runnable
{
    private List<Asset> payedAssets;

    private String gameMatchName;

    public ReceiveAssetDecisionThread(List<Asset> payedAssets, String gameMatchName)
    {

        this.payedAssets = payedAssets;
        this.gameMatchName = gameMatchName;
    }

    @Override
    public void run()
    {
        Long timeout = GameMatch.getTimeout();

        Long timestamp = System.currentTimeMillis();

        GameMatch gameMatch = GameServer.getGameMatchMap().get(gameMatchName);

        IOAdapter adapter = gameMatch.getCurrentPlayer().getAdapter();

        while (System.currentTimeMillis() < timestamp + timeout)
        {
            String assetMessage = adapter.getMessage();

            Integer choiceInt = Integer.parseInt(assetMessage);

            if (choiceInt < 0 || choiceInt > payedAssets.size())
            {
                adapter.printMessage(new ErrorMessage("Numero inserito non valido"));

                continue;
            }

            ChooseAsset effect = (ChooseAsset) gameMatch.getCurrEffect();

            effect.setChosenAssetsToPay(choiceInt);

            break;
        }
    }
}
