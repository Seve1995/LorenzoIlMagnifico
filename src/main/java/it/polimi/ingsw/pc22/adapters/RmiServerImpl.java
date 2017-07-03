package it.polimi.ingsw.pc22.adapters;

import it.polimi.ingsw.pc22.actions.Action;
import it.polimi.ingsw.pc22.actions.ActionFactory;
import it.polimi.ingsw.pc22.connection.GameMatch;
import it.polimi.ingsw.pc22.effects.*;
import it.polimi.ingsw.pc22.exceptions.GenericException;
import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.AssetType;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.gamebox.ColorsEnum;
import it.polimi.ingsw.pc22.messages.ErrorMessage;
import it.polimi.ingsw.pc22.messages.ExecutedAction;
import it.polimi.ingsw.pc22.messages.LoginMessage;
import it.polimi.ingsw.pc22.player.Player;
import it.polimi.ingsw.pc22.rmi.RMIClientStreamService;
import it.polimi.ingsw.pc22.rmi.RMIServerInterface;
import it.polimi.ingsw.pc22.utils.CouncilPrivilege;
import it.polimi.ingsw.pc22.utils.GameBoardUtils;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fandroid95 on 29/06/2017.
 */
public class RmiServerImpl implements RMIServerInterface
{
    private Map<Long, RMIIOAdapter> rmiAdapters;

    private Long timeout;

    public RmiServerImpl(Long timeout)
    {
        this.rmiAdapters = new ConcurrentHashMap<>();
        this.timeout = timeout;
    }

    @Override
    public synchronized Long registerClient(RMIClientStreamService streamService) throws RemoteException
    {
        RMIIOAdapter adapter = new RMIIOAdapter(streamService, timeout);

        Long mapKey = rmiAdapters.size() + 1L;

        rmiAdapters.put(mapKey, adapter);

        return mapKey;
    }

    @Override
    public void login(String loginMessage, Long key) throws RemoteException
    {
        IOAdapter adapter = rmiAdapters.get(key);

        Player player;

        try
        {
            player = adapter.authenticate(loginMessage);

            if (player != null)
            {
                LoginMessage message;

                if (player.isInMatch())
                    message = new LoginMessage(true, true, player);
                else
                    message = new LoginMessage(true, false, player);

                System.out.println(message);

                adapter.printMessage(message);
            }
        }
        catch (IOException e)
        {
            throw new GenericException("Cannot find client in registry", e);
        }
    }

    @Override
    public void matchHandling(String matchString, Long key) throws RemoteException
    {
        IOAdapter adapter = rmiAdapters.get(key);

        try
        {
            if (adapter.gameHandling(matchString))
            {
                LoginMessage message = new LoginMessage(true, true, null);

                adapter.printMessage(message);
            }
        }
        catch (IOException e)
        {
            throw new GenericException("Cannot find client in registry", e);
        }
    }

    @Override
    public void doAction(String actionMessage, Long key) throws RemoteException
    {
        IOAdapter adapter = rmiAdapters.get(key);

        if (actionMessage == null)
        {
            adapter.printMessage(new ErrorMessage("Action Not received"));

            return;
        }

        Action action = ActionFactory.createAction(actionMessage, GameMatch.getCurrentPlayer());

        System.out.println("Action created: " + action);

        if (action == null)
        {
            adapter.printMessage(new ErrorMessage("Action Not Valid"));

            return;
        }

        boolean executed = action.executeAction
                (GameMatch.getCurrentPlayer(), GameMatch.getCurrentGameBoard());

        System.out.println(executed + " - " +  GameMatch.getCurrentPlayer().isHasPassed());

        if (!executed) return;

        if (GameMatch.getCurrentPlayer().isFamiliarPositioned())
        {
            adapter.printMessage(new ExecutedAction("Action Performed"));

            return;
        }

        if (GameMatch.getCurrentPlayer().isHasPassed())
            return;
    }

    @Override
    public void takeCouncilDecision(String councilMessage, Long key, int numberOfBonus)
            throws RemoteException
    {
        IOAdapter adapter = rmiAdapters.get(key);

        if (councilMessage == null)
        {
            adapter.printMessage(new ErrorMessage("Council decision Not received"));

            return;
        }

        CouncilPrivilege privileges = new CouncilPrivilege();

        boolean valid = privileges.validateBonusDecision(councilMessage, numberOfBonus);

        if (!valid)
        {
            adapter.printMessage(new ErrorMessage("Invalid message received"));

            return;
        }


        String[] bonuses = councilMessage.split("-");

        List<Asset> assets = new ArrayList<>();

        for (String bonus : bonuses)
        {
            assets.addAll(privileges.getBonusFromNumberString(bonus));
        }

        PickCouncilPrivilege effect =
                (PickCouncilPrivilege) GameMatch.getCurrentGameBoard().getCurreEffect();

        effect.setChosenAssets(assets);
    }

