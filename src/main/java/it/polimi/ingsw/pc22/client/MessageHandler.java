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
            printOnClient(((TimerMessage) message).getMessage());

            Client.setGenericState(new WaitingState());

            Client.setStateChanged(true);
        }

        if (message instanceof ErrorMessage)
        {
            printOnClient(((ErrorMessage) message).getMessage());
        }

        if (message instanceof GameStatusMessage)
        {
            GameStatusMessage gameStatusMessage = (GameStatusMessage) message;

            Client.setPlayer(gameStatusMessage.getPlayer());

            Client.setGameBoard(gameStatusMessage.getGameBoard());
            
            if (Client.getInterfaceChoice().equals("GUI") &&
                    !(Client.getController() instanceof GameBoardController))
            {
                Platform.runLater(() ->
                {
                    Client.launchGameBoard();
                    printOnClient(message);
                });
            }
            
            else
            {
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
                    //TODO: Gestire fine

                    Client.setStateChanged(true);
                }
            }
            

        }

        if (message instanceof PickPrivilegeMessage)
        {
            int numberOFPrivileges =
                    ((PickPrivilegeMessage) message).getNumberOfPrivilege();

            Client.setGenericState(new PickCouncilState(numberOFPrivileges));

            Client.setStateChanged(true);
        }

        if (message instanceof ChooseServantsMessage)
        {
            Client.setPlayer(((ChooseServantsMessage) message).getPlayer());

            Client.setGenericState(new ChooseServantsState());

            Client.setStateChanged(true);
        }

        if (message instanceof ChooseAssetsMessage)
        {
            List<Asset> assets = ((ChooseAssetsMessage) message).getAssets();

            Client.setGenericState(new ChooseAssetsState(assets));

            Client.setStateChanged(true);
        }

        if (message instanceof ExecutedAction)
        {
            printOnClient(message);

        }

        if (message instanceof CommunicationMessage)
        {
            printOnClient(((CommunicationMessage) message).getMessage());
        }

        if (message instanceof ChooseCardMessage)
        {
            CardTypeEnum type = ((ChooseCardMessage) message).getCardType();

            Client.setGenericState(new ChooseCardState(type));

            Client.setStateChanged(true);
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
        }
        /*
        if ("show board".equals(msgReceived))
        {
            //PlayerBoard board = player.getPlayerBoard();

            //player.getAdapter().printMessage(board.toString());

            //continue;
        }


        if ("show cards".equals(msgReceived))
        {
            //PlayerBoard board = player.getPlayerBoard();

            //IOAdapter adapter = player.getAdapter();

            //adapter.printMessage(board.getTerritories().toString());
            //adapter.printMessage(board.getBuildings().toString());
            //adapter.printMessage(board.getCharacters().toString());
            //adapter.printMessage(board.getVentures().toString());

            //adapter.printMessage(board.getLeaderCards().toString());

            continue;
        }

        if("EXIT".equalsIgnoreCase(msgReceived))
        {
            System.out.println("Shutting down input");

            socket.close();

            break;
        }*/
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
