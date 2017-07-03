package it.polimi.ingsw.pc22.client;

import it.polimi.ingsw.pc22.gamebox.Asset;
import it.polimi.ingsw.pc22.gamebox.CardTypeEnum;
import it.polimi.ingsw.pc22.messages.*;
import it.polimi.ingsw.pc22.states.*;
import javafx.application.Platform;

import java.util.List;

/**
 * Created by fandroid95 on 28/06/2017.
 */
public class MessageHandler
{
    public static void handleMessage(Message message)
    {
        if (message instanceof LoginMessage)
        {
            LoginMessage login = (LoginMessage) message;

            if (login.isUserLogged())
            {
                printOnClient(LoginMessage.getLoggedMessage());

                Client.setGenericState(new GameCreationState());

                Client.setPlayer(login.getPlayer());

                Platform.runLater(() -> {
                    Client.launchCreationMatch();
                });
            }

            if (login.isMatchStarted())
            {
                printOnClient(LoginMessage.getStarted());

                Client.setGenericState(new WaitingState());

                Platform.runLater(() -> {
                    Client.launchWaitingForTheMatch();
                });
            }


            Client.setStateChanged(true);
        }

        if (message instanceof TimerMessage)
        {
            printOnClient(message);

            Client.setGenericState(new WaitingState());

            Client.setStateChanged(true);
        }

        if (message instanceof ErrorMessage)
        {
            printOnClient(message);
        }

        if (message instanceof GameStatusMessage)
        {
            GameStatusMessage gameStatusMessage = (GameStatusMessage) message;

            Client.setPlayer(gameStatusMessage.getPlayer());

            Client.setGameBoard(gameStatusMessage.getGameBoard());
            
            if ("GUI".equals(Client.getInterfaceChoice()) &&
            		"startGameBoard".equals(gameStatusMessage.getState()))
            {
                Platform.runLater(() ->
                {
                    Client.launchGameBoard();
                });
            }
            
            if ("refreshGameBoard".equals(gameStatusMessage.getState()))
            {
                printOnClient(message);
                printOnClient(new CommunicationMessage("Wait your turn..."));
            }

            if ("started".equals(gameStatusMessage.getState()))
            {
                Client.setGenericState(new PlayState());
                
                printOnClient(message);
                
                printOnClient(new CommunicationMessage("Is your turn!"));

                Client.setStateChanged(true);

            }

            if ("finished".equals(gameStatusMessage.getState()))
            {
                Client.setGenericState(new IdleState());
                
                printOnClient(message);
                
                printOnClient(new CommunicationMessage("Turn finished!"));
                
                Client.setStateChanged(true);

            }
        }

        if (message instanceof PickPrivilegeMessage)
        {
            int numberOFPrivileges =
                    ((PickPrivilegeMessage) message).getNumberOfPrivilege();

            Client.setGenericState(new PickCouncilState(numberOFPrivileges));

            Client.setStateChanged(true);

            printOnClient(message);
        }

        if (message instanceof ChooseServantsMessage)
        {
            Client.setPlayer(((ChooseServantsMessage) message).getPlayer());

            Client.setGenericState(new ChooseServantsState());

            Client.setStateChanged(true);

            printOnClient(message);
        }

        if (message instanceof ChooseAssetsMessage)
        {
            List<Asset> assets = ((ChooseAssetsMessage) message).getAssets();

            Client.setGenericState(new ChooseAssetsState(assets));

            Client.setStateChanged(true);

            printOnClient(message);
        }

        if (message instanceof ExecutedAction)
        {
            printOnClient(message);
        }

        if (message instanceof CommunicationMessage)
        {
            printOnClient(message);
        }

        if (message instanceof ChooseCardMessage)
        {
            CardTypeEnum type = ((ChooseCardMessage) message).getCardType();

            Client.setGenericState(new ChooseCardState(type));

            Client.setStateChanged(true);

            printOnClient(message);
        }

        if (message instanceof  ChooseCostsMessage)
        {
            ChooseCostsMessage costsMessage = (ChooseCostsMessage) message;

            Asset militaryPointsRequired = costsMessage.getMilitaryPointsRequired();
            Asset militaryPointsCost = costsMessage.getMilitaryPointsCost();
            List<Asset> resourcesCost = costsMessage.getResourcesCost();

            ChooseCostsState costsState = new ChooseCostsState
                    (militaryPointsRequired, militaryPointsCost, resourcesCost);

            Client.setGenericState(costsState);

            Client.setStateChanged(true);

            printOnClient(message);
        }

        if (message instanceof SuspendedMessage)
        {
            printOnClient(message);

            Client.setGenericState(new AuthenticationState());

            Client.setStateChanged(true);
        }

        if (message instanceof ExcommunicationMessage)
        {
            printOnClient(message);

            Client.setGenericState(new ExcommunicateState());

            Client.setStateChanged(true);
        }

        if (message instanceof ExcommunicationMessage)
        {
            //TODO IMPLEMENTARE CORRETTAMENTE
        }
    }

    private static void printOnClient(Object message)
    {
        if ("GUI".equals(Client.getInterfaceChoice()))
            Platform.runLater(() ->
            {
                Client.getController().updateScene(message);
            });
        else
        {
            System.out.println("Server: " + message.toString());
        }
    }
}