    @Override
    public void takeAssetDecision(String assetDecision, Long key, List<Asset> payedAssets)
        throws RemoteException
    {
        IOAdapter adapter = rmiAdapters.get(key);

        if (assetDecision == null)
        {
            adapter.printMessage(new ErrorMessage("Asset decision Not received"));

            return;
        }

        String assetMessage = adapter.getMessage();

        Integer choiceInt = Integer.parseInt(assetMessage);

        if (choiceInt < 0 || choiceInt > payedAssets.size())
        {
            adapter.printMessage(new ErrorMessage("Numero inserito non valido"));

            return;
        }

        ChooseAsset effect =
                (ChooseAsset) GameMatch.getCurrentGameBoard().getCurreEffect();

        effect.setChosenAssetsToPay(choiceInt);
    }

    @Override
    public void takeCardDecision(String cardMessage, Long key, CardTypeEnum currCardType)
        throws RemoteException
    {
        IOAdapter adapter = rmiAdapters.get(key);

        Pattern cardPattern;

        if (!currCardType.equals(CardTypeEnum.ANY))
        {
            cardPattern = Pattern.compile("^[0-3]$");
        }
        else
        {
            cardPattern = Pattern.compile("^(BUILDING|VENTURE|CHARACTER|TERRITORY) [0-3]$");
        }

        Matcher matcher = cardPattern.matcher(cardMessage);

        if (!matcher.find())
        {
            adapter.printMessage(new ErrorMessage("INVALID INSERTION RETRY"));

            return;
        }

        String[] choices = cardMessage.split(" ");

        Integer floor;

        PickTowerCard effect =
                (PickTowerCard) GameMatch.getCurrentGameBoard().getCurreEffect();

        if (!currCardType.equals(CardTypeEnum.ANY))
        {
            floor = Integer.parseInt(choices[0]);
        }
        else
        {
            floor = Integer.parseInt(choices[1]);

            CardTypeEnum type = CardTypeEnum.valueOf(choices[0]);

            effect.setCardType(type);
        }

        effect.setFloor(floor);
    }

    @Override
    public void takeCostsDecision(String costMessage, Long key)
        throws RemoteException
    {
        IOAdapter adapter = rmiAdapters.get(key);

        Pattern costPattern = Pattern.compile("^[1-2]$");

        Matcher matcher = costPattern.matcher(costMessage);

        if (!matcher.find())
        {
            adapter.printMessage(new ErrorMessage("INVALID INSERTION RETRY"));

            return;
        }

        Integer choiceInt = Integer.parseInt(costMessage);

        PickTowerCard effect =
                (PickTowerCard) GameMatch.getCurrentGameBoard().getCurreEffect();


        effect.setCostDecision(choiceInt);
    }

    @Override
    public void takeServantsDecision(String servantsMessage, Long key)
        throws RemoteException
    {
        IOAdapter adapter = rmiAdapters.get(key);

        Integer servantNumber;

        try
        {
            servantNumber = Integer.parseInt(servantsMessage);
        }
        catch (NumberFormatException e)
        {
            adapter.printMessage(new ErrorMessage("ERROR! You must enter a valid input"));

            return;
        }

        if (servantNumber > GameMatch.getCurrentPlayer().getServants())
        {
            adapter.printMessage(new ErrorMessage("You haven't so much servants"));

            return;
        }

        Asset servants =  new Asset(servantNumber, AssetType.SERVANT);

        ServantsAction effect =
                (ServantsAction) GameMatch.getCurrentGameBoard().getCurreEffect();

        effect.setServants(servants);
    }

    @Override
    public void takeExcommunicationDecision(String excommunicationMessage, Long key)
        throws RemoteException
    {
        IOAdapter adapter = rmiAdapters.get(key);

        if (excommunicationMessage == null)
        {
            adapter.printMessage(new ErrorMessage("Decision Not received"));

            return;
        }

        Pattern costMessage = Pattern.compile("^[1-2]$");

        Matcher matcher = costMessage.matcher(excommunicationMessage);

        if (!matcher.find())
        {
            adapter.printMessage(new ErrorMessage("INVALID INSERTION RETRY"));

            return;
        }

        Integer choiceInt = Integer.parseInt(excommunicationMessage);

        GameBoardUtils.getCurrentPlayer().setExcommunicationChoice(choiceInt);
    }

    @Override
    public void takeFamiliarDecision(String familiarMessage, Long key)
    {
        IOAdapter adapter = rmiAdapters.get(key);

        if (familiarMessage == null)
        {
            adapter.printMessage(new ErrorMessage("Invalid Choice retry"));

            return;
        }

        Pattern familiarPattern =
                Pattern.compile("^(BLACK|ORANGE|WHITE|NEUTER)$");

        Matcher matcher = familiarPattern.matcher(familiarMessage);

        if (!matcher.find())
        {
            adapter.printMessage(new ErrorMessage("Invalid Choice retry"));

            return;
        }

        ColorsEnum color = ColorsEnum.getColorFromString(familiarMessage);


        FamilyMemberModifier effect = (FamilyMemberModifier)
                GameMatch.getCurrentGameBoard().getCurreEffect();

        effect.setFamilyMemberColor(color);
    }

}
